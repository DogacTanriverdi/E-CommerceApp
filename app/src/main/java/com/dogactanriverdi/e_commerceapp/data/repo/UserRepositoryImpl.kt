package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.GetUserResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.UserResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.UserService
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody
import com.dogactanriverdi.e_commerceapp.domain.repo.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: UserService
) : UserRepository {

    override suspend fun getUser(userId: String): GetUserResponseDto {
        return service.getUser(userId = userId)
    }

    override suspend fun editProfile(editProfileBody: EditProfileBody): UserResponseDto {
        return service.editProfile(editProfileBody = editProfileBody)
    }

    override suspend fun changePassword(changePasswordBody: ChangePasswordBody): UserResponseDto {
        return service.changePassword(changePasswordBody = changePasswordBody)
    }
}