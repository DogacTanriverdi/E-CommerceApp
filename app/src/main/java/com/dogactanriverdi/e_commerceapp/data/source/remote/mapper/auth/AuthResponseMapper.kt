package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.auth

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.auth.AuthResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.auth.AuthResponse

fun AuthResponseDto.toAuthResponse(): AuthResponse {
    return AuthResponse(
        message = message ?: "",
        status = status ?: -1,
        userId = userId ?: ""
    )
}