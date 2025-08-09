package com.example.fake_store_ecomerce.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.navigator.Screen
import com.example.fake_store_ecomerce.ui.managers.HomeViewmodel
import com.example.fake_store_ecomerce.ui.theme.Pink40

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewmodel
) {
    val products = viewModel.productsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    Button(
                        onClick = { navController.navigate(Screen.Categories.route) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Categories", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (val state = products.value) {
                is ApiResult.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

                is ApiResult.Error -> {
                    Text("Error: ${state.message}", modifier = Modifier.padding(16.dp))
                }

                is ApiResult.Success -> {
                    LazyColumn {
                        items(state.data) { product ->
                            ProductItem(product = product, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductResponse, navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.ProductDetails.route) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.images),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = product.title ?: "No title", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$${product.price}", style = MaterialTheme.typography.bodySmall)

                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Added to cart")
                        }

                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Add To Cart" )
                }
            }
        }

        // Show snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(8.dp)
        )
    }

        }


