package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user


import com.google.gson.annotations.SerializedName

data class ChangePasswordBodyDto(
    @SerializedName("password")
    val password: String?,
    @SerializedName("userId")
    val userId: String?
)