package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class CartResponseDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)