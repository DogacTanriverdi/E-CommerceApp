package com.dogactanriverdi.e_commerceapp.domain.model.favorite

data class AddToFavoritesBody(
    val productId: Int,
    val userId: String
)