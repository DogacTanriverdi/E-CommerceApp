package com.dogactanriverdi.e_commerceapp.data.repo

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address.AddressResponseDto
import com.dogactanriverdi.e_commerceapp.data.source.remote.service.AddressService
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.ClearAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.DeleteFromAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val service: AddressService
) : AddressRepository {

    override suspend fun addAddress(addAddressBody: AddAddressBody): AddressResponseDto {
        return service.addAddress(addAddressBody = addAddressBody)
    }

    override suspend fun getAddresses(userId: String): AddressResponseDto {
        return service.getAddresses(userId = userId)
    }

    override suspend fun deleteFromAddresses(deleteFromAddressesBody: DeleteFromAddressesBody): AddressResponseDto {
        return service.deleteFromAddresses(deleteFromAddressBody = deleteFromAddressesBody)
    }

    override suspend fun clearAddresses(clearAddressBody: ClearAddressesBody): AddressResponseDto {
        return service.clearAddresses(clearAddressesBody = clearAddressBody)
    }
}