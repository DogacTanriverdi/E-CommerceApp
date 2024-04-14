package com.dogactanriverdi.e_commerceapp.domain.usecase.auth

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.auth.toAuthResponse
import com.dogactanriverdi.e_commerceapp.domain.model.auth.AuthResponse
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(signUpBody: SignUpBody): Flow<Resource<AuthResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val signUp = repo.signUp(signUpBody)
                signUp.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = signUp.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = signUp.toAuthResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}