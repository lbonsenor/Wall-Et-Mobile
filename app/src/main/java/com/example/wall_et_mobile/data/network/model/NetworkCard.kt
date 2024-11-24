package com.example.wall_et_mobile.data.network.model;

import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.CardType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class NetworkCardRequest(
    val fullName: String,
    val cvv: String,
    val number: String,
    val expirationDate: String,
    val type: String
)

@Serializable
data class NetworkCard(
    val id: Int,
    val number: String,
    val expirationDate: String,
    val fullName: String,
    val type: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)  {
    fun asModel(): Card {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Card(
            cardId  = id,
            cardNumber = number,
            cardExpiration = expirationDate,
            cardHolder = fullName,
            cardType = when (type) { "DEBIT" -> CardType.DEBIT_CARD else -> CardType.CREDIT_CARD },
            cardCvv = null,
            createdAt = createdAt?.let { runCatching { dateFormat.parse(it) }.getOrNull() },
            updatedAt = updatedAt?.let { runCatching { dateFormat.parse(it) }.getOrNull() }
        )
    }
}