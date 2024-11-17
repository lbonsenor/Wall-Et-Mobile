package com.example.wall_et_mobile.components

import androidx.compose.runtime.Composable
import com.example.wall_et_mobile.model.CardDetails

@Composable
fun CardList(cards: List<CardDetails>) {
    cards.forEach { card ->
        CardItem(card = card)
    }
}