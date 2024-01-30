package br.com.souzabrunoj.storekmp.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import br.com.souzabrunoj.storekmp.domain.model.Product
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _productsList = MutableStateFlow<List<Product>>(emptyList())
    val productsList: StateFlow<List<Product>> = _productsList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getProducts().collect { products ->

                _productsList.update { list ->
                    list.plus(products)
                }
            }
        }
    }
}
