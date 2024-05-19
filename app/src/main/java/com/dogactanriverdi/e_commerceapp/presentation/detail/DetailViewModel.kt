package com.dogactanriverdi.e_commerceapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.AddToCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.detail.GetProductDetailUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.AddToFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToCartState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: GetProductDetailUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState

    private val _addToFavoritesState = MutableStateFlow(AddToFavoritesState())
    val addToFavoritesState: StateFlow<AddToFavoritesState> = _addToFavoritesState

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState: StateFlow<AddToCartState> = _addToCartState

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            detailUseCase(productId).collect { detail ->
                when (detail) {

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
                            detail = detail.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _detailState.value = detailState.value.copy(
                            isLoading = false,
                            detail = null,
                            error = detail.message ?: "Unknown error!"
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

    fun addToCart(addToCartBody: AddToCartBody) {
        viewModelScope.launch {
            addToCartUseCase(addToCartBody).collect { response ->
                when (response) {

                    is Resource.Loading -> {
                        _addToCartState.value = addToCartState.value.copy(
                            isLoading = true,
                            addToCart = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _addToCartState.value = addToCartState.value.copy(
                            isLoading = false,
                            addToCart = response.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addToCartState.value = addToCartState.value.copy(
                            isLoading = false,
                            addToCart = null,
                            error = response.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}