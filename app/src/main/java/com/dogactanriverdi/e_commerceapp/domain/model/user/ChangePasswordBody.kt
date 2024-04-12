package com.dogactanriverdi.e_commerceapp.domain.model.user

data class ChangePasswordBody(
    val password: String,
    val userId: String
)