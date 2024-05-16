package com.dogactanriverdi.e_commerceapp.domain.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address.AddressResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.ClearAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.DeleteFromAddressesBody

interface AddressRepository {

    suspend fun addAddress(addAddressBody: AddAddressBody): AddressResponseDto

    suspend fun getAddresses(userId: String): AddressResponseDto

    suspend fun deleteFromAddresses(deleteFromAddressesBody: DeleteFromAddressesBody): AddressResponseDto

    suspend fun clearAddresses(clearAddressBody: ClearAddressesBody): AddressResponseDto
}