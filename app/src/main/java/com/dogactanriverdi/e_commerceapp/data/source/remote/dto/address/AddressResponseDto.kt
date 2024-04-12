package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address

import com.google.gson.annotations.SerializedName

data class AddressResponseDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<AddressDto?>?,
    @SerializedName("status")
    val status: Int?
)