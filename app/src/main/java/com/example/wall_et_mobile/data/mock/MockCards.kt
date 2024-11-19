package com.example.wall_et_mobile.data.mock

import com.example.wall_et_mobile.model.CardDetails
import com.example.wall_et_mobile.model.CardGradient
import com.example.wall_et_mobile.model.CardType

object MockCards {
    private val _sampleCards = mutableListOf(
        CardDetails(
            cardNumber = "4234 5678 9012 3456",
            cardType = CardType.CREDIT_CARD,
            cardHolder = "John Doe",
            cardExpiration = "01/25",
            cardCvv = "123",
            gradient = CardGradient.PURPLE_INDIGO
        ),
        CardDetails(
            cardNumber = "5876 5432 1098 7654",
            cardType = CardType.DEBIT_CARD,
            cardHolder = "Jane Smith",
            cardExpiration = "03/29",
            cardCvv = "456",
            gradient = CardGradient.DARK_TEAL
        ),
        CardDetails(
            cardNumber = "4131 5234 6213 1231",
            cardType = CardType.DEBIT_CARD,
            cardHolder = "Juan José",
            cardExpiration = "12/31",
            cardCvv = "442",
            gradient = CardGradient.MIDNIGHT_BLUE
        )
    )
    val sampleCards: List<CardDetails> = _sampleCards

    fun add(card: CardDetails){
        _sampleCards.add(card)
    }

    fun delete(card: CardDetails) {
        _sampleCards.remove(card)

    }
}