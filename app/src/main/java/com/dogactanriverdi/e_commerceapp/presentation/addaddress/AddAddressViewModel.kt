package com.dogactanriverdi.e_commerceapp.presentation.addaddress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.AddAddressUseCase
import com.dogactanriverdi.e_commerceapp.presentation.addaddress.state.AddAddressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val addAddressUseCase: AddAddressUseCase
) : ViewModel() {

    private val _addAddressState = MutableStateFlow(AddAddressState())
    val addAddressState: StateFlow<AddAddressState> = _addAddressState

    fun addAddress(addAddressBody: AddAddressBody) {
        viewModelScope.launch {
            addAddressUseCase(addAddressBody).collect { response ->
                when(response) {

                    is Resource.Loading -> {
                        _addAddressState.value = addAddressState.value.copy(
                            isLoading = true,
                            addAddress = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _addAddressState.value = addAddressState.value.copy(
                            isLoading = false,
                            addAddress = response.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addAddressState.value = addAddressState.value.copy(
                            isLoading = false,
                            addAddress = null,
                            error = response.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}