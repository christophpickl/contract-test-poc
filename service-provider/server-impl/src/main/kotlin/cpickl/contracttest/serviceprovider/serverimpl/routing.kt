package cpickl.contracttest.serviceprovider.serverimpl

import cpickl.contracttest.serviceprovider.contractdto.ProductDto
import cpickl.contracttest.serviceprovider.contractdto.ProductResponseDto
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

// wire up endpoints
fun Routing.installRouting(kodein: Kodein) {
    val productRepository by kodein.instance<ProductRepository>()

    get("/products") {
        call.respond(ProductResponseDto(productRepository.loadAll().map { it.toProductDto() }))
    }
}

private fun Product.toProductDto() = ProductDto(id, name)
