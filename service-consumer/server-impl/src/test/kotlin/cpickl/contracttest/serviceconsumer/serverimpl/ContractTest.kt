package cpickl.contracttest.serviceconsumer.serverimpl

import assertk.assertThat
import assertk.assertions.isEqualTo
import cpickl.contracttest.serviceconsumer.contractdto.BestSellerDto
import cpickl.contracttest.serviceconsumer.contractdto.BestSellerResponseDto
import cpickl.contracttest.serviceprovider.contractdto.ProductDto
import cpickl.contracttest.serviceprovider.contractdto.ProductResponseDto
import cpickl.contracttest.serviceprovider.contractmock.`given service-provider get-products returns success ✅`
import cpickl.contracttest.serviceprovider.contractmock.`verify service-provider get-products`
import cpickl.contracttest.testlib.WireMockListener
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import org.testng.annotations.Listeners
import org.testng.annotations.Test

@Test
@Listeners(WireMockListener::class)
class ContractTest {

    private val anyId = 42
    private val bestsellerProductName = "Affen"

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
        withServiceApplication(
                konfig = {
                    copy(serviceProviderBaseUrl = WireMockListener.wireMockUrl)
                },
                testCode = testCode
        )
    }
}
