
package br.com.souzabrunoj.storekmp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.souzabrunoj.storekmp.components.FullScreenLoading
import br.com.souzabrunoj.storekmp.domain.model.Product
import br.com.souzabrunoj.storekmp.presentation.screenModel.HomeState
import br.com.souzabrunoj.storekmp.presentation.screenModel.HomeViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberImagePainter
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<HomeViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(key1 = viewModel) {
            viewModel.getProducts()
        }
        MaterialTheme {
            if (state.isLoading)
                FullScreenLoading()
            else
                StepContent(
                    state = state,
                    onSearch = { viewModel.search(it) },
                    onItemClicked = { product ->
                        navigator.push(ProductDetailsScreen(product))
                    })
        }
    }

    @Composable
    private fun StepContent(
        state: HomeState,
        onSearch: (String) -> Unit,
        onItemClicked: (Product) -> Unit
    ) {
        val scrollState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()
        var query: String by remember { mutableStateOf("") }

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                query = query,
                active = false,
                onActiveChange = {},
                onQueryChange = {
                    onSearch(it)
                    query = it
                },
                onSearch = { onSearch(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                placeholder = { Text(text = "Search a product") },
                content = {})

            LazyVerticalGrid(
                modifier = Modifier.draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch { scrollState.scrollBy(-delta) }

                    }),
                columns = GridCells.Fixed(2),
                state = scrollState,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items = state.productsList, key = { products -> products.id }) { product ->
                    Card(
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                            .clickable(onClick = { onItemClicked(product) }),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape((16.dp)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 2.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            val painter = rememberImagePainter(url = product.image)
                            Image(
                                painter = painter,
                                contentDescription = product.title,
                                modifier = Modifier.height(130.dp)
                            )
                            Text(
                                modifier = Modifier.padding(top = 16.dp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                text = product.title, maxLines = 2, overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                modifier = Modifier.padding(top = 4.dp).align(Alignment.Start),
                                text = "$ ${product.price}", fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}
