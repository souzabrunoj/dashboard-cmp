package br.com.souzabrunoj.storekmp.data

import br.com.souzabrunoj.storekmp.domain.model.Product
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import br.com.souzabrunoj.storekmp.networking.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HomeRepositoryImpl(private val api: HttpClient) : HomeRepository {
    override suspend fun getApiProducts(): List<Product> {
       return safeRequest { api.get("products").body() }
    }
}