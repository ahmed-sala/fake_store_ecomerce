package com.example.fake_store_ecomerce.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val image: String
)