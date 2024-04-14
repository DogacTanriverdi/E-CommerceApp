package com.dogactanriverdi.e_commerceapp.domain.usecase.product

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.product.toProducts
import com.dogactanriverdi.e_commerceapp.domain.model.product.Products
import com.dogactanriverdi.e_commerceapp.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val repo: ProductRepository
) {
    operator fun invoke(searchQuery: String): Flow<Resource<Products>> {
        return flow {
            try {
                emit(Resource.Loading())
                val searchProduct = repo.searchProduct(searchQuery)
                searchProduct.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = searchProduct.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = searchProduct.toProducts()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}