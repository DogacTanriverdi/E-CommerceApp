package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product

import com.dogactanriverdi.e_commerceapp.domain.model.product.Product
import com.google.gson.annotations.SerializedName

data class ProductsDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<Product?>?,
    @SerializedName("status")
    val status: Int?
)