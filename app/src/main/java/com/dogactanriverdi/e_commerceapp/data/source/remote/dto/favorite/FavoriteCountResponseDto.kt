package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite


import com.google.gson.annotations.SerializedName

data class FavoriteCountResponseDto(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)