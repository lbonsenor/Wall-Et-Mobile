package com.example.wall_et_mobile.data.network.model;

import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.CardType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkCard(
    var id: Int?,
    var number: String,
    var expirationDate: String,
    var fullName: String,
    var cvv: String? = null,
    var type: String,
    var createdAt: String?,
    var updatedAt: String?
) {
    fun asModel(): Card {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Card(
            cardId  = id,
            cardNumber = number,
            cardExpiration = expirationDate,
            cardHolder = fullName,
            cardCvv = cvv,
            cardType = when (type) { "DEBIT" -> CardType.DEBIT_CARD else -> CardType.CREDIT_CARD },
            createdAt = createdAt?.let { dateFormat.parse(createdAt!!) },
            updatedAt = updatedAt?.let { dateFormat.parse(updatedAt!!) }
        )
    }
}