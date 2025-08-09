package com.example.fake_store_ecomerce.navigator

import HomeScreen
import HomeViewmodel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.screens.CartScreen
import com.example.fake_store_ecomerce.ui.screens.CategoriesScreen
import com.example.fake_store_ecomerce.ui.screens.ProductDetailsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewmodel: HomeViewmodel,
    categoryViewmodel: CategoryViewmodel,
    productsDetailsViewmodel: ProductsDetailsViewmodel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
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
