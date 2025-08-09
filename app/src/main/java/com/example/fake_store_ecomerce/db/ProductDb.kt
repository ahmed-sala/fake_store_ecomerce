package com.example.fake_store_ecomerce.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
