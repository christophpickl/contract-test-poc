package cpickl.contracttest.serviceconsumer.serverimpl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.Application
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
import mu.KotlinLogging.logger
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

// some application specific support test code, to wire ktor's test app engine with kodein + konfig

private val log = logger {}

val jackson = jacksonObjectMapper()

fun withServiceApplication(
        kodein: Kodein.MainBuilder.(Application) -> Unit = {},
        konfig: Konfig.() -> Konfig = { this },
        testCode: TestApplicationEngine.() -> Unit
) {
    withTestApplication({
        val kodeinInstance = Kodein {
            val konfigInstance = Konfig().konfig()
            log.debug { "Test konfig being used: $konfigInstance" }
            extend(buildKodein(konfigInstance), copy = Copy.All)
            kodein(this@withTestApplication)
            bind<Konfig>(overrides = true) with instance(konfigInstance)
        }
        main(kodeinInstance)
    }, testCode)
}
