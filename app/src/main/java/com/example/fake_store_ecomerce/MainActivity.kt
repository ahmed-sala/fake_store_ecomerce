package com.example.fake_store_ecomerce

import HomeScreen
import HomeViewmodel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fake_store_ecomerce.navigator.Screen
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.screens.*
import com.example.fake_store_ecomerce.ui.screens.CategoriesScreen
import com.example.fake_store_ecomerce.ui.screens.ProductDetailsScreen
import com.example.fake_store_ecomerce.ui.screens.CartScreen
import com.example.fake_store_ecomerce.ui.theme.Fake_store_ecomerceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val homeViewmodel: HomeViewmodel by viewModels()
    private val categoryViewmodel: CategoryViewmodel by viewModels()
    private val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Fake_store_ecomerceTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()

                var isConnected by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    val connectivityManager =
                        getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

                    var wasConnected: Boolean? = null


                    val initialConnected = connectivityManager.activeNetwork != null
                    wasConnected = initialConnected
                    isConnected = initialConnected
                    if (!initialConnected) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("No internet connection ❌")
                        }

                    }
                    else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Back online ✅")
                        }
                    }

                    connectivityManager.registerDefaultNetworkCallback(object :
                        ConnectivityManager.NetworkCallback() {

                        override fun onAvailable(network: Network) {
                            if (wasConnected != true) {
                                wasConnected = true
                                isConnected = true
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Back online ✅")
                                }
                            }
                        }

                        override fun onLost(network: Network) {
                            if (wasConnected != false) {
                                wasConnected = false
                                isConnected = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("No internet connection ❌")
                                }
                            }
                        }
                    })
                }


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
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
