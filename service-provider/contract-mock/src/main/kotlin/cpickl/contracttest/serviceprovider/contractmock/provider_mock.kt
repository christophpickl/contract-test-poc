package cpickl.contracttest.serviceprovider.contractmock

import cpickl.contracttest.serviceprovider.contractdto.ProductDto
import cpickl.contracttest.serviceprovider.contractdto.ProductResponseDto
import cpickl.contracttest.testlib.HttpMethod.GET
import cpickl.contracttest.testlib.given
import cpickl.contracttest.testlib.verifyStub

private val defaultResponse = ProductResponseDto(listOf(ProductDto(42, "Mock Product")))

fun `given service-provider get-products returns success ✅`(
        response: ProductResponseDto = defaultResponse
) {
    given(GET, "/products") {
        response(200) {
            headers["Content-Type"] = "application/json"
            body = response
        }
    }
}

fun `verify service-provider get-products`() {
    verifyStub(GET, "/products")
}