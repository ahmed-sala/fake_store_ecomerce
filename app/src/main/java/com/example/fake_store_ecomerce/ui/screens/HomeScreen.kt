package com.example.fake_store_ecomerce.ui.screens

import HomeViewmodel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.db.CartViewModel
import com.example.fake_store_ecomerce.navigator.Screen
import com.example.fake_store_ecomerce.ui.items.CartFloatingActionButton
import com.example.fake_store_ecomerce.ui.items.ProductsPagingList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewmodel,
    cartViewModel: CartViewModel
) {
    val products: LazyPagingItems<ProductResponse> = viewModel.productsFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fake Store") },
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
        },
        floatingActionButton = {
            CartFloatingActionButton(
                cartViewModel = cartViewModel,
                navController = navController
            )
        }
    ) { padding ->
        ProductsPagingList(
            padding = padding,
            products = products,
            navController = navController,
            cartViewModel = cartViewModel
        )
    }
}


