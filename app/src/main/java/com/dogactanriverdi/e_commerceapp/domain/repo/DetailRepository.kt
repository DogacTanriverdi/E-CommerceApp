package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.detail.DetailDto

interface DetailRepository {

    suspend fun getProductDetail(productId: Int): DetailDto
}