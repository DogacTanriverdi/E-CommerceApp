package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth.AuthResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.AuthService
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.domain.repo.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService
) : AuthRepository {

    override suspend fun signIn(signInBody: SignInBody): AuthResponseDto {
        return service.signIn(signInBody = signInBody)
    }

    override suspend fun signUp(signUpBody: SignUpBody): AuthResponseDto {
        return service.signUp(signUpBody = signUpBody)
    }
}