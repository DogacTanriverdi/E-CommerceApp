package com.dogactanriverdi.e_commerceapp.presentation.detail.state

import com.dogactanriverdi.e_commerceapp.domain.model.cart.CartResponse

data class AddToCartState(
    val isLoading: Boolean = false,
    val addToCart: CartResponse? = null,
    val error: String = ""
)
