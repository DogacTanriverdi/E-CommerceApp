package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite


import com.google.gson.annotations.SerializedName

data class AddToFavoritesBodyDto(
    @SerializedName("productId")
    val productId: Int?,
    @SerializedName("userId")
    val userId: String?
)