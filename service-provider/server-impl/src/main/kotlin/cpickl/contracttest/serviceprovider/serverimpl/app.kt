package cpickl.contracttest.serviceprovider.serverimpl

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging.logger
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

object ServiceProviderApp {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting up application." }
        embeddedServer(factory = Netty, port = 8081) {
            main(buildKodein())
        }.start(wait = true)
    }
}

// set up beans for dependency injection via Kodein
fun buildKodein() = Kodein {
    bind<ProductRepository>() with instance(InMemoryProductRepository)
}

// configure basics for Ktor
fun Application.main(kodein: Kodein) {
    install(ContentNegotiation) {
        jackson {}
    }
    routing {
        installRouting(kodein)
    }
}
