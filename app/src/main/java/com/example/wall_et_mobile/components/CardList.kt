package com.example.wall_et_mobile.components

import androidx.compose.runtime.Composable

@Composable
fun CardList(cards: List<Card>) {
    cards.forEach { card ->
        CardItem(card = card)
    }
}