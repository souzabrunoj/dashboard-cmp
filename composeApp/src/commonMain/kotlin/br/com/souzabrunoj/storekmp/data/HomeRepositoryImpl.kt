package br.com.souzabrunoj.storekmp.data

import br.com.souzabrunoj.storekmp.data.remote.httpClient
import br.com.souzabrunoj.storekmp.domain.model.Product
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl : HomeRepository {
    override suspend fun getApiProducts(): List<Product> {
        return httpClient.get("https://fakestoreapi.com/products").body()
    }
}