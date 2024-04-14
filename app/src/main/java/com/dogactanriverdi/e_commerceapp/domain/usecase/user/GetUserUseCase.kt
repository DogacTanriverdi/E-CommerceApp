package com.dogactanriverdi.e_commerceapp.domain.usecase.user

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.user.toGetUserResponse
import com.dogactanriverdi.e_commerceapp.domain.model.user.GetUserResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    operator fun invoke(userId: String): Flow<Resource<GetUserResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getUser = repo.getUser(userId)
                getUser.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = getUser.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getUser.toGetUserResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}