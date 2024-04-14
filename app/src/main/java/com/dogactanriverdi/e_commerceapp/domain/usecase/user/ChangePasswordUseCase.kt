package com.dogactanriverdi.e_commerceapp.domain.usecase.user

import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.user.toUserResponse
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.UserResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(changePasswordBody: ChangePasswordBody): UserResponse {
        return repo.changePassword(changePasswordBody).toUserResponse()
    }
}