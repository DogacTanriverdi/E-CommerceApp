package com.dogactanriverdi.e_commerceapp.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.auth.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val useCase: SignUpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    private fun signUp(signUpBody: SignUpBody) {
        viewModelScope.launch {
            useCase(signUpBody).collect { authResponse ->
                when (authResponse) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            signUp = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            signUp = authResponse.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            signUp = null,
                            error = authResponse.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}