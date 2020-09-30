package cpickl.contracttest.serviceconsumer.serverimpl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import cpickl.contracttest.serviceconsumer.contractdto.BestSellerDto
import cpickl.contracttest.serviceconsumer.contractdto.BestSellerResponseDto
import cpickl.contracttest.serviceprovider.contractdto.ProductDto
import cpickl.contracttest.serviceprovider.contractdto.ProductResponseDto
import cpickl.contracttest.serviceprovider.contractmock.`given service-provider get-products returns success ✅`
import cpickl.contracttest.serviceprovider.contractmock.`verify service-provider get-products`
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

private val jackson = jacksonObjectMapper()

@Test
class ContractTest {

    private val anyId = 42
    private val bestsellerProductName = "Affen"
    private val wireMockPort = 8091
    private val wireMockServer = WireMockServer(wireMockPort);

    @BeforeClass
    fun `start wiremock`() {
        wireMockServer.start()
        WireMock.configureFor("localhost", wireMockPort)
    }

    @AfterClass
    fun `stop wiremock`() {
        wireMockServer.stop()
    }

    @BeforeMethod
    fun `reset wiremock`() {
        wireMockServer.resetAll()
    }

    fun `Given service-provider returns bestseller When get bestseller Then return it`() = withMockedProvider {
        `given service-provider get-products returns success ✅`(ProductResponseDto(listOf(
                ProductDto(anyId, bestsellerProductName)
        )))

        with(handleRequest(Get, "/bestseller")) {

            assertThat(response).isStatusCode(OK)
            assertThat(response).isJsonContentType()
            assertThat(jackson.read<BestSellerResponseDto>(response))
                    .isEqualTo(BestSellerResponseDto(listOf(
                            BestSellerDto(name = bestsellerProductName)
                    )))
        }
        `verify service-provider get-products`()
    }

    fun `Given service-provider returns empty When get bestseller Then return empty`() = withMockedProvider {
        `given service-provider get-products returns success ✅`(ProductResponseDto.empty)

        with(handleRequest(Get, "/bestseller")) {

            assertThat(response).isStatusCode(OK)
            assertThat(response).isJsonContentType()
            assertThat(jackson.read<BestSellerResponseDto>(response))
                    .isEqualTo(BestSellerResponseDto.empty)
        }
        `verify service-provider get-products`()
    }

    private fun withMockedProvider(testCode: TestApplicationEngine.() -> Unit) {
        withKtor(
                konfig = {
                    copy(serviceProviderBaseUrl = "http://localhost:$wireMockPort")
                },
                testCode = testCode
        )
    }
}
