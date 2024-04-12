package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth


import com.google.gson.annotations.SerializedName

data class SignUpBodyDto(
    @SerializedName("address")
    val address: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?
)