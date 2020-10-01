package cpickl.contracttest.serviceconsumer.serverimpl

import cpickl.contracttest.serviceconsumer.contractdto.BestSellerDto
import cpickl.contracttest.serviceconsumer.contractdto.BestSellerResponseDto
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

// wire up endpoints
fun Routing.installRouting(kodein: Kodein) {
    val controller by kodein.instance<BestSellerService>()

    get("/bestsellers") {
        call.respond(BestSellerResponseDto(controller.loadBestSeller().map { it.toBestSellerDto() }))
    }
}

private fun BestSeller.toBestSellerDto() = BestSellerDto(
        name = name
)
