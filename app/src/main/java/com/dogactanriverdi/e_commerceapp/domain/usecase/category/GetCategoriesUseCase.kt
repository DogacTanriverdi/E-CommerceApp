package com.dogactanriverdi.e_commerceapp.domain.usecase.category

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.category.toCategoriesResponse
import com.dogactanriverdi.e_commerceapp.domain.model.category.CategoriesResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repo: CategoryRepository
) {
    operator fun invoke(): Flow<Resource<CategoriesResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getCategories = repo.getCategories()
                getCategories.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = getCategories.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getCategories.toCategoriesResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}