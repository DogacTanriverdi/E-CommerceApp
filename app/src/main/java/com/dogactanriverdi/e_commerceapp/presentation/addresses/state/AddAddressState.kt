package com.dogactanriverdi.e_commerceapp.presentation.addresses.state

import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse

data class AddAddressState(
    val isLoading: Boolean = false,
    val addAddress: AddressResponse? = null,
    val error: String = ""
)