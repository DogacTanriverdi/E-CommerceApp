package com.dogactanriverdi.e_commerceapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.usecase.category.GetCategoriesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetProductsUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetSaleProductsUseCase
import com.dogactanriverdi.e_commerceapp.presentation.home.state.CategoriesState
import com.dogactanriverdi.e_commerceapp.presentation.home.state.ProductsState
import com.dogactanriverdi.e_commerceapp.presentation.home.state.SaleProductsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    private val saleProductsUseCase: GetSaleProductsUseCase,
    private val categoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductsState())
    val productsState: StateFlow<ProductsState> = _productsState

    private val _saleProductsState = MutableStateFlow(SaleProductsState())
    val saleProductsState: StateFlow<SaleProductsState> = _saleProductsState

    private val _categoriesState = MutableStateFlow(CategoriesState())
    val categoriesState: StateFlow<CategoriesState> = _categoriesState

    init {
        getProducts()
        getSaleProducts()
        getCategories()
    }

    private fun getProducts() {
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

    private fun getSaleProducts() {
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

    private fun getCategories() {
        viewModelScope.launch {
            categoriesUseCase().collect { categories ->
                when (categories) {

                    is Resource.Loading -> {
                        _categoriesState.value = categoriesState.value.copy(
                            isLoading = true,
                            categories = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _categoriesState.value = categoriesState.value.copy(
                            isLoading = false,
                            categories = categories.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _categoriesState.value = categoriesState.value.copy(
                            isLoading = false,
                            categories = null,
                            error = categories.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}