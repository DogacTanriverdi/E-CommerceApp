package com.dogactanriverdi.e_commerceapp.presentation.cart.state

import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class CartProductsState(
    val isLoading: Boolean = false,
    val products: Products? = null,
    val error: String = ""
)
