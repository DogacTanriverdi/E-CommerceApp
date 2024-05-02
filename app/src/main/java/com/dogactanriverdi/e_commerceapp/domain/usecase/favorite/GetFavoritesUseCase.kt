package com.dogactanriverdi.e_commerceapp.domain.usecase.favorite

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.product.toProducts
import com.dogactanriverdi.e_commerceapp.domain.model.product.Products
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    operator fun invoke(userId: String): Flow<Resource<Products>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getFavorites = repo.getFavorites(userId)
                getFavorites.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = getFavorites.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getFavorites.toProducts()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}