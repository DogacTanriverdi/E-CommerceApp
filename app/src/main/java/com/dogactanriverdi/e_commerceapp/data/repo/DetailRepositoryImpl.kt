package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.detail.DetailDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.DetailService
import com.dogactanriverdi.e_commerceapp.domain.repo.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val service: DetailService
) : DetailRepository {

    override suspend fun getProductDetail(productId: Int): DetailDto {
        return service.getProductDetail(productId = productId)
    }
}