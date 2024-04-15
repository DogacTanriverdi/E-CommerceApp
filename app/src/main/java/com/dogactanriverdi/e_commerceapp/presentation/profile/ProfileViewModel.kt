package com.dogactanriverdi.e_commerceapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.user.ChangePasswordBody
import com.dogactanriverdi.e_commerceapp.domain.model.user.EditProfileBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.user.ChangePasswordUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.user.EditProfileUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.user.GetUserUseCase
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.ChangePasswordState
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.EditProfileState
import com.dogactanriverdi.e_commerceapp.presentation.profile.state.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userUseCase: GetUserUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState

    private val _editProfileState = MutableStateFlow(EditProfileState())
    val editProfileState: StateFlow<EditProfileState> = _editProfileState

    private val _changePasswordState = MutableStateFlow(ChangePasswordState())
    val changePasswordState: StateFlow<ChangePasswordState> = _changePasswordState

    fun getUser(userId: String) {
        viewModelScope.launch {
            userUseCase(userId).collect { user ->
                when (user) {

                    is Resource.Loading -> {
                        _userState.value = userState.value.copy(
                            isLoading = true,
                            user = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _userState.value = userState.value.copy(
                            isLoading = false,
                            user = user.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _userState.value = userState.value.copy(
                            isLoading = false,
                            user = null,
                            error = user.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun editProfile(editProfileBody: EditProfileBody) {
        viewModelScope.launch {
            editProfileUseCase(editProfileBody).collect { user ->
                when (user) {

                    is Resource.Loading -> {
                        _editProfileState.value = editProfileState.value.copy(
                            isLoading = true,
                            editProfile = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _editProfileState.value = editProfileState.value.copy(
                            isLoading = false,
                            editProfile = user.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _editProfileState.value = editProfileState.value.copy(
                            isLoading = false,
                            editProfile = null,
                            error = user.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun changePassword(changePasswordBody: ChangePasswordBody) {
        viewModelScope.launch {
            changePasswordUseCase(changePasswordBody).collect { user ->
                when (user) {

                    is Resource.Loading -> {
                        _changePasswordState.value = changePasswordState.value.copy(
                            isLoading = true,
                            changePassword = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _changePasswordState.value = changePasswordState.value.copy(
                            isLoading = false,
                            changePassword = user.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _changePasswordState.value = changePasswordState.value.copy(
                            isLoading = false,
                            changePassword = null,
                            error = user.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}