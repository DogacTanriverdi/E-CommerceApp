package com.dogactanriverdi.e_commerceapp.presentation.addresses.state

import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse

data class ClearAddressesState(
    val isLoading: Boolean = false,
    val clearAddresses: AddressResponse? = null,
    val error: String = ""
)