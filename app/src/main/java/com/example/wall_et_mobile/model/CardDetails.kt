package com.example.wall_et_mobile.model

import androidx.compose.ui.graphics.Color

data class CardDetails(
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String,
    val gradient: CardGradient = CardGradient.random(),
    val cardBrand: CardBrand = getCardBrand(cardNumber)
) {
    val backgroundColors: List<Color>
        get() = gradient.colors
}

fun getCardBrand(cardNumber: String) : CardBrand{
    CardBrand.entries.forEach { cardBrand ->
        if (cardBrand.validate(cardNumber)) return cardBrand
    }
    return CardBrand.VISA
}
