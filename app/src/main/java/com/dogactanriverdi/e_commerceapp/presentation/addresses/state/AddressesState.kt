package com.dogactanriverdi.e_commerceapp.presentation.addresses.state

import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse

data class AddressesState(
    val isLoading: Boolean = false,
    val addresses: AddressResponse? = null,
    val error: String = ""
)
