package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite


import com.google.gson.annotations.SerializedName

data class ClearFavoritesBodyDto(
    @SerializedName("userId")
    val userId: String?
)