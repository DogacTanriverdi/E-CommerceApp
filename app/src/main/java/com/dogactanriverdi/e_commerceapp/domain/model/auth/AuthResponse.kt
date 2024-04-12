package com.dogactanriverdi.e_commerceapp.domain.model.auth

data class AuthResponse(
    val message: String,
    val status: Int,
    val userId: String
)