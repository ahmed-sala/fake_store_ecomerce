package com.example.fake_store_ecomerce.networking

import com.example.fake_store_ecomerce.AppConstants
import com.example.fake_store_ecomerce.data.models.CategoryResponse
import com.example.fake_store_ecomerce.data.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(AppConstants.GET_PRODUCTS_ENDPOINT)
    suspend fun getProducts(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<List<ProductResponse>>

    @GET(AppConstants.GET_PRODUCT_BY_ID_ENDPOINT)
    suspend fun getProductById(@Path("id") productId: Int): Response<ProductResponse>

    @GET(AppConstants.GET_CATEGORIES_ENDPOINT)
    suspend fun getCategories(): Response<List<CategoryResponse>>

    @GET(AppConstants.GET_PRODUCTS_BY_CATEGORY_ID_ENDPOINT)
    suspend fun getProductsByCategoryId(@Path("id") categoryId: Int): Response<List<ProductResponse>>
}