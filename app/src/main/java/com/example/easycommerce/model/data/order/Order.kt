package com.example.easycommerce.model.data.order

import com.example.easycommerce.model.data.Address
import com.example.easycommerce.model.data.CartProduct

data class Order(
    val orderStatus: String,
    val totalPrice: Float,
    val products: List<CartProduct>,
    val address: Address
)
