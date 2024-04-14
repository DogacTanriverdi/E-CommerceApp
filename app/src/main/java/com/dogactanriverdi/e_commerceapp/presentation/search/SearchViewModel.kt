package com.dogactanriverdi.e_commerceapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.SearchProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val useCase: SearchProductUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private fun searchProduct(searchQuery: String) {
        viewModelScope.launch {
            useCase(searchQuery).collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            search = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            search = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            search = null,
                            error = products.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}