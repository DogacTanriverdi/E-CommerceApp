package com.dogactanriverdi.e_commerceapp.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignUpBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.auth.SignUpUseCase
import com.dogactanriverdi.e_commerceapp.presentation.signup.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun signUp(signUpBody: SignUpBody) {
        viewModelScope.launch {
            useCase(signUpBody).collect { response ->
                when (response) {

                    is Resource.Loading -> {
                        _signUpState.value = signUpState.value.copy(
                            isLoading = true,
                            signUp = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _signUpState.value = signUpState.value.copy(
                            isLoading = false,
                            signUp = response.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _signUpState.value = signUpState.value.copy(
                            isLoading = false,
                            signUp = null,
                            error = response.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}