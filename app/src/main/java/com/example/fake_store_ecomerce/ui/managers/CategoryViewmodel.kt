package com.example.fake_store_ecomerce.ui.managers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.CategoryResponse
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.data.repo.CategoryRepository
import com.example.fake_store_ecomerce.data.repo.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewmodel: ViewModel() {
    private val _productsState = MutableStateFlow<ApiResult<List<ProductResponse>>>(ApiResult.Loading)
    val productsState: StateFlow<ApiResult<List<ProductResponse>>> = _productsState

    private val _categoryState = MutableStateFlow<ApiResult<List<CategoryResponse>>>(ApiResult.Loading)
    val categoryState: StateFlow<ApiResult<List<CategoryResponse>>> = _categoryState

    fun getCategories() {

        viewModelScope.launch {
            _categoryState.value = ApiResult.Loading
            _categoryState.value = CategoryRepository().getCategories()         }
    }

    fun getProductsByCategoryId(categoryId: Int) {

        viewModelScope.launch {
            _productsState.value = ApiResult.Loading
            _productsState.value = CategoryRepository().getProductsByCategoryId(id = categoryId)        }
    }
}