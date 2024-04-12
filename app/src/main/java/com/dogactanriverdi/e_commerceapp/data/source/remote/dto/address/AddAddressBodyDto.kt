package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address


import com.google.gson.annotations.SerializedName

data class AddAddressBodyDto(
    @SerializedName("productId")
    val productId: Int?,
    @SerializedName("userId")
    val userId: String?
)