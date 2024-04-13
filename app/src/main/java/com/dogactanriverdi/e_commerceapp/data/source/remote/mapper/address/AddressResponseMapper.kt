package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.address

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address.AddressResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.address.Address
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse

fun AddressResponseDto.toAddressResponse(): AddressResponse {

    val address = products?.let { products ->
        products.map { product ->
            Address(
                address = product.address,
                id = product.id,
                title = product.title
            )
        }
    }

    return AddressResponse(
        message = message ?: "",
        products = address ?: emptyList(),
        status = status ?: -1
    )
}