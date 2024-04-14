package com.dogactanriverdi.e_commerceapp.presentation.home.state

import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class SaleProductsState(
    val isLoading: Boolean = false,
    val products: Products? = null,
    val error: String = ""
)
