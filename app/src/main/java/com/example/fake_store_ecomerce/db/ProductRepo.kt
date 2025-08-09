package com.example.fake_store_ecomerce.db
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.map

class CartRepository(context: Context) {
    private val cartDao = CartDatabase.getInstance(context).cartDao()

    suspend fun addToCart(productId: Int, title: String, price: Double, image: String) {
        val existingItem = cartDao.getCartItem(productId)
        if (existingItem != null) {
            cartDao.increaseQuantity(productId)
        } else {
            val newCartItem = CartItem(
                productId = productId,
                title = title,
                price = price,
                image = image,
                quantity = 1
            )
            cartDao.insertOrUpdateCartItem(newCartItem)
        }
    }

    suspend fun removeFromCart(productId: Int) {
        val existingItem = cartDao.getCartItem(productId)
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                cartDao.decreaseQuantity(productId)
            } else {
                cartDao.removeCartItem(productId)
            }
        }
    }

    suspend fun removeAllFromCart(productId: Int) {
        cartDao.removeCartItem(productId)
    }

    suspend fun updateQuantity(productId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            cartDao.removeCartItem(productId)
        } else {
            val existingItem = cartDao.getCartItem(productId)
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = newQuantity)
                cartDao.insertOrUpdateCartItem(updatedItem)
            }
        }
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun getCartItem(productId: Int): CartItem? {
        return cartDao.getCartItem(productId)
    }

    // Flow methods for real-time updates
    fun getCartItemsFlow(): Flow<List<CartItem>> = cartDao.getAllCartItemsFlow()

    fun getTotalItemCountFlow(): Flow<Int> = cartDao.getTotalItemCountFlow().map { it ?: 0 }

    fun getTotalPriceFlow(): Flow<Double> = cartDao.getTotalPriceFlow().map { it ?: 0.0 }

    // Non-flow methods for one-time queries
    suspend fun getAllCartItems(): List<CartItem> = cartDao.getAllCartItems()
    suspend fun getTotalItems(): Int = cartDao.getTotalItemCount() ?: 0
    suspend fun getTotalPrice(): Double = cartDao.getTotalPrice() ?: 0.0
}