package com.dogactanriverdi.e_commerceapp.domain.usecase.address

import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.address.toAddressResponse
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddressResponse
import com.dogactanriverdi.e_commerceapp.domain.repo.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val repo: AddressRepository
) {
    operator fun invoke(userId: String): Flow<Resource<AddressResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val getAddresses = repo.getAddresses(userId)
                getAddresses.status?.let { status ->
                    if (status == 400) {
                        emit(Resource.Error(message = getAddresses.message ?: "Unknown error!"))
                    }
                    emit(Resource.Success(data = getAddresses.toAddressResponse()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error!"))
            }
        }
    }
}