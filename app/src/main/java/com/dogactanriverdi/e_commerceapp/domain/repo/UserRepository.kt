package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.GetUserResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.UserResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody

interface UserRepository {

    suspend fun getUser(userId: String): GetUserResponseDto

    suspend fun editProfile(editProfileBody: EditProfileBody): UserResponseDto

    suspend fun changePassword(changePasswordBody: ChangePasswordBody): UserResponseDto
}