package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth.AuthResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody

interface AuthRepository {

    suspend fun signIn(signInBody: SignInBody): AuthResponseDto

    suspend fun signUp(signUpBody: SignUpBody): AuthResponseDto
}