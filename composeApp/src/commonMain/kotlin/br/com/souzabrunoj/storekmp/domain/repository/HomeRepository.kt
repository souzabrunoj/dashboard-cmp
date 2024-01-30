package br.com.souzabrunoj.storekmp.domain.repository

import br.com.souzabrunoj.storekmp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getApiProducts(): List<Product>
    suspend fun getProducts(): Flow<List<Product>>

}