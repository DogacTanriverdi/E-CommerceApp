package com.dogactanriverdi.e_commerceapp.data.source.remote.service

import com.dogactanriverdi.e_commerceapp.common.Constants.ADD_ADDRESS
import com.dogactanriverdi.e_commerceapp.common.Constants.CLEAR_ADDRESSES
import com.dogactanriverdi.e_commerceapp.common.Constants.DELETE_FROM_ADDRESSES
import com.dogactanriverdi.e_commerceapp.common.Constants.GET_ADDRESSES
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE
import com.dogactanriverdi.e_commerceapp.common.Constants.STORE_NAME
import com.dogactanriverdi.e_commerceapp.common.Constants.USER_ID
import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.address.AddressResponseDto
import com.dogactanriverdi.e_commerceapp.domain.model.address.AddAddressBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.ClearAddressesBody
import com.dogactanriverdi.e_commerceapp.domain.model.address.DeleteFromAddressesBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AddressService {

    @POST(ADD_ADDRESS)
    suspend fun addAddress(
        @Header(STORE) store: String = STORE_NAME,
        @Body addAddressBody: AddAddressBody
    ): AddressResponseDto

    @GET(GET_ADDRESSES)
    suspend fun getAddresses(
        @Header(STORE) store: String = STORE_NAME,
        @Query(USER_ID) userId: String
    ): AddressResponseDto

    @POST(DELETE_FROM_ADDRESSES)
    suspend fun deleteFromAddresses(
        @Header(STORE) store: String = STORE_NAME,
        @Body deleteFromAddressBody: DeleteFromAddressesBody
    ): AddressResponseDto

    @POST(CLEAR_ADDRESSES)
    suspend fun clearAddresses(
        @Header(STORE) store: String = STORE_NAME,
        @Body clearAddressesBody: ClearAddressesBody
    ): AddressResponseDto
}