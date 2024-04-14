package com.dogactanriverdi.e_commerceapp.domain.usecase.detail

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.product.toProducts
import com.dogactanriverdi.e_commerceapp.domain.model.product.Products
import com.dogactanriverdi.e_commerceapp.domain.repo.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repo: DetailRepository
) {
    operator fun invoke(productId: Int): Flow<Resource<Products>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getProductDetail = repo.getProductDetail(productId)
                getProductDetail.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = getProductDetail.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getProductDetail.toProducts()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}