package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.GET_PRODUCT_DETAIL
import com.dogactanriverdi.e_commerceapp.common.Constants.PRODUCT_ID
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DetailService {

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Header(STORE) store: String = STORE_NAME,
        @Query(PRODUCT_ID) productId: Int
    ): ProductsDto
}