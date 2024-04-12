package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user


import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)