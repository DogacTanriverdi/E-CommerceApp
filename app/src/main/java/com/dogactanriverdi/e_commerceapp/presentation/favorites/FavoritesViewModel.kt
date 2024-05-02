package com.dogactanriverdi.e_commerceapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactanriverdi.e_commerceapp.common.Resource
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.AddToFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.ClearFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.model.favorite.DeleteFromFavoritesBody
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.AddToFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.ClearFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.DeleteFromFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.GetFavoriteCountUseCase
import com.dogactanriverdi.e_commerceapp.domain.usecase.favorite.GetFavoritesUseCase
import com.dogactanriverdi.e_commerceapp.presentation.detail.state.AddToFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.ClearFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.DeleteFromFavoritesState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.FavoriteCountState
import com.dogactanriverdi.e_commerceapp.presentation.favorites.state.FavoritesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val clearFavoritesUseCase: ClearFavoritesUseCase,
    private val favoriteCountUseCase: GetFavoriteCountUseCase,
    private val favoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _addToFavoritesState = MutableStateFlow(AddToFavoritesState())
    val addToFavoritesState: StateFlow<AddToFavoritesState> = _addToFavoritesState

    private val _deleteFromFavoritesState = MutableStateFlow(DeleteFromFavoritesState())
    val deleteFromFavoritesState: StateFlow<DeleteFromFavoritesState> = _deleteFromFavoritesState

    private val _clearFavoritesState = MutableStateFlow(ClearFavoritesState())
    val clearFavoritesState: StateFlow<ClearFavoritesState> = _clearFavoritesState

    private val _favoriteCountState = MutableStateFlow(FavoriteCountState())
    val favoriteCountState: StateFlow<FavoriteCountState> = _favoriteCountState

    private val _favoritesState = MutableStateFlow(FavoritesState())
    val favoritesState: StateFlow<FavoritesState> = _favoritesState

    fun addToFavorites(addToFavoritesBody: AddToFavoritesBody) {
        viewModelScope.launch {
            addToFavoritesUseCase(addToFavoritesBody).collect { favorites ->
                when (favorites) {

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
                            addToFavorites = favorites.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _addToFavoritesState.value = addToFavoritesState.value.copy(
                            isLoading = false,
                            addToFavorites = null,
                            error = favorites.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun deleteFromFavorites(deleteFromFavoritesBody: DeleteFromFavoritesBody) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(deleteFromFavoritesBody).collect { favorites ->
                when (favorites) {

                    is Resource.Loading -> {
                        _deleteFromFavoritesState.value = deleteFromFavoritesState.value.copy(
                            isLoading = true,
                            deleteFromFavorites = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _deleteFromFavoritesState.value = deleteFromFavoritesState.value.copy(
                            isLoading = false,
                            deleteFromFavorites = favorites.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _deleteFromFavoritesState.value = deleteFromFavoritesState.value.copy(
                            isLoading = false,
                            deleteFromFavorites = null,
                            error = favorites.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun clearFavorites(clearFavoritesBody: ClearFavoritesBody) {
        viewModelScope.launch {
            clearFavoritesUseCase(clearFavoritesBody).collect { favorites ->
                when (favorites) {

                    is Resource.Loading -> {
                        _clearFavoritesState.value = clearFavoritesState.value.copy(
                            isLoading = true,
                            clearFavorites = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _clearFavoritesState.value = clearFavoritesState.value.copy(
                            isLoading = false,
                            clearFavorites = favorites.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _clearFavoritesState.value = clearFavoritesState.value.copy(
                            isLoading = false,
                            clearFavorites = null,
                            error = favorites.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun favoriteCount() {
        viewModelScope.launch {
            favoriteCountUseCase().collect { favorites ->
                when (favorites) {

                    is Resource.Loading -> {
                        _favoriteCountState.value = favoriteCountState.value.copy(
                            isLoading = true,
                            favoriteCount = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _favoriteCountState.value = favoriteCountState.value.copy(
                            isLoading = false,
                            favoriteCount = favorites.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _favoriteCountState.value = favoriteCountState.value.copy(
                            isLoading = false,
                            favoriteCount = null,
                            error = favorites.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }

    fun getFavorites(userId: String) {
        viewModelScope.launch {
            favoritesUseCase(userId).collect { products ->
                when (products) {

                    is Resource.Loading -> {
                        _favoritesState.value = favoritesState.value.copy(
                            isLoading = true,
                            favorites = null,
                            error = ""
                        )
                    }

                    is Resource.Success -> {
                        _favoritesState.value = favoritesState.value.copy(
                            isLoading = false,
                            favorites = products.data,
                            error = ""
                        )
                    }

                    is Resource.Error -> {
                        _favoritesState.value = favoritesState.value.copy(
                            isLoading = false,
                            favorites = null,
                            error = products.message ?: "Unknown error!"
                        )
                    }
                }
            }
        }
    }
}