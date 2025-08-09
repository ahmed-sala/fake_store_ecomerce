package com.example.fake_store_ecomerce

import HomeScreen
import HomeViewmodel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import com.example.fake_store_ecomerce.navigator.Screen
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.screens.CategoriesScreen
import com.example.fake_store_ecomerce.ui.screens.ProductDetailsScreen
import com.example.fake_store_ecomerce.ui.screens.CartScreen
import com.example.fake_store_ecomerce.ui.theme.Fake_store_ecomerceTheme


class MainActivity : ComponentActivity() {
    val homeViewmodel: HomeViewmodel by viewModels()
    val categoryViewmodel: CategoryViewmodel by viewModels()
    val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Fake_store_ecomerceTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                navController = navController,
                                viewModel = homeViewmodel
                            )
                        }
                        composable(Screen.Categories.route) {
                            CategoriesScreen(
                                navController = navController,
                                viewModel = categoryViewmodel
                            )
                        }
                        composable(Screen.ProductDetails.route) {
                            ProductDetailsScreen(
                                navController = navController,
                                viewModel = productsDetailsViewmodel
                            )
                        }
                        composable(Screen.Cart.route) {
                            CartScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
