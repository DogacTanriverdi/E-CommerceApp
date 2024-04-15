package com.dogactanriverdi.e_commerceapp.presentation.favorites.state

import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteCountResponse

data class FavoriteCountState(
    val isLoading: Boolean = false,
    val favoriteCount: FavoriteCountResponse? = null,
    val error: String = ""
)
