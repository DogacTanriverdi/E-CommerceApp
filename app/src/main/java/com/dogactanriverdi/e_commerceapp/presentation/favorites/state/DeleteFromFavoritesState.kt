package com.dogactanriverdi.e_commerceapp.presentation.favorites.state

import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteResponse

data class DeleteFromFavoritesState(
    val isLoading: Boolean = false,
    val deleteFromFavorites: FavoriteResponse? = null,
    val error: String = ""
)
