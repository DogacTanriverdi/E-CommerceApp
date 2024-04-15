package com.dogactanriverdi.e_commerceapp.presentation.favorites.state

import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: Products? = null,
    val error: String = ""
)
