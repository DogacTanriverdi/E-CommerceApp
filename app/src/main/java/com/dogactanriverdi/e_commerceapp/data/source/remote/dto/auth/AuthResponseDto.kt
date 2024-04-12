package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth


import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("userId")
    val userId: String?
)