package com.dogactanriverdi.e_commerceapp.presentation.addresses.state

import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse

data class DeleteFromAddressesState(
    val isLoading: Boolean = false,
    val deleteFromAddresses: AddressResponse? = null,
    val error: String = ""
)
