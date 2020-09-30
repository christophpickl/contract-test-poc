package cpickl.contracttest.serviceconsumer.serverimpl

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.contentType
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import mu.KotlinLogging.logger
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

private val log = logger {}

fun withKtor(
        kodein: Kodein.MainBuilder.(Application) -> Unit = {},
        konfig: Konfig.() -> Konfig = { this },
        testCode: TestApplicationEngine.() -> Unit
) {
    withTestApplication({
        val kodeinInstance = Kodein {
            val konfigInstance = Konfig().konfig()
            log.debug { "Test konfig being used: $konfigInstance" }
            extendApplicationKodein(konfigInstance)
            kodein(this@withTestApplication)
            bind<Konfig>(overrides = true) with instance(konfigInstance)
        }
        main(kodeinInstance)
    }, testCode)
}

fun Kodein.MainBuilder.extendApplicationKodein(konfig: Konfig) {
    extend(buildKodein(konfig), copy = Copy.All)
}

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
        readValue<T>(response.content ?: "Required response content but none given!")
