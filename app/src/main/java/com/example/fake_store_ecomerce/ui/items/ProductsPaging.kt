package com.example.fake_store_ecomerce.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.fake_store_ecomerce.data.models.ProductResponse

@Composable
fun ProductsPagingList(
    products: LazyPagingItems<ProductResponse>,
    navController: NavController,
    padding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            count = products.itemCount,
            key = products.itemKey { it.id ?: 0 }
        ) { index ->
            val product = products[index]
            if (product != null) {
                ProductItem(product = product, navController = navController)
            }
        }

        when (products.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is LoadState.Error -> {
                val error = products.loadState.refresh as LoadState.Error
                item {
                    ErrorItem(
                        message = error.error.localizedMessage ?: "Unknown error occurred",
                        onRetry = { products.retry() }
                    )
                }
            }
            else -> {}
        }

        when (products.loadState.append) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
            is LoadState.Error -> {
                val error = products.loadState.append as LoadState.Error
                item {
                    ErrorItem(
                        message = error.error.localizedMessage ?: "Error loading more items",
                        onRetry = { products.retry() }
                    )
                }
            }
            else -> {}
        }
    }
}