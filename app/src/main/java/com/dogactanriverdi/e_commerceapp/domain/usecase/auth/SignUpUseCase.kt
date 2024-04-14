package com.dogactanriverdi.e_commerceapp.domain.usecase.auth

import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.auth.toAuthResponse
import com.dogactanriverdi.e_commerceapp.domain.model.auth.AuthResponse
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.domain.repo.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(signUpBody: SignUpBody): AuthResponse {
        return repo.signUp(signUpBody).toAuthResponse()
    }
}