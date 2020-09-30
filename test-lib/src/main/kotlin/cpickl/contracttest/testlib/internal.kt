package cpickl.contracttest.testlib

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock

internal object WireMockApi {

    private val jackson = jacksonObjectMapper()

    fun stub(
            method: HttpMethod,
            path: String,
            request: RequestDsl,
            response: ResponseDsl
    ) {
        WireMock.stubFor(method.wiremockMethod(WireMock.urlPathEqualTo(path))
                .withBody(request.body, request.bodyForm)
                .withHeaders(request.headers)
                .withQueryParams(request.queryParams)
                .willReturn(WireMock.aResponse().withStatus(response.statusCode)
                        .withDynamicBody(response.body, response.bodyFile)
                        .withHeaders(response.headers)
                ))
    }

    private fun ResponseDefinitionBuilder.withDynamicBody(body: Any?, bodyFile: String?) =
            bodyFile?.let { withBodyFile(it) } ?: when (body) {
                null -> this
                is String -> withBody(body)
                else -> withBody(jackson.writeValueAsString(body))
            }

}

private fun MappingBuilder.withQueryParams(queryParams: MutableMap<String, String>) = queryParams.entries.fold(this) { acc, entry ->
    acc.withQueryParam(entry.key, WireMock.equalTo(entry.value))
}

private fun ResponseDefinitionBuilder.withHeaders(headers: Map<String, String>) =
        headers.entries.fold(this) { acc, entry ->
            acc.withHeader(entry.key, entry.value)
        }

private fun MappingBuilder.withHeaders(headers: Map<String, String>) =
        headers.entries.fold(this) { acc, entry ->
            acc.withHeader(entry.key, WireMock.equalTo(entry.value))
        }

private fun MappingBuilder.withBody(body: Any?, bodyForm: Map<String, String>?) = when {
    body != null -> withAnyBody(body)
    bodyForm != null -> withOptionalBodyForm(bodyForm)
    else -> this
}

private fun MappingBuilder.withOptionalBodyForm(bodyForm: Map<String, String>?) =
        if (bodyForm == null) {
            this
        } else {
            withRequestBodyFormEncoded(bodyForm)
        }

private fun MappingBuilder.withRequestBodyFormEncoded(keyValues: Map<String, String>): MappingBuilder =
        keyValues.entries.fold(this) { mapping, entry ->
            mapping.withRequestBody(WireMock.containing("${entry.key}=${entry.value}"))
        }

private fun MappingBuilder.withAnyBody(body: Any?): MappingBuilder = when (body) {
    null -> this
    is String -> withRequestBody(WireMock.equalTo(body))
    else -> withRequestBody(WireMock.equalToJson(jackson.writeValueAsString(body)))
}
