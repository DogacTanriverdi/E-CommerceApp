package com.dogactanriverdi.e_commerceapp.data.source.remote.mapper.product

import com.dogactanriverdi.e_commerceapp.data.source.remote.dto.product.ProductsDto
import com.dogactanriverdi.e_commerceapp.domain.model.product.Product
import com.dogactanriverdi.e_commerceapp.domain.model.product.Products

fun ProductsDto.toProducts(): Products {

    val products = products?.let { products ->
        products.map { product ->
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
    }

    return Products(
        message = message ?: "",
        products = products ?: emptyList(),
        status = status ?: -1
    )
}