package com.example.fake_store_ecomerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.HomeViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.theme.Fake_store_ecomerceTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val homeViewmodel: HomeViewmodel by viewModels()
        val categoryViewmodel: CategoryViewmodel by viewModels()
        val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fake_store_ecomerceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Product(homeViewmodel = homeViewmodel,
                        modifier = Modifier.padding(innerPadding)
                    )
                    // You can add more composables here, like a top bar or bottom navigation
                }
            }
        }
    }
}
@Composable
fun Product(homeViewmodel: HomeViewmodel,
           modifier: Modifier = Modifier) {
    var products= homeViewmodel.productsState.collectAsState()
    when (products.value) {
        is ApiResult.Error -> {
            val errorMessage = (products.value as ApiResult.Error).message
            Text(
                text = "Error: $errorMessage",
                modifier = Modifier.padding(16.dp)
            )
        }
        ApiResult.Loading -> {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(16.dp)
            )
        }
        is ApiResult.Success -> {
            // Display the list of products
            val productList = (products.value as ApiResult.Success).data
            if (productList.isEmpty()) {
                Text(
                    text = "No products available",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Display the product list
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(productList) { product ->
                        product.title?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

}}}
