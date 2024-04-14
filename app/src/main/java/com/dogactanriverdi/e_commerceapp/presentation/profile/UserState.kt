package com.dogactanriverdi.e_commerceapp.presentation.profile

import com.dogactanriverdi.e_commerceapp.domain.model.user.GetUserResponse

data class UserState(
    val isLoading: Boolean = false,
    val user: GetUserResponse? = null,
    val error: String = ""
)
