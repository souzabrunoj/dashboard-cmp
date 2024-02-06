package br.com.souzabrunoj.storekmp.presentation

import br.com.souzabrunoj.storekmp.domain.model.Product
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private var productRootList = mutableListOf<Product>()

    private val _productsList = MutableStateFlow<List<Product>>(emptyList())
    val productsList: StateFlow<List<Product>> = _productsList.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                productRootList.addAll(products)
                _productsList.update { list ->
                    list.plus(productRootList)
                }
            }
        }
    }

    fun search(query: String) {
        _productsList.update {
            productRootList.filter { it.title.contains(query) }
        }
    }
}
