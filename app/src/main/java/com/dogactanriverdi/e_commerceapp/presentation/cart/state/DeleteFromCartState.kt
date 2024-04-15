package com.dogactanriverdi.e_commerceapp.presentation.cart.state

import com.dogactanriverdi.e_commerceapp.domain.model.cart.CartResponse

data class DeleteFromCartState(
    val isLoading: Boolean = false,
    val deleteFromCart: CartResponse? = null,
    val error: String = ""
)
