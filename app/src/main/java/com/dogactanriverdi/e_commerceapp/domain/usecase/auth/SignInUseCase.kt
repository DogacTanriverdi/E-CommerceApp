package com.dogactanriverdi.e_commerceapp.domain.usecase.auth

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.auth.toAuthResponse
import com.dogactanriverdi.e_commerceapp.domain.model.auth.AuthResponse
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import com.dogactanriverdi.e_commerceapp.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(signInBody: SignInBody): Flow<Resource<AuthResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val signIn = repo.signIn(signInBody)
                signIn.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = signIn.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = signIn.toAuthResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}