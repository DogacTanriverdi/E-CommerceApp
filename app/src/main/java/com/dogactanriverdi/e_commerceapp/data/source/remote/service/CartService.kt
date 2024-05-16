package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.ADD_TO_CART
import com.dogactanriverdi.e_commerceapp.common.Constants.CLEAR_CART
import com.dogactanriverdi.e_commerceapp.common.Constants.DELETE_FROM_CART
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_CART_PRODUCTS
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.common.Constants.USER_ID
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.cart.CartResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.DeleteFromCartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CartService {

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(
        @Header(STORE) store: String = STORE_NAME,
        @Query(USER_ID) userId: String
    ): ProductsDto

    @POST(ADD_TO_CART)
    suspend fun addToCart(
        @Header(STORE) store: String = STORE_NAME,
        @Body addToCartBody: AddToCartBody
    ): CartResponseDto

    @POST(DELETE_FROM_CART)
    suspend fun deleteFromCart(
        @Header(STORE) store: String = STORE_NAME,
        @Body deleteFromCartBody: DeleteFromCartBody
    ): CartResponseDto

    @POST(CLEAR_CART)
    suspend fun clearCart(
        @Header(STORE) store: String = STORE_NAME,
        @Body clearCartBody: ClearCartBody
    ): CartResponseDto
}