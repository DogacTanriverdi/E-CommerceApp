package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.ADD_TO_FAVORITES
import com.dogactanriverdi.e_commerceapp.common.Constants.CLEAR_FAVORITES
import com.dogactanriverdi.e_commerceapp.common.Constants.DELETE_FROM_FAVORITES
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_FAVORITES
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_FAVORITE_COUNT
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteCountResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FavoriteService {

    @POST(ADD_TO_FAVORITES)
    suspend fun addToFavorites(
        @Header(STORE) store: String = STORE_NAME,
        @Body addToFavoritesBody: AddToFavoritesBody
    ): FavoriteResponseDto

    @GET(GET_FAVORITES)
    suspend fun getFavorites(
        @Header(STORE) store: String = STORE_NAME,
        @Query("userId") userId: String
    ): ProductsDto

    @GET(GET_FAVORITE_COUNT)
    suspend fun getFavoriteCount(
        @Header(STORE) store: String = STORE_NAME
    ): FavoriteCountResponseDto

    @POST(DELETE_FROM_FAVORITES)
    suspend fun deleteFromFavorites(
        @Header(STORE) store: String = STORE_NAME,
        @Body deleteFromFavoritesBody: DeleteFromFavoritesBody
    ): FavoriteResponseDto

    @POST(CLEAR_FAVORITES)
    suspend fun clearFavorites(
        @Header(STORE) store: String = STORE_NAME,
        @Body clearFavoritesBody: ClearFavoritesBody
    ): FavoriteResponseDto
}