package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.SIGN_IN
import com.dogactanriverdi.e_commerceapp.common.Constants.SIGN_UP
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth.AuthResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(SIGN_IN)
    suspend fun signIn(
        @Body signInBody: SignInBody
    ): AuthResponseDto

    @POST(SIGN_UP)
    suspend fun signUp(
        @Body signUpBody: SignUpBody
    ): AuthResponseDto
}