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

    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState

    fun signIn(signInBody: SignInBody) {
        viewModelScope.launch {
            useCase(signInBody).collect { response ->
                when (response) {

                    is Resource.Loading -> {
                        _signInState.value = signInState.value.copy(
                            isLoading = true,
                            signIn = null,
                            error = "",
                        )
                    }

                    is Resource.Success -> {
                        _signInState.value = signInState.value.copy(
                            isLoading = false,
                            signIn = response.data,
                            error = "",
                        )
                    }

                    is Resource.Error -> {
                        _signInState.value = signInState.value.copy(
                            isLoading = false,
                            signIn = null,
                            error = response.message ?: "Unknown error!",
                        )
                    }
                }
            }
        }
    }
}