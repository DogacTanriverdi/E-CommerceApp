package com.dogactanriverdi.e_commerceapp.presentation.favorites.state

import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteResponse

data class ClearFavoritesState(
    val isLoading: Boolean = false,
    val clearFavorites: FavoriteResponse? = null,
    val error: String = ""
)
