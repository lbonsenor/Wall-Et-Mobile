package com.example.wall_et_mobile.screens.cards

import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.Wallet

data class CardsUiState(
    val cards: List<Card> = emptyList(),
    val card: Card? = null,
    val isFetching: Boolean = true,
    val error: Error? = null,
)


