package com.example.fake_store_ecomerce.data.repo

import com.example.fake_store.networking.ApiResult
import com.example.fake_store.networking.RetrofitClient
import com.example.fake_store_ecomerce.data.models.ProductResponse

class ProductRepository {
    suspend fun getProducts(): ApiResult<List<ProductResponse>> {
        return try {
            val response = RetrofitClient.apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResult.Error("Exception: ${e.message ?: "Unknown error"}")
        }
    }
    suspend fun getProductById(id: Int): ApiResult<ProductResponse> {
        return try {
            val response = RetrofitClient.apiService.getProductById(productId = id)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error: ${response.message()}")
            }

        } catch (e: Exception) {
            ApiResult.Error("Exception: ${e.message ?: "Unknown error"}")
        }
    }
}