package com.dogactanriverdi.e_commerceapp.presentation.detail.state

import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteResponse

data class AddToFavoritesState(
    val isLoading: Boolean = false,
    val addToFavorites: FavoriteResponse? = null,
    val error: String = ""
)
