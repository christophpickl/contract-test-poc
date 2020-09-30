package cpickl.contracttest.serviceconsumer.serverimpl

import cpickl.contracttest.serviceprovider.contractclient.ServiceProviderClient
import cpickl.contracttest.serviceprovider.contractclient.ServiceProviderKtorClient
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
import org.kodein.di.generic.singleton

object ServiceConsumerApp {
    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting up application." }
        embeddedServer(factory = Netty, port = 8082) {
            main(buildKodein(Konfig()))
        }.start(wait = true)
    }
}

// set up beans for dependency injection via Kodein
fun buildKodein(konfig: Konfig) = Kodein {
    bind<Konfig>() with singleton { konfig }
    bind<ServiceProviderClient>() with instance(ServiceProviderKtorClient(konfig.serviceProviderBaseUrl))
    bind<BestSellerService>() with singleton { BestSellerServiceImpl(instance()) }
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

data class Konfig(
        // defaults to production URLs (could add reading from sys-vars/env)
        val serviceProviderBaseUrl: String = "http://localhost:8081"
)