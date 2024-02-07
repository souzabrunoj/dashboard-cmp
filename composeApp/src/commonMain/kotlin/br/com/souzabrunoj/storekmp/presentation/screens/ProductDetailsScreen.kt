package br.com.souzabrunoj.storekmp.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.com.souzabrunoj.storekmp.domain.model.Product
import cafe.adriel.voyager.core.screen.Screen

data class ProductDetailsScreen(private val product: Product) : Screen {
    @Composable
    override fun Content() {
        Text(text = product.title)
    }
}
