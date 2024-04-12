package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address


import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("address")
    val address: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?
)