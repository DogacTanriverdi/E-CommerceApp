package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.address

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address.AddressResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.address.Address
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse

fun AddressResponseDto.toAddressResponse(): AddressResponse {

    val address = addresses?.let { addresses ->
        addresses.map { address ->
            Address(
                address = address.address,
                id = address.id,
                title = address.title
            )
        }
    }

    return AddressResponse(
        message = message ?: "",
        addresses = address ?: emptyList(),
        status = status ?: -1
    )
}