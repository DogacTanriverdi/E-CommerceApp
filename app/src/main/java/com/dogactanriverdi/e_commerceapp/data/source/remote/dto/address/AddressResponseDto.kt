package com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address

import com.dogactanriverdi.e_commerceapp.domain.model.address.Address
import com.google.gson.annotations.SerializedName

data class AddressResponseDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("addresses")
    val addresses: List<Address>?,
    @SerializedName("status")
    val status: Int?
)