package com.dogactanriverdi.e_commerceapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.cart.AddToCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.ClearCartBody
import com.dogactanriverdi.e_commerceapp.domain.model.cart.DeleteFromCartBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.AddToCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.ClearCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.DeleteFromCartUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.cart.GetCartProductsUseCase
import com.dogactanriverdi.e_commerceapp.presentation.cart.state.CartProductsState
import com.dogactanriverdi.e_commerceapp.presentation.cart.state.ClearCartState
import com.dogactanriverdi.e_commerceapp.presentation.cart.state.DeleteFromCartState
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToCartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val deleteFromCartUseCase: DeleteFromCartUseCase,
    private val cartProductsUseCase: GetCartProductsUseCase
) : ViewModel() {

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState: StateFlow<AddToCartState> = _addToCartState

    private val _deleteFromCartState = MutableStateFlow(DeleteFromCartState())
    val deleteFromCartState: StateFlow<DeleteFromCartState> = _deleteFromCartState

    private val _clearCartState = MutableStateFlow(ClearCartState())
    val clearCartState: StateFlow<ClearCartState> = _clearCartState

    private val _cartProductsState = MutableStateFlow(CartProductsState())
    val cartProductsState: StateFlow<CartProductsState> = _cartProductsState

    fun addToCart(addToCartBody: AddToCartBody) {
        viewModelScope.launch {
            addToCartUseCase(addToCartBody).collect { cart ->
                when (cart) {

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
                            addToCart = cart.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addToCartState.value = addToCartState.value.copy(
                            isLoading = false,
                            addToCart = null,
                            error = cart.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun deleteFomCart(deleteFromCartBody: DeleteFromCartBody) {
        viewModelScope.launch {
            deleteFromCartUseCase(deleteFromCartBody).collect { cart ->
                when (cart) {

                    is Resource.Loading -> {
                        _deleteFromCartState.value = deleteFromCartState.value.copy(
                            isLoading = true,
                            deleteFromCart = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _deleteFromCartState.value = deleteFromCartState.value.copy(
                            isLoading = false,
                            deleteFromCart = cart.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _deleteFromCartState.value = deleteFromCartState.value.copy(
                            isLoading = false,
                            deleteFromCart = null,
                            error = cart.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun clearCart(clearCartBody: ClearCartBody) {
        viewModelScope.launch {
            clearCartUseCase(clearCartBody).collect { cart ->
                when (cart) {

                    is Resource.Loading -> {
                        _clearCartState.value = clearCartState.value.copy(
                            isLoading = true,
                            clearCart = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _clearCartState.value = clearCartState.value.copy(
                            isLoading = false,
                            clearCart = cart.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _clearCartState.value = clearCartState.value.copy(
                            isLoading = false,
                            clearCart = null,
                            error = cart.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun cartProducts(userId: String) {
        viewModelScope.launch {
            cartProductsUseCase(userId).collect { cart ->
                when (cart) {

                    is Resource.Loading -> {
                        _cartProductsState.value = cartProductsState.value.copy(
                            isLoading = true,
                            products = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _cartProductsState.value = cartProductsState.value.copy(
                            isLoading = false,
                            products = cart.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _cartProductsState.value = cartProductsState.value.copy(
                            isLoading = false,
                            products = null,
                            error = cart.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}