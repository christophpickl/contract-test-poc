package cpickl.contracttest.serviceconsumer.serverimpl

import cpickl.contracttest.serviceprovider.contractclient.ServiceProviderClient

interface BestSellerService {
    suspend fun loadBestSeller(): List<BestSeller>
}

class BestSellerServiceImpl(
        private val client: ServiceProviderClient
) : BestSellerService {
    override suspend fun loadBestSeller() =
            client.getProducts().products
                    .filter { it.name.startsWith("a", ignoreCase = true) }
                    .map { BestSeller(name = it.name) }
}

data class BestSeller(
        val name: String
)