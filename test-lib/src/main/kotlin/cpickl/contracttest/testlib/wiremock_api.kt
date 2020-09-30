package cpickl.contracttest.testlib

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.verify

internal val jackson = jacksonObjectMapper()

fun given(method: HttpMethod, path: String, builderCode: Builder.() -> Unit) {
    val builder = Builder().apply(builderCode)

    WireMockApi.stub(
            method = method,
            path = path,
            request = builder._request,
            response = builder._response
    )
}

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE
}

class Builder {

    internal var _request = RequestDsl()
    internal val _response = ResponseDsl()

    fun request(requestCode: RequestDsl.() -> Unit = {}) {
        _request.requestCode()
    }

    fun response(statusCode: Int, responseCode: ResponseDsl.() -> Unit = {}) {
        _response.statusCode = statusCode
        _response.responseCode()
    }
}

class RequestDsl {
    var body: Any? = null
    var bodyForm: Map<String, String>? = null
    var headers: MutableMap<String, String> = mutableMapOf()
    var queryParams: MutableMap<String, String> = mutableMapOf()

    fun headers(vararg headerEntries: Pair<String, String>) {
        headerEntries.forEach { (key, value) ->
            headers[key] = value
        }
    }

    fun queryParams(vararg params: Pair<String, String>) {
        params.forEach { (key, value) ->
            queryParams[key] = value
        }
    }
}

class ResponseDsl {
    var statusCode: Int = -1
    var body: Any? = null
    var bodyFile: String? = null
    var headers: MutableMap<String, String> = mutableMapOf()
    fun headerJsonContent() {
        headers["Content-Type"] = "application/json"
    }
}

fun verifyStub(method: HttpMethod, path: String, queryParams: Map<String, String> = emptyMap()) {
    verify(method.verifyWireMock(urlPathEqualTo(path)).let {
        queryParams.entries.fold(it) { acc, entry ->
            acc.withQueryParam(entry.key, equalTo(entry.value))
        }
    })
}
