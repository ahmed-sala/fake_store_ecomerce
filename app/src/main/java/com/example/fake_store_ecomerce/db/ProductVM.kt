package com.example.fake_store_ecomerce.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> get() = _cartItems

    fun addToCart(item: Product) {
        viewModelScope.launch {
            repository.addItem(item)
            loadCartItems()
        }
    }

    fun loadCartItems() {
        viewModelScope.launch {
            repository.getCartItems().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun removeFromCart(item: Product) {
        viewModelScope.launch {
            repository.deleteItem(item)
            loadCartItems()
        }
    }
}
