package com.example.easycommerce.helper

fun Float?.getProductPrice(price: Float): Float{
    //this --> percentage

    if (this == null)
        return price
    val remainingPricePercenteage = 1f - this
    val priceAfterOffer = remainingPricePercenteage * price

    return priceAfterOffer
}