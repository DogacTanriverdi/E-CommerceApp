package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.detail


import com.dogactanriverdi.e_commerceapp.domain.model.detail.Product
import com.google.gson.annotations.SerializedName

data class DetailDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("product")
    val product: Product?,
    @SerializedName("status")
    val status: Int?
)