package com.example.fake_store_ecomerce.ui.screens

import HomeViewmodel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.fake_store_ecomerce.db.CartViewModel
import com.example.fake_store_ecomerce.navigator.AppNavigation
import com.example.fake_store_ecomerce.networking.NetworkManager
import com.example.fake_store_ecomerce.networking.NetworkStatusHandler
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel

@Composable
 fun MainContent(
    homeViewmodel: HomeViewmodel,
    categoryViewmodel: CategoryViewmodel,
    productsDetailsViewmodel: ProductsDetailsViewmodel,
    networkManager: NetworkManager,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val networkStatusHandler = remember {
        NetworkStatusHandler(
            networkManager = networkManager,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        )
    }

    LaunchedEffect(Unit) {
        networkStatusHandler.startMonitoring()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            homeViewmodel = homeViewmodel,
            categoryViewmodel = categoryViewmodel,
            productsDetailsViewmodel = productsDetailsViewmodel,
            modifier = Modifier.padding(0.dp),
            cartViewModel = cartViewModel
        )
    }
}

