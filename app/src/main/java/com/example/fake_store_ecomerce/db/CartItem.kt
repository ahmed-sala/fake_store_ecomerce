package com.example.fake_store_ecomerce.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items_table")
data class CartItem(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
)