package com.dogactanriverdi.e_commerceapp.presentation.seeall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetProductsUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetSaleProductsUseCase
import com.dogactanriverdi.e_commerceapp.presentation.seeall.state.ProductsState
import com.dogactanriverdi.e_commerceapp.presentation.seeall.state.SaleProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    private val saleProductsUseCase: GetSaleProductsUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsState())
    val productsState: StateFlow<ProductsState> = _productsState

    private val _saleProductsState = MutableStateFlow(SaleProductsState())
    val saleProductsState: StateFlow<SaleProductsState> = _saleProductsState

    fun getProducts() {
        viewModelScope.launch {
            productsUseCase().collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _productsState.value = productsState.value.copy(
                            isLoading = true,
                            products = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _productsState.value = productsState.value.copy(
                            isLoading = false,
                            products = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _productsState.value = productsState.value.copy(
                            isLoading = false,
                            products = null,
                            error = products.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun getSaleProducts() {
        viewModelScope.launch {
            saleProductsUseCase().collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _saleProductsState.value = saleProductsState.value.copy(
                            isLoading = true,
                            products = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _saleProductsState.value = saleProductsState.value.copy(
                            isLoading = false,
                            products = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _saleProductsState.value = saleProductsState.value.copy(
                            isLoading = false,
                            products = null,
                            error = products.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}