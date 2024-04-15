package com.dogactanriverdi.e_commerceapp.presentation.profile.state

import com.dogactanriverdi.e_commerceapp.domain.model.user.UserResponse

data class ChangePasswordState(
    val isLoading: Boolean = false,
    val changePassword: UserResponse? = null,
    val error: String = ""
)
