package com.example.fake_store_ecomerce.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.navigator.Screen
import com.example.fake_store_ecomerce.ui.managers.HomeViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewmodel) {
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
                is ApiResult.Loading -> Text("Loading...", modifier = Modifier.padding(16.dp))
                is ApiResult.Error -> Text("Error: ${state.message}", modifier = Modifier.padding(16.dp))
                is ApiResult.Success -> {
                    LazyColumn {
                        items(state.data) { product ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = product.title ?: "No title",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Button(onClick = {
                                        navController.navigate(Screen.ProductDetails.route)
                                    }) {
                                        Text("Details")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
