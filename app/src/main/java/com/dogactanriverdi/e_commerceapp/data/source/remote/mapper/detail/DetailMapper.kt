package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.detail

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.detail.DetailDto
import com.dogactanriverdi.e_commerceapp.domain.model.detail.Detail
import com.dogactanriverdi.e_commerceapp.domain.model.detail.Product

fun DetailDto.toDetail(): Detail {

    val product = product?.let { product ->
        Product(
            category = product.category,
            count = product.count,
            description = product.description,
            id = product.id,
            imageOne = product.imageOne,
            imageThree = product.imageThree,
            imageTwo = product.imageTwo,
            price = product.price,
            rate = product.rate,
            salePrice = product.salePrice,
            saleState = product.saleState,
            title = product.title
        )
    }

    return Detail(
        message = message ?: "",
        product = product ?: Product(
            "",
            -1,
            "",
            -1,
            "",
            "",
            "",
            -1.0,
            -1.0,
            -1.0,
            false,
            ""
        ),
        status = status ?: -1
    )
}