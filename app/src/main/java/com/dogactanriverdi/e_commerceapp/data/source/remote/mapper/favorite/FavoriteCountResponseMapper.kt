package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.favorite

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.favorite.FavoriteCountResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.FavoriteCountResponse

fun FavoriteCountResponseDto.toFavoriteCountResponse(): FavoriteCountResponse {
    return FavoriteCountResponse(
        count = count ?: -1,
        message = message ?: "",
        status = status ?: -1
    )
}