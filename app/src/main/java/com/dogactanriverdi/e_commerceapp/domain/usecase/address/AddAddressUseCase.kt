package com.dogactanriverdi.e_commerceapp.domain.usecase.address

import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.address.toAddressResponse
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val repo: AddressRepository
) {
    suspend operator fun invoke(addAddressBody: AddAddressBody): AddressResponse {
        return repo.addAddress(addAddressBody).toAddressResponse()
    }
}