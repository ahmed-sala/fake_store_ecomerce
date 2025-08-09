package com.example.fake_store_ecomerce

import HomeViewmodel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fake_store_ecomerce.networking.NetworkManager
import com.example.fake_store_ecomerce.networking.NetworkStatusHandler
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel
import com.example.fake_store_ecomerce.ui.screens.MainContent
import com.example.fake_store_ecomerce.ui.theme.FakeStoreEcomerceTheme

class MainActivity : ComponentActivity() {
    private val homeViewmodel: HomeViewmodel by viewModels()
    private val categoryViewmodel: CategoryViewmodel by viewModels()
    private val productsDetailsViewmodel: ProductsDetailsViewmodel by viewModels()

    private lateinit var networkManager: NetworkManager
    private var networkStatusHandler: NetworkStatusHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        networkManager = NetworkManager(this)

        setContent {
            FakeStoreEcomerceTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(
                        homeViewmodel = homeViewmodel,
                        categoryViewmodel = categoryViewmodel,
                        productsDetailsViewmodel = productsDetailsViewmodel,
                        networkManager = networkManager
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStatusHandler?.stopMonitoring()
    }
}