package br.com.souzabrunoj.storekmp.presentation

import br.com.souzabrunoj.storekmp.domain.model.Product
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val rootProductList: List<Product> = emptyList(), val productsList: List<Product> = emptyList()
)

class HomeViewModel(private val repository: HomeRepository) :
    StateScreenModel<HomeState>(initialState = HomeState()) {

    fun getProducts() {
        screenModelScope.launch {
            val products = repository.getApiProducts()
            mutableState.update { state ->
                state.copy(rootProductList = products, productsList = products)
            }
        }
    }

    fun search(query: String) {
        mutableState.update { state ->
            state.copy(productsList = state.rootProductList.filter { product ->
                product.title.contains(query)
            })
        }
    }
}
