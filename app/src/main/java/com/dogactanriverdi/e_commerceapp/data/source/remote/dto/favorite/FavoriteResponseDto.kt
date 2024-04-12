package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite


import com.google.gson.annotations.SerializedName

data class FavoriteResponseDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)