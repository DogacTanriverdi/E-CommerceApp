package com.dogactanriverdi.e_commerceapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.usecase.product.SearchProductUseCase
import com.dogactanriverdi.e_commerceapp.presentation.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchProductUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    fun searchProduct(searchQuery: String) {
        viewModelScope.launch {
            useCase(searchQuery).collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _searchState.value = searchState.value.copy(
                            isLoading = true,
                            search = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _searchState.value = searchState.value.copy(
                            isLoading = false,
                            search = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _searchState.value = searchState.value.copy(
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