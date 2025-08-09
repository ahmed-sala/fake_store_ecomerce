package com.example.fake_store_ecomerce.networking

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fake_store_ecomerce.AppConstants
import com.example.fake_store_ecomerce.data.models.ProductResponse

class ProductPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ProductResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResponse> {
        return try {
            val currentOffset = params.key ?: AppConstants.INITIAL_OFFSET
            val response = apiService.getProducts(
                offset = currentOffset,
                limit = params.loadSize
            )

            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                LoadResult.Page(
                    data = products,
                    prevKey = if (currentOffset == AppConstants.INITIAL_OFFSET) null else currentOffset - params.loadSize,
                    nextKey = if (products.isEmpty() || products.size < params.loadSize) null else currentOffset + params.loadSize
                )
            } else {
                LoadResult.Error(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(AppConstants.DEFAULT_PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(AppConstants.DEFAULT_PAGE_SIZE)
        }
    }
}