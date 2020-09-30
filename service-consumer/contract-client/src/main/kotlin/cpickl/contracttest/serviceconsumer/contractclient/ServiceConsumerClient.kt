package cpickl.contracttest.serviceconsumer.contractclient

import cpickl.contracttest.serviceconsumer.contractdto.BestSellerResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import mu.KotlinLogging.logger

interface ServiceConsumerClient {
    suspend fun getBestSeller(): BestSellerResponseDto
}

class ServiceConsumerKtorClient(
        private val baseUrl: String
) : ServiceConsumerClient {

    private val log = logger {}

    init {
        log.debug { "new client with base URL: '$baseUrl'" }
    }

    private val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    override suspend fun getBestSeller(): BestSellerResponseDto =
            client.get("$baseUrl/bestseller")
}