package cpickl.contracttest.serviceprovider.contractclient

import cpickl.contracttest.serviceprovider.contractdto.ProductResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import mu.KotlinLogging.logger

interface ServiceProviderClient {
    suspend fun getProducts(): ProductResponseDto
}

class ServiceProviderKtorClient(
        private val baseUrl: String
) : ServiceProviderClient {

    private val log = logger {}

    init {
        log.debug { "new client with base URL: '$baseUrl'" }
    }

    private val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    override suspend fun getProducts(): ProductResponseDto =
            client.get("$baseUrl/products")
}