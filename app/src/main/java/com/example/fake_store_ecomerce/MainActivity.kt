package com.example.fake_store_ecomerce

import HomeViewmodel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.fake_store_ecomerce.networking.NetworkManager
import com.example.fake_store_ecomerce.networking.NetworkStatusHandler
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.screens.MainContent
import com.example.fake_store_ecomerce.ui.theme.Fake_store_ecomerceTheme

class MainActivity : ComponentActivity() {
    private val homeViewmodel: HomeViewmodel by viewModels()
    private val categoryViewmodel: CategoryViewmodel by viewModels()
    private val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()

    private lateinit var networkManager: NetworkManager
    private var networkStatusHandler: NetworkStatusHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val homeViewmodel: HomeViewmodel by viewModels()
        val categoryViewmodel: CategoryViewmodel by viewModels()
        val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        networkManager = NetworkManager(this)

        setContent {
            Fake_store_ecomerceTheme {
                MainContent(
                    homeViewmodel = homeViewmodel,
                    categoryViewmodel = categoryViewmodel,
                    productsDetailsViewmodel = productsDetailsViewmodel,
                    networkManager = networkManager,

                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStatusHandler?.stopMonitoring()
    }
}