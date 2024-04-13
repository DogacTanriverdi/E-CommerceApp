package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.user

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.UserResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.user.UserResponse

fun UserResponseDto.toUserResponse(): UserResponse {
    return UserResponse(
        message = message ?: "",
        status = status ?: -1
    )
}