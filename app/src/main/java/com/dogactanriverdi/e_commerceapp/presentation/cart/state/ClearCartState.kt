package com.dogactanriverdi.e_commerceapp.presentation.cart.state

import com.dogactanriverdi.e_commerceapp.domain.model.cart.CartResponse

data class ClearCartState(
    val isLoading: Boolean = false,
    val clearCart: CartResponse? = null,
    val error: String = ""
)
