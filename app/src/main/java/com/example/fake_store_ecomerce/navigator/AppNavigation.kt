package com.example.fake_store_ecomerce.navigator

import com.example.fake_store_ecomerce.ui.screens.HomeScreen
import HomeViewmodel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fake_store_ecomerce.db.CartViewModel
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.screens.CartScreen
import com.example.fake_store_ecomerce.ui.screens.CategoriesScreen
import com.example.fake_store_ecomerce.ui.screens.ProductDetailsScreen
import com.example.fake_store_ecomerce.ui.screens.SplashScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewmodel: HomeViewmodel,
    categoryViewmodel: CategoryViewmodel,
    productsDetailsViewmodel: ProductsDetailsViewmodel,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = homeViewmodel,
                cartViewModel = cartViewModel
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                navController = navController,
                viewModel = categoryViewmodel
            )
        }

        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailsScreen(
                productId = productId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = productsDetailsViewmodel,
                cartViewModel = cartViewModel

            )
        }




        composable(Screen.Cart.route) {
            CartScreen(navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}
