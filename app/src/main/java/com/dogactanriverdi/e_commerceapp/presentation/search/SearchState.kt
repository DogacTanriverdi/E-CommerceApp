package com.dogactanriverdi.e_commerceapp.presentation.search

import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class SearchState(
    val isLoading: Boolean = false,
    val search: Products? = null,
    val error: String = ""
)