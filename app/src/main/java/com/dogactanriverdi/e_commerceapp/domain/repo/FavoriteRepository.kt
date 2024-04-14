package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteCountResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody

interface FavoriteRepository {

    suspend fun addToFavorites(addToFavoritesBody: AddToFavoritesBody): FavoriteResponseDto

    suspend fun getFavorites(): ProductsDto

    suspend fun getFavoriteCount(): FavoriteCountResponseDto

    suspend fun deleteFromFavorites(deleteFromFavoritesBody: DeleteFromFavoritesBody): FavoriteResponseDto

    suspend fun clearFavorites(clearFavoritesBody: ClearFavoritesBody): FavoriteResponseDto
}