package com.example.wall_et_mobile.data.model

import androidx.compose.ui.graphics.Color
import com.example.wall_et_mobile.data.network.model.NetworkCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Card(
    val cardId : Int?,
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String?,
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

    fun asNetworkModel(): NetworkCard {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return NetworkCard(
            id = cardId,
            number = cardNumber,
            expirationDate = cardExpiration,
            fullName = cardHolder,
            cvv = cardCvv,
            type = when (cardType) { CardType.DEBIT_CARD -> "DEBIT" else -> "CREDIT" },
            createdAt = createdAt?.let { dateFormat.format(createdAt!!) },
            updatedAt = updatedAt?.let { dateFormat.format(updatedAt!!) }
        )
    }

    val backgroundColors: List<Color>
        get() = gradient.colors

}
