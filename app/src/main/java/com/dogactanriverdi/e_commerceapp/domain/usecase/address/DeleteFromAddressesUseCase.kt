package com.dogactanriverdi.e_commerceapp.domain.usecase.address

import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.address.toAddressResponse
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse
import com.dogactanriverdi.e_commerceapp.domain.model.address.DeleteFromAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import javax.inject.Inject

class DeleteFromAddressesUseCase @Inject constructor(
    private val repo: AddressRepository
) {
    suspend operator fun invoke(deleteFromAddressesBody: DeleteFromAddressesBody): AddressResponse {
        return repo.deleteFromAddresses(deleteFromAddressesBody).toAddressResponse()
    }
}