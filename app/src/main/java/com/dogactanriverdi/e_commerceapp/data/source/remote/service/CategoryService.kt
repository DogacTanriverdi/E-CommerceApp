package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.GET_CATEGORIES
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.category.CategoriesResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface CategoryService {

    @GET(GET_CATEGORIES)
    suspend fun getCategories(
        @Header(STORE) store: String = STORE_NAME
    ): CategoriesResponseDto
}