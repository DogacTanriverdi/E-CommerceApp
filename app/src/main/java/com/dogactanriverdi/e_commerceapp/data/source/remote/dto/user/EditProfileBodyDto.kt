package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user


import com.google.gson.annotations.SerializedName

data class EditProfileBodyDto(
    @SerializedName("address")
    val address: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("userId")
    val userId: String?
)