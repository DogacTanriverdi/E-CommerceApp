package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart.CartResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.CartService
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.DeleteFromCartBody
import com.dogactanriverdi.e_commerceapp.domain.repo.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val service: CartService
) : CartRepository {

    override suspend fun getCartProducts(userId: String): ProductsDto {
        return service.getCartProducts(userId = userId)
    }

    override suspend fun addToCart(addToCartBody: AddToCartBody): CartResponseDto {
        return service.addToCart(addToCartBody = addToCartBody)
    }

    override suspend fun deleteFromCart(deleteFromCartBody: DeleteFromCartBody): CartResponseDto {
        return service.deleteFromCart(deleteFromCartBody = deleteFromCartBody)
    }

    override suspend fun clearCart(clearCartBody: ClearCartBody): CartResponseDto {
        return service.clearCart(clearCartBody = clearCartBody)
    }
}