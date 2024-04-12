package com.dogactanriverdi.e_commerceapp.domain.model.user

data class GetUserResponse(
    val email: String,
    val message: String,
    val name: String,
    val phone: String,
    val status: Int,
    val userId: String
)