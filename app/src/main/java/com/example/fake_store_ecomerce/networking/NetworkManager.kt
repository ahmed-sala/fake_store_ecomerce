package com.example.fake_store_ecomerce.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkManager(private val context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun registerNetworkCallback(callback: ConnectivityManager.NetworkCallback) {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    fun unregisterNetworkCallback(callback: ConnectivityManager.NetworkCallback) {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}
