package cpickl.contracttest.serviceconsumer.serverimpl

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.contentType
import io.ktor.server.testing.setBody

// outsource to some ktor-test-lib

fun Assert<TestApplicationResponse>.isJsonContentType() {
    given {
        assertThat(it.contentType().withoutParameters()).isEqualTo(ContentType.Application.Json)
    }
}

fun Assert<TestApplicationResponse>.isStatusCode(expected: HttpStatusCode) {
    prop("status", TestApplicationResponse::status).isEqualTo(expected)
}

fun TestApplicationRequest.addJsonContentType() {
    addHeader("Content-Type", "application/json")
}

fun TestApplicationRequest.setJsonBody(body: String) {
    setBody(body)
    addJsonContentType()
}

inline fun <reified T> ObjectMapper.read(response: TestApplicationResponse): T =
        readValue(response.content ?: "Required response content but none given!")
