package com.example.wall_et_mobile.components

import com.example.wall_et_mobile.R

enum class CardType(val stringInt: Int) {
    CREDIT_CARD(R.string.credit),
    DEBIT_CARD(R.string.debit),
}

data class Card(
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String,
) {
    companion object {
        val sampleCards = listOf(
            Card(
                cardNumber = "1234 5678 9012 3456",
                cardType = CardType.CREDIT_CARD,
                cardHolder = "John Doe",
                cardExpiration = "01/25",
                cardCvv = "123"
            ),
            Card(
                cardNumber = "9876 5432 1098 7654",
                cardType = CardType.DEBIT_CARD,
                cardHolder = "Jane Smith",
                cardExpiration = "03/29",
                cardCvv = "456"
            )
        )
    }


}