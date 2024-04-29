package com.dogactanriverdi.e_commerceapp.presentation.categories.state

import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class ProductsState(
    val isLoading: Boolean = false,
    val products: Products? = null,
    val error: String = ""
)
