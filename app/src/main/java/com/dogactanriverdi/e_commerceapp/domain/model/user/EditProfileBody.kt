package com.dogactanriverdi.e_commerceapp.domain.model.user

data class EditProfileBody(
    val address: String,
    val name: String,
    val phone: String,
    val userId: String
)