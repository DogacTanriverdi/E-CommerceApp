package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user


import com.google.gson.annotations.SerializedName

data class GetUserResponseDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("userId")
    val userId: String?
)