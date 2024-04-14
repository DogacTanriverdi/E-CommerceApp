package com.dogactanriverdi.e_commerceapp.presentation.home.state

import com.dogactanriverdi.e_commerceapp.domain.model.category.CategoriesResponse
import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: CategoriesResponse? = null,
    val error: String = ""
)
