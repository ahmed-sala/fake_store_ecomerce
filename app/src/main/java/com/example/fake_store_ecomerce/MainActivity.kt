package com.example.fake_store_ecomerce

import HomeViewmodel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.fake_store_ecomerce.db.CartViewModel
import com.example.fake_store_ecomerce.networking.NetworkManager
import com.example.fake_store_ecomerce.networking.NetworkStatusHandler
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.managers.CartViewModelFactory
import com.example.fake_store_ecomerce.ui.screens.MainContent
import com.example.fake_store_ecomerce.ui.theme.Fake_store_ecomerceTheme

class MainActivity : ComponentActivity() {
    private lateinit var networkManager: NetworkManager
    private var networkStatusHandler: NetworkStatusHandler? = null

    private val cartViewModelFactory by lazy { CartViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val homeViewmodel: HomeViewmodel by viewModels()
        val categoryViewmodel: CategoryViewmodel by viewModels()
        val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()

        val cartViewModel: CartViewModel by viewModels { cartViewModelFactory }

        networkManager = NetworkManager(this)

        setContent {
            Fake_store_ecomerceTheme {
                MainContent(
                    homeViewmodel = homeViewmodel,
                    categoryViewmodel = categoryViewmodel,
                    productsDetailsViewmodel = productsDetailsViewmodel,
                    networkManager = networkManager,
                    cartViewModel = cartViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStatusHandler?.stopMonitoring()
    }
}