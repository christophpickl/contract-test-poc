package cpickl.contracttest.serviceprovider.serverimpl

interface ProductRepository {
    fun loadAll(): List<Product>
}

data class Product(
        val id: Int,
        val name: String
)

object InMemoryProductRepository : ProductRepository {
    private val products = listOf(
            Product(1, "Ijsberg Sla"),
            Product(2, "Aardappel")
    )

    override fun loadAll() = products
}