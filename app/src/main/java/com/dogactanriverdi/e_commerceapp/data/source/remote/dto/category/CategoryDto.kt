package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.category


import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?
)