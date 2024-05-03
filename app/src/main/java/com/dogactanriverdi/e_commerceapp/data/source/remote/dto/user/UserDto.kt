package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("userId")
    val userId: String?
)