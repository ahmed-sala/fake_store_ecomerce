package com.example.fake_store_ecomerce.navigator

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: Int) = "product_details/$productId"
    }
    object Cart : Screen("cart")
}