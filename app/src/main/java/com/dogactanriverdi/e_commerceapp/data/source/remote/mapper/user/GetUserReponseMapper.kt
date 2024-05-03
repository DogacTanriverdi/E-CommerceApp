package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.user

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.GetUserResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.user.GetUserResponse
import com.dogactanriverdi.e_commerceapp.domain.model.user.User

fun GetUserResponseDto.toGetUserResponse(): GetUserResponse {

    val user = user?.let { user ->
        User(
            email = user.email.orEmpty(),
            name = user.name.orEmpty(),
            phone = user.phone.orEmpty(),
            userId = user.userId.orEmpty()
        )
    }

    return GetUserResponse(
        message = message.orEmpty(),
        user = user ?: User("", "", "", ""),
        status = status ?: -1
    )
}