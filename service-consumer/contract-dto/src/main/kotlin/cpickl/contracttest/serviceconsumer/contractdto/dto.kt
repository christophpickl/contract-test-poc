package cpickl.contracttest.serviceconsumer.contractdto

data class BestSellerResponseDto(
        val bestSellers: List<BestSellerDto>
) {
    companion object {
        val empty = BestSellerResponseDto(bestSellers = emptyList())
    }
}

data class BestSellerDto(
        val name: String
)
