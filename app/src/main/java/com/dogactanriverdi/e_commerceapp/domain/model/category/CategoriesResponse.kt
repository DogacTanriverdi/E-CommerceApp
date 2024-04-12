package com.dogactanriverdi.e_commerceapp.domain.model.category

data class CategoriesResponse(
    val categories: List<Category>,
    val message: String,
    val status: Int
)