package com.dogactanriverdi.e_commerceapp.domain.usecase.favorite

import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.favorite.toFavoriteResponse
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import javax.inject.Inject

class ClearFavoritesUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(clearFavoritesBody: ClearFavoritesBody): FavoriteResponse {
        return repo.clearFavorites(clearFavoritesBody).toFavoriteResponse()
    }
}