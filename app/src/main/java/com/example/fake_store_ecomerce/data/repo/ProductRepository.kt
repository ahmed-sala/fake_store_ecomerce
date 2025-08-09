package com.example.fake_store_ecomerce.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.fake_store.networking.ApiResult
import com.example.fake_store.networking.RetrofitClient
import com.example.fake_store_ecomerce.AppConstants
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.networking.ProductPagingSource
import kotlinx.coroutines.flow.Flow

class ProductRepository {

    // Paging 3 implementation
    fun getProductsPaging(): Flow<PagingData<ProductResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = AppConstants.DEFAULT_PAGE_SIZE,
                prefetchDistance = AppConstants.PREFETCH_DISTANCE,
                enablePlaceholders = false,
                initialLoadSize = AppConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = { ProductPagingSource(RetrofitClient.apiService) }
        ).flow
    }

    // Keep the original method for single product fetching
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
