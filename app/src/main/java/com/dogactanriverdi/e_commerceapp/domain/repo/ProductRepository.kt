package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto

interface ProductRepository {

    suspend fun getProducts(): ProductsDto

    suspend fun getSaleProducts(): ProductsDto

    suspend fun getProductsByCategory(category: String): ProductsDto

    suspend fun searchProduct(searchQuery: String): ProductsDto
}