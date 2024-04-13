package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.favorite

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteResponse

fun FavoriteResponseDto.toFavoriteResponse(): FavoriteResponse {
    return FavoriteResponse(
        message = message ?: "",
        status = status ?: -1
    )
}