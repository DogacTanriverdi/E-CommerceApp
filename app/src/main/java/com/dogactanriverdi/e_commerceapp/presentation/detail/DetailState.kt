package com.dogactanriverdi.e_commerceapp.presentation.detail

import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

data class DetailState(
    val isLoading: Boolean = false,
    val detail: Products? = null,
    val error: String = ""
)
