package com.example.fake_store_ecomerce

object AppConstants {
    const val BASE_URL=" https://api.escuelajs.co/api/v1/"
    const val GET_PRODUCTS_ENDPOINT = "products"
    const val GET_CATEGORIES_ENDPOINT = "categories"
    const val GET_PRODUCT_BY_ID_ENDPOINT = "products/{id}"
    const val GET_PRODUCTS_BY_CATEGORY_ID_ENDPOINT = "categories/{id}/products"
    const val DEFAULT_PAGE_SIZE = 10
    const val INITIAL_OFFSET = 0
    const val PREFETCH_DISTANCE = 3
}