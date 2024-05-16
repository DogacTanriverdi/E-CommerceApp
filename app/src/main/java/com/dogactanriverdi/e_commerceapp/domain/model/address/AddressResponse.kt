package com.dogactanriverdi.e_commerceapp.domain.model.address

data class AddressResponse(
    val message: String,
    val addresses: List<Address>,
    val status: Int
)