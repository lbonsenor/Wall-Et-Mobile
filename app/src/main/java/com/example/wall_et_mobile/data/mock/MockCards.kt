package com.example.wall_et_mobile.data.mock

import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.CardGradient
import com.example.wall_et_mobile.data.model.CardType

object MockCards {
    val sampleCards = listOf(
        Card(
            cardNumber = "4234 5678 9012 3456",
            cardType = CardType.CREDIT_CARD,
            cardHolder = "John Doe",
            cardExpiration = "01/25",
            cardCvv = "123",
            gradient = CardGradient.PURPLE_INDIGO,
            cardId = 0
        ),
        Card(
            cardNumber = "5876 5432 1098 7654",
            cardType = CardType.DEBIT_CARD,
            cardHolder = "Jane Smith",
            cardExpiration = "03/29",
            cardCvv = "456",
            gradient = CardGradient.DARK_TEAL,
            cardId = 1,
        ),
        Card(
            cardNumber = "4131 5234 6213 1231",
            cardType = CardType.DEBIT_CARD,
            cardHolder = "Juan José",
            cardExpiration = "12/31",
            cardCvv = "442",
            gradient = CardGradient.MIDNIGHT_BLUE,
            cardId = 2,
        )
    )
}