package com.dogactanriverdi.e_commerceapp.domain.usecase.favorite

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.favorite.toFavoriteResponse
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(addToFavoritesBody: AddToFavoritesBody): Flow<Resource<FavoriteResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val addToFavorites = repo.addToFavorites(addToFavoritesBody)
                addToFavorites.status?.let { status ->
                    if (status == 400) {
                        emit(
                            Resource.Error(
                                message = addToFavorites.message ?: "Unknown error!"
                            )
                        )
                    }
                    emit(Resource.Success(data = addToFavorites.toFavoriteResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}