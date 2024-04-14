package com.dogactanriverdi.e_commerceapp.domain.usecase.cart

import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.cart.toCartResponse
import com.dogactanriverdi.e_commerceapp.domain.model.cart.CartResponse
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.domain.repo.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(clearCartBody: ClearCartBody): CartResponse {
        return repo.clearCart(clearCartBody).toCartResponse()
    }
}