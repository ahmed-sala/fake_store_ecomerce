package com.example.fake_store_ecomerce.ui.managers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.data.repo.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewmodel: ViewModel() {
    private val _productsState = MutableStateFlow<ApiResult<List<ProductResponse>>>(ApiResult.Loading)
    val productsState: StateFlow<ApiResult<List<ProductResponse>>> = _productsState
    init {
getProducts()
    }
    fun getProducts() {

        viewModelScope.launch {
            _productsState.value = ApiResult.Loading
            _productsState.value = ProductRepository().getProducts()         }
    }
}