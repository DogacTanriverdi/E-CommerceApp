package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class DeleteFromCartBodyDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("userId")
    val userId: String?
)