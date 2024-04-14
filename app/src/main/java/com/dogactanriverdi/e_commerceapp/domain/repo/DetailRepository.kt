package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto

interface DetailRepository {

    suspend fun getProductDetail(productId: Int): ProductsDto
}