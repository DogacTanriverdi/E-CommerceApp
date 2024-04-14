package com.dogactanriverdi.e_commerceapp.presentation.profile

import com.dogactanriverdi.e_commerceapp.domain.model.user.UserResponse

data class EditProfileState(
    val isLoading: Boolean = false,
    val editProfile: UserResponse? = null,
    val error: String = ""
)
