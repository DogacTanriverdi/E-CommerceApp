package com.dogactanriverdi.e_commerceapp.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.auth.SignInBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.auth.SignInUseCase
import com.dogactanriverdi.e_commerceapp.presentation.signin.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCase: SignInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    fun signIn(signInBody: SignInBody) {
        viewModelScope.launch {
            useCase(signInBody).collect { authResponse ->
                when (authResponse) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            signIn = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            signIn = authResponse.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            signIn = null,
                            error = authResponse.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}