package com.example.fake_store_ecomerce.db
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import android.content.Context
import androidx.room.Room

class CartRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        CartDatabase::class.java,
        "cart_database"
    ).build()

    private val cartDao = db.cartDao()

    suspend fun addItem(cartItem: Product) {
        cartDao.addToCart(cartItem)
    }

  fun getCartItems(): Flow<List<Product>> = flow {
        emit(cartDao.getCartItems())
    }

    suspend fun deleteItem(cartItem: Product) {
        cartDao.removeFromCart(cartItem)
    }
}
