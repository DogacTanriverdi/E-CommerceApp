package com.dogactanriverdi.e_commerceapp.presentation.addresses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.ClearAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.DeleteFromAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.AddAddressUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.ClearAddressesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.DeleteFromAddressesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.address.GetAddressesUseCase
import com.dogactanriverdi.e_commerceapp.presentation.addresses.state.AddAddressState
import com.dogactanriverdi.e_commerceapp.presentation.addresses.state.AddressesState
import com.dogactanriverdi.e_commerceapp.presentation.addresses.state.ClearAddressesState
import com.dogactanriverdi.e_commerceapp.presentation.addresses.state.DeleteFromAddressesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val addressesUseCase: GetAddressesUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val deleteFromAddressesUseCase: DeleteFromAddressesUseCase,
    private val clearAddressesUseCase: ClearAddressesUseCase
) : ViewModel() {

    private val _addressesState = MutableStateFlow(AddressesState())
    val addressesState: StateFlow<AddressesState> = _addressesState

    private val _addAddressState = MutableStateFlow(AddAddressState())
    val addAddressState: StateFlow<AddAddressState> = _addAddressState

    private val _deleteFromAddressesState = MutableStateFlow(DeleteFromAddressesState())
    val deleteFromAddressesState: StateFlow<DeleteFromAddressesState> = _deleteFromAddressesState

    private val _clearAddressesState = MutableStateFlow(ClearAddressesState())
    val clearAddressesState: StateFlow<ClearAddressesState> = _clearAddressesState

    fun getAddresses(userId: String) {
        viewModelScope.launch {
            addressesUseCase(userId).collect { addresses ->
                when (addresses) {

                    is Resource.Loading -> {
                        _addressesState.value = addressesState.value.copy(
                            isLoading = true,
                            addresses = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _addressesState.value = addressesState.value.copy(
                            isLoading = false,
                            addresses = addresses.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addressesState.value = addressesState.value.copy(
                            isLoading = false,
                            addresses = null,
                            error = addresses.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun addAddress(addAddressBody: AddAddressBody) {
        viewModelScope.launch {
            addAddressUseCase(addAddressBody).collect { addresses ->
                when (addresses) {

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
                            addAddress = addresses.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addAddressState.value = addAddressState.value.copy(
                            isLoading = false,
                            addAddress = null,
                            error = addresses.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun deleteFromAddresses(deleteFromAddressesBody: DeleteFromAddressesBody) {
        viewModelScope.launch {
            deleteFromAddressesUseCase(deleteFromAddressesBody).collect { addresses ->
                when (addresses) {

                    is Resource.Loading -> {
                        _deleteFromAddressesState.value = deleteFromAddressesState.value.copy(
                            isLoading = true,
                            deleteFromAddresses = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _deleteFromAddressesState.value = deleteFromAddressesState.value.copy(
                            isLoading = false,
                            deleteFromAddresses = addresses.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _deleteFromAddressesState.value = deleteFromAddressesState.value.copy(
                            isLoading = false,
                            deleteFromAddresses = null,
                            error = addresses.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun clearAddresses(clearAddressesBody: ClearAddressesBody) {
        viewModelScope.launch {
            clearAddressesUseCase(clearAddressesBody).collect { addresses ->
                when (addresses) {

                    is Resource.Loading -> {
                        _clearAddressesState.value = clearAddressesState.value.copy(
                            isLoading = true,
                            clearAddresses = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _clearAddressesState.value = clearAddressesState.value.copy(
                            isLoading = false,
                            clearAddresses = addresses.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _clearAddressesState.value = clearAddressesState.value.copy(
                            isLoading = false,
                            clearAddresses = null,
                            error = addresses.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}