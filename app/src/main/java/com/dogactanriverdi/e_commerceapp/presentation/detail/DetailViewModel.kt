package com.dogactanriverdi.e_commerceapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.detail.GetProductDetailUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.AddToFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val detailUseCase: GetProductDetailUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState

    private val _addToFavoritesState = MutableStateFlow(AddToFavoritesState())
    val addToFavoritesState: StateFlow<AddToFavoritesState> = _addToFavoritesState

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            detailUseCase(productId).collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _detailState.value = detailState.value.copy(
                            isLoading = true,
                            detail = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _detailState.value = detailState.value.copy(
                            isLoading = false,
                            detail = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _detailState.value = detailState.value.copy(
                            isLoading = false,
                            detail = null,
                            error = products.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun addToFavorites(addToFavoritesBody: AddToFavoritesBody) {
        viewModelScope.launch {
            addToFavoritesUseCase(addToFavoritesBody).collect { favorite ->
                when (favorite) {

                    is Resource.Loading -> {
                        _addToFavoritesState.value = addToFavoritesState.value.copy(
                            isLoading = true,
                            addToFavorites = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _addToFavoritesState.value = addToFavoritesState.value.copy(
                            isLoading = false,
                            addToFavorites = favorite.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addToFavoritesState.value = addToFavoritesState.value.copy(
                            isLoading = false,
                            addToFavorites = null,
                            error = favorite.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}