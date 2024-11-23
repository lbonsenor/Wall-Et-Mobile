package com.example.wall_et_mobile.data.model

import androidx.compose.ui.graphics.Color
import com.example.wall_et_mobile.data.network.model.NetworkCard
import com.example.wall_et_mobile.data.network.model.NetworkCardRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Card(
    val cardId : Int?,
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String? = null,
    val gradient: CardGradient = CardGradient.random(),
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
) {
    fun getCardBrand() : CardBrand{
        CardBrand.entries.forEach{brand ->
            if (brand.validate(cardNumber)) {
                return brand
            }
        }
        return CardBrand.VISA
    }

    fun asNetworkRequest(): NetworkCardRequest {
        return NetworkCardRequest(
            fullName = cardHolder,
            cvv = cardCvv ?: throw IllegalStateException("CVV required for card creation"),
            number = cardNumber,
            expirationDate = cardExpiration,
            type = when (cardType) {
                CardType.DEBIT_CARD -> "DEBIT"
                else -> "CREDIT"
            }
        )
    }

    fun asNetworkCard(): NetworkCard {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return NetworkCard(
            id = cardId ?: 0,
            number = cardNumber,
            expirationDate = cardExpiration,
            fullName = cardHolder,
            type = when (cardType) {
                CardType.DEBIT_CARD -> "DEBIT"
                else -> "CREDIT"},
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString()
        )
    }

    val backgroundColors: List<Color>
        get() = gradient.colors

}
