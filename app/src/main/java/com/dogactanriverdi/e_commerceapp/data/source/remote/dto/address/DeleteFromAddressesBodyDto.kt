package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address


import com.google.gson.annotations.SerializedName

data class DeleteFromAddressesBodyDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("userId")
    val userId: String?
)