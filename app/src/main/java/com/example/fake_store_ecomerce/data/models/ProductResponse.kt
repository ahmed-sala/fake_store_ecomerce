package com.example.fake_store_ecomerce.data.models

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val categoryResponse: CategoryResponse? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)