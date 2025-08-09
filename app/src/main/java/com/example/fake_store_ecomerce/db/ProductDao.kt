package com.example.fake_store_ecomerce.db

import androidx.room.*

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(item: Product)

    @Query("SELECT * FROM cart_table")
    suspend fun getCartItems(): List<Product>

    @Delete
        suspend fun removeFromCart(item:Product)
}
