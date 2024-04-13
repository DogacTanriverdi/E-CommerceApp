package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.category

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.category.CategoriesResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.category.CategoriesResponse
import com.dogactanriverdi.e_commerceapp.domain.model.category.Category

fun CategoriesResponseDto.toCategoriesResponse(): CategoriesResponse {

    val categories = categories?.let { categories ->
        categories.map { category ->
            Category(
                image = category.image,
                name = category.name
            )
        }
    }

    return CategoriesResponse(
        categories = categories ?: emptyList(),
        message = message ?: "",
        status = status ?: -1
    )
}