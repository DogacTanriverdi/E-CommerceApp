package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.user

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.GetUserResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.user.GetUserResponse

fun GetUserResponseDto.toGetUserResponse(): GetUserResponse {
    return GetUserResponse(
        email = email ?: "",
        message = message ?: "",
        name = name ?: "",
        phone = phone ?: "",
        status = status ?: -1,
        userId = userId ?: ""
    )
}