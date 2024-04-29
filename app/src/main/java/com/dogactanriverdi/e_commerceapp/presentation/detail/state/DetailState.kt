package com.dogactanriverdi.e_commerceapp.presentation.detail.state

import com.dogactanriverdi.e_commerceapp.domain.model.detail.Detail

data class DetailState(
    val isLoading: Boolean = false,
    val detail: Detail? = null,
    val error: String = ""
)
