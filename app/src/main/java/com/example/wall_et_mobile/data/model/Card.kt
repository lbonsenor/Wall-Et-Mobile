package com.example.wall_et_mobile.data.model

import androidx.compose.ui.graphics.Color

data class Card(
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String,
    val gradient: CardGradient = CardGradient.random(),
) {
    fun getCardBrand() : CardBrand{
        if (cardNumber.startsWith("4")) return CardBrand.VISA
        else if (cardNumber.startsWith("5")) return CardBrand.MASTERCARD
        return CardBrand.VISA
    }

    val backgroundColors: List<Color>
        get() = gradient.colors

}
