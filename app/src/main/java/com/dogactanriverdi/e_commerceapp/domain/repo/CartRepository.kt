package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart.CartResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.DeleteFromCartBody

interface CartRepository {

    suspend fun getCartProducts(userId: String): ProductsDto

    suspend fun addToCart(addToCartBody: AddToCartBody): CartResponseDto

    suspend fun deleteFromCart(deleteFromCartBody: DeleteFromCartBody): CartResponseDto

    suspend fun clearCart(clearCartBody: ClearCartBody): CartResponseDto
}