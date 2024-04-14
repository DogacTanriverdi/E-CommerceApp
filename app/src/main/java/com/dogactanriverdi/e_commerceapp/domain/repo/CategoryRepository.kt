package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.category.CategoriesResponseDto

interface CategoryRepository {

    suspend fun getCategories(): CategoriesResponseDto
}