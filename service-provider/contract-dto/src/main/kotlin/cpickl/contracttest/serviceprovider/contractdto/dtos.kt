package cpickl.contracttest.serviceprovider.contractdto

// FUTURE: generate by openapi spec
// json ready serializable entities, without reference to any specific frameworks

data class ProductResponseDto(
        val products: List<ProductDto>
) {
    companion object {
        val empty = ProductResponseDto(products = emptyList())
    }
}

data class ProductDto(
        val id: Int,
        val name: String
)