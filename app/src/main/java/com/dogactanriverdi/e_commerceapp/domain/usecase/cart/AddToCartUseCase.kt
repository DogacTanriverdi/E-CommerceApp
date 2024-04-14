package com.dogactanriverdi.e_commerceapp.domain.usecase.cart

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.cart.toCartResponse
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.CartResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(addToCartBody: AddToCartBody): Flow<Resource<CartResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val addToCart = repo.addToCart(addToCartBody)
                addToCart.status?.let { status ->
                    if (status == 400) {
                        emit(
                            Resource.Error(
                                message = addToCart.message ?: "Unknown error!"
                            )
                        )
                    }
                    emit(Resource.Success(data = addToCart.toCartResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}