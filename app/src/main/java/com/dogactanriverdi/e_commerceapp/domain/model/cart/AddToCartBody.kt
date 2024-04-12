package com.dogactanriverdi.e_commerceapp.domain.model.cart

data class AddToCartBody(
    val productId: Int,
    val userId: String
)