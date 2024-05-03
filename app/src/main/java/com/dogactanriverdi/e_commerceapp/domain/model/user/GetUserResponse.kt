package com.dogactanriverdi.e_commerceapp.domain.model.user

data class GetUserResponse(
    val message: String,
    val status: Int,
    val user: User
)