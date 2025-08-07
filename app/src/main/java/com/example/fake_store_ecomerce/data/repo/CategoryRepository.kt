package com.example.fake_store_ecomerce.data.repo

import com.example.fake_store.networking.ApiResult
import com.example.fake_store.networking.RetrofitClient
import com.example.fake_store_ecomerce.data.models.CategoryResponse
import com.example.fake_store_ecomerce.data.models.ProductResponse

class CategoryRepository {
    suspend fun getCategories(): ApiResult<List<CategoryResponse>> {
        return try {
            val response = RetrofitClient.apiService.getCategories()
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResult.Error("Exception: ${e.message ?: "Unknown error"}")
        }
    }
    suspend fun getProductsByCategoryId(id: Int): ApiResult<List<ProductResponse>> {
        return try {
            val response = RetrofitClient.apiService.getProductsByCategoryId(categoryId = id)
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