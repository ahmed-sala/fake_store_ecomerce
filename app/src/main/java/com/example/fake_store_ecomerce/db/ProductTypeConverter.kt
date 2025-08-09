//package com.example.fake_store_ecomerce.db
//
//
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class ProductTypeConverter {
//
//    private val gson = Gson()
//
//    @TypeConverter
//    fun fromProduct(product: Product): String {
//        return gson.toJson(product)
//    }
//
//    @TypeConverter
//    fun toProduct(productString: String): Product {
//        val type = object : TypeToken<Product>() {}.type
//        return gson.fromJson(productString, type)
//    }
//}
