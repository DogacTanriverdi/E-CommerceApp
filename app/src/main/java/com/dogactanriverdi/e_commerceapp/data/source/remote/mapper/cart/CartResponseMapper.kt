package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.cart

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart.CartResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.cart.CartResponse

fun CartResponseDto.toCartResponse(): CartResponse {
    return CartResponse(
        message = message ?: "",
        status = status ?: -1
    )
}