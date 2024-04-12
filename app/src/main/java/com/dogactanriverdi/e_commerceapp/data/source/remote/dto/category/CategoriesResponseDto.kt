package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.category


import com.google.gson.annotations.SerializedName

data class CategoriesResponseDto(
    @SerializedName("categories")
    val categories: List<CategoryDto?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)