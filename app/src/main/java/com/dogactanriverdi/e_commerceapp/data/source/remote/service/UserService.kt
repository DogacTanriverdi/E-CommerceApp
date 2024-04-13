package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.CHANGE_PASSWORD
import com.dogactanriverdi.e_commerceapp.common.Constants.EDIT_PROFILE
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_USER
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.common.Constants.USER_ID
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.GetUserResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.user.UserResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET(GET_USER)
    suspend fun getUser(
        @Header(STORE) store: String = STORE_NAME,
        @Query(USER_ID) userId: String
    ): GetUserResponseDto

    @POST(EDIT_PROFILE)
    suspend fun editProfile(
        @Header(STORE) store: String = STORE_NAME,
        @Body editProfileBody: EditProfileBody
    ): UserResponseDto

    @POST(CHANGE_PASSWORD)
    suspend fun changePassword(
        @Header(STORE) store: String = STORE_NAME,
        @Body changePasswordBody: ChangePasswordBody
    ): UserResponseDto
}