package com.dogactanriverdi.e_commerceapp.presentation.signin

import com.dogactanriverdi.e_commerceapp.domain.model.auth.AuthResponse

data class SignInState(
    val isLoading: Boolean = false,
    val signIn: AuthResponse? = null,
    val error: String = ""
)
