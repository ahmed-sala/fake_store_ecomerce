package com.example.fake_store_ecomerce.ui.managers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.data.repo.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsDetailsViewmodel: ViewModel() {
    private val _productState = MutableStateFlow<ApiResult<ProductResponse>>(ApiResult.Loading)
    val productState: StateFlow<ApiResult<ProductResponse>> = _productState
    fun getProductsById(productId: Int) {
        viewModelScope.launch {
            _productState.value = ApiResult.Loading
            _productState.value = ProductRepository().getProductById(id=productId)         }
    }
}