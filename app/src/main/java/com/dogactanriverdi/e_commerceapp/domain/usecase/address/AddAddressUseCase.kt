package com.dogactanriverdi.e_commerceapp.domain.usecase.address

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.address.toAddressResponse
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val repo: AddressRepository
) {
    suspend operator fun invoke(addAddressBody: AddAddressBody): Flow<Resource<AddressResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val addAddress = repo.addAddress(addAddressBody)
                addAddress.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = addAddress.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = addAddress.toAddressResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}