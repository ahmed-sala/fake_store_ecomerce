package com.example.fake_store_ecomerce.networking

import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NetworkStatusHandler(
    private val networkManager: NetworkManager,
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) {

    private var wasConnected: Boolean? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun startMonitoring() {
        // Check initial connection status
        val initialConnected = networkManager.isNetworkAvailable()
        wasConnected = initialConnected

        showConnectionStatus(initialConnected, isInitial = true)

        // Register network callback
        networkCallback = createNetworkCallback()
        networkManager.registerNetworkCallback(networkCallback!!)
    }

    fun stopMonitoring() {
        networkCallback?.let { callback ->
            networkManager.unregisterNetworkCallback(callback)
        }
    }

    private fun createNetworkCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (wasConnected != true) {
                    wasConnected = true
                    showConnectionStatus(true)
                }
            }

            override fun onLost(network: Network) {
                if (wasConnected != false) {
                    wasConnected = false
                    showConnectionStatus(false)
                }
            }
        }
    }

    private fun showConnectionStatus(isConnected: Boolean, isInitial: Boolean = false) {
        coroutineScope.launch {
            val message = if (isConnected) {
                if (isInitial) "Connected ✅" else "Back online ✅"
            } else {
                "No internet connection ❌"
            }
            snackbarHostState.showSnackbar(message)
        }
    }
}
