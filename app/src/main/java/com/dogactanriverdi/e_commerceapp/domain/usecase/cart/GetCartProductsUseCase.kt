package com.dogactanriverdi.e_commerceapp.domain.usecase.cart

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.product.toProducts
import com.dogactanriverdi.e_commerceapp.domain.model.product.Products
import com.dogactanriverdi.e_commerceapp.domain.repo.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val repo: CartRepository
) {
    operator fun invoke(userId: String): Flow<Resource<Products>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getCartProducts = repo.getCartProducts(userId)
                getCartProducts.status?.let { status ->
                    if (getCartProducts.status == 400) {
                        emit(Resource.Error(message = getCartProducts.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getCartProducts.toProducts()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}