package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth


import com.google.gson.annotations.SerializedName

data class SignInBodyDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)