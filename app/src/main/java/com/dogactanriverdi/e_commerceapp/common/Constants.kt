package com.dogactanriverdi.e_commerceapp.common

object Constants {

    /*** Service ***/

    // Base
    const val STORE_NAME = "dogactnrvrdi_ecommerce"

    // Auth
    const val SIGN_IN = "sign_in"
    const val SIGN_UP = "sign_up"

    // Products
    // Endpoints
    const val STORE = "store"
    const val GET_PRODUCTS = "get_products"
    const val GET_SALE_PRODUCTS = "get_sale_products"
    const val GET_PRODUCTS_BY_CATEGORY = "get_products_by_category"
    const val SEARCH_PRODUCT = "search_product"

    // Queries
    const val CATEGORY = "category"
    const val SEARCH_QUERY = "query"

    // Cart
    // Endpoints
    const val GET_CART_PRODUCTS = "get_cart_products"
    const val ADD_TO_CART = "add_to_cart"
    const val DELETE_FROM_CART = "delete_from_cart"
    const val CLEAR_CART = "clear_cart"

    // Queries
    const val USER_ID = "userId"

    // Categories
    const val GET_CATEGORIES = "get_categories"

    // Detail
    // Endpoints
    const val GET_PRODUCT_DETAIL = "get_product_detail"

    // Queries
    const val PRODUCT_ID = "id"

    // Favorites
    const val ADD_TO_FAVORITES = "add_to_favorites"
    const val GET_FAVORITES = "get_favorites"
    const val GET_FAVORITE_COUNT = "get_favorite_count"
    const val DELETE_FROM_FAVORITES = "delete_from_favorites"
    const val CLEAR_FAVORITES = "clear_favorites"

    // Addresses
    const val ADD_ADDRESS = "add_address"
    const val GET_ADDRESSES = "get_addresses"
    const val DELETE_FROM_ADDRESSES = "delete_from_addresses"
    const val CLEAR_ADDRESSES = "clear_addresses"
}