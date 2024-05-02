package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteCountResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.FavoriteService
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val service: FavoriteService
) : FavoriteRepository {

    override suspend fun addToFavorites(addToFavoritesBody: AddToFavoritesBody): FavoriteResponseDto {
        return service.addToFavorites(addToFavoritesBody = addToFavoritesBody)
    }

    override suspend fun getFavorites(userId: String): ProductsDto {
        return service.getFavorites(userId = userId)
    }

    override suspend fun getFavoriteCount(): FavoriteCountResponseDto {
        return service.getFavoriteCount()
    }

    override suspend fun deleteFromFavorites(deleteFromFavoritesBody: DeleteFromFavoritesBody): FavoriteResponseDto {
        return service.deleteFromFavorites(deleteFromFavoritesBody = deleteFromFavoritesBody)
    }

    override suspend fun clearFavorites(clearFavoritesBody: ClearFavoritesBody): FavoriteResponseDto {
        return service.clearFavorites(clearFavoritesBody = clearFavoritesBody)
    }
}