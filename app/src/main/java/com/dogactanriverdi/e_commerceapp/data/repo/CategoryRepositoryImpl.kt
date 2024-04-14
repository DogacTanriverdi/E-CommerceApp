package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.category.CategoriesResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.CategoryService
import com.dogactanriverdi.e_commerceapp.domain.repo.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val service: CategoryService
) : CategoryRepository {

    override suspend fun getCategories(): CategoriesResponseDto {
        return service.getCategories()
    }
}