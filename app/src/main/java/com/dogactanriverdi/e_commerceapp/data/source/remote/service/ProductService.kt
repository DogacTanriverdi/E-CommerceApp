package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.CATEGORY
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_PRODUCTS
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_PRODUCTS_BY_CATEGORY
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_SALE_PRODUCTS
import com.dogactanriverdi.e_commerceapp.common.Constants.SEARCH_PRODUCT
import com.dogactanriverdi.e_commerceapp.common.Constants.SEARCH_QUERY
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductService {

    @GET(GET_PRODUCTS)
    suspend fun getProducts(
        @Header(STORE) store: String = STORE_NAME
    ): ProductsDto

    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(
        @Header(STORE) store: String = STORE_NAME
    ): ProductsDto

    @GET(GET_PRODUCTS_BY_CATEGORY)
    suspend fun getProductsByCategory(
        @Header(STORE) store: String = STORE_NAME,
        @Query(CATEGORY) category: String
    ): ProductsDto

    @GET(SEARCH_PRODUCT)
    suspend fun searchProduct(
        @Header(STORE) store: String = STORE_NAME,
        @Query(SEARCH_QUERY) searchQuery: String
    ): ProductsDto
}