package com.dogactanriverdi.e_commerceapp.domain.usecase.user

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.user.toUserResponse
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.UserResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(changePasswordBody: ChangePasswordBody): Flow<Resource<UserResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val changePassword = repo.changePassword(changePasswordBody)
                changePassword.status?.let { status ->
                    if (status == 400) {
                        emit(
                            Resource.Error(
                                message = changePassword.message ?: "Unknown error!"
                            )
                        )
                    }
                    emit(Resource.Success(data = changePassword.toUserResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}