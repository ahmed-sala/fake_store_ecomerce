package com.example.fake_store_ecomerce.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_store_ecomerce.data.models.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CartRepository(application)

    // StateFlows for reactive UI updates
    val cartItems: StateFlow<List<CartItem>> = repository.getCartItemsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalItemCount: StateFlow<Int> = repository.getTotalItemCountFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val totalPrice: StateFlow<Double> = repository.getTotalPriceFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun addToCart(productResponse: ProductResponse) {
        viewModelScope.launch {
            repository.addToCart(
                productId = productResponse.id ?: 0,
                title = productResponse.title ?: "",
                price = productResponse.price?.toDouble() ?: 0.0,
                image = productResponse.images?.firstOrNull() ?: ""
            )
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
        }
    }

    fun removeAllFromCart(productId: Int) {
        viewModelScope.launch {
            repository.removeAllFromCart(productId)
        }
    }

    fun updateQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(productId, newQuantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun getQuantityForProduct(productId: Int): Int {
        return cartItems.value.find { it.productId == productId }?.quantity ?: 0
    }

    // Convenience methods
    fun getTotalItems(): Int = totalItemCount.value
    fun getTotalPrice(): Double = totalPrice.value
}