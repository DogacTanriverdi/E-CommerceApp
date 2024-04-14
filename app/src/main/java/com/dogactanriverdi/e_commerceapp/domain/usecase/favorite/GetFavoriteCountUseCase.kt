package com.dogactanriverdi.e_commerceapp.domain.usecase.favorite

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.favorite.toFavoriteCountResponse
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteCountResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteCountUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    operator fun invoke(): Flow<Resource<FavoriteCountResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getFavoriteCount = repo.getFavoriteCount()
                getFavoriteCount.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = getFavoriteCount.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getFavoriteCount.toFavoriteCountResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}