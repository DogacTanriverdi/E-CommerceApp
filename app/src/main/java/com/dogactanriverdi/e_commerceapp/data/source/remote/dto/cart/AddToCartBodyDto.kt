package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class AddToCartBodyDto(
    @SerializedName("productId")
    val productId: Int?,
    @SerializedName("userId")
    val userId: String?
)