package com.dogactanriverdi.e_commerceapp.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.GetProductsByCategoryUseCase
import com.dogactanriverdi.e_commerceapp.presentation.categories.state.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val useCase: GetProductsByCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state: StateFlow<ProductsState> = _state

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            useCase(category).collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            products = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            products = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
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