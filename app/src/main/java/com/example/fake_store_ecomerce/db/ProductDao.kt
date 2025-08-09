package com.example.fake_store_ecomerce.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCartItem(item: CartItem)

    @Query("SELECT * FROM cart_items_table")
    suspend fun getAllCartItems(): List<CartItem>

    @Query("SELECT * FROM cart_items_table WHERE productId = :productId")
    suspend fun getCartItem(productId: Int): CartItem?

    @Query("UPDATE cart_items_table SET quantity = quantity + 1 WHERE productId = :productId")
    suspend fun increaseQuantity(productId: Int)

    @Query("UPDATE cart_items_table SET quantity = quantity - 1 WHERE productId = :productId")
    suspend fun decreaseQuantity(productId: Int)

    @Query("DELETE FROM cart_items_table WHERE productId = :productId")
    suspend fun removeCartItem(productId: Int)

    @Query("DELETE FROM cart_items_table WHERE quantity <= 0")
    suspend fun removeEmptyItems()

    @Query("DELETE FROM cart_items_table")
    suspend fun clearCart()

    @Query("SELECT SUM(quantity) FROM cart_items_table")
    suspend fun getTotalItemCount(): Int?

    @Query("SELECT SUM(price * quantity) FROM cart_items_table")
    suspend fun getTotalPrice(): Double?

    // Flow versions for real-time updates
    @Query("SELECT * FROM cart_items_table")
    fun getAllCartItemsFlow(): Flow<List<CartItem>>

    @Query("SELECT SUM(quantity) FROM cart_items_table")
    fun getTotalItemCountFlow(): Flow<Int?>

    @Query("SELECT SUM(price * quantity) FROM cart_items_table")
    fun getTotalPriceFlow(): Flow<Double?>
}