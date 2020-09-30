package cpickl.contracttest.serviceprovider.contractmock

import cpickl.contracttest.serviceprovider.contractdto.ProductDto
import cpickl.contracttest.serviceprovider.contractdto.ProductResponseDto
import cpickl.contracttest.testlib.HttpMethod.GET
import cpickl.contracttest.testlib.given
import cpickl.contracttest.testlib.verifyStub

fun `given service-provider get-products returns success ✅`(response: ProductResponseDto = ProductResponseDto(listOf(ProductDto(42, "Mock Product")))) {
    given(GET, "/products") {
        response(200) {
            headerJsonContent()
            body = response
        }
    }
}

fun `verify service-provider get-products`() {
    verifyStub(GET, "/products")
}