package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.ProductService
import com.dogactanriverdi.e_commerceapp.domain.repo.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val service: ProductService
) : ProductRepository {

    override suspend fun getProducts(): ProductsDto {
        return service.getProducts()
    }

    override suspend fun getSaleProducts(): ProductsDto {
        return service.getSaleProducts()
    }

    override suspend fun getProductsByCategory(category: String): ProductsDto {
        return service.getProductsByCategory(category = category)
    }

    override suspend fun searchProduct(searchQuery: String): ProductsDto {
        return service.searchProduct(searchQuery = searchQuery)
    }
}