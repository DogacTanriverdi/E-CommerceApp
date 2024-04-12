package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class ClearCartBodyDto(
    @SerializedName("userId")
    val userId: String?
)