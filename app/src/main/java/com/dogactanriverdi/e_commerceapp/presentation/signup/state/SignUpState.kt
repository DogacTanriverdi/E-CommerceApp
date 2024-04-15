package com.dogactanriverdi.e_commerceapp.presentation.signup.state

import com.dogactanriverdi.e_commerceapp.domain.model.auth.AuthResponse

data class SignUpState(
    val isLoading: Boolean = false,
    val signUp: AuthResponse? = null,
    val error: String = ""
)
