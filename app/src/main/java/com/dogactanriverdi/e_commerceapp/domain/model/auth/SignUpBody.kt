package com.dogactanriverdi.e_commerceapp.domain.model.auth

data class SignUpBody(
    val address: String,
    val email: String,
    val name: String,
    val password: String,
    val phone: String
)