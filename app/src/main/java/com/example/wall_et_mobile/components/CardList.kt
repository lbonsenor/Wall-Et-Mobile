package com.example.wall_et_mobile.components

import androidx.compose.runtime.Composable
import com.example.wall_et_mobile.data.model.Card

@Composable
fun CardList(cards: List<Card>) {
    cards.forEach { card ->
        CardItem(card = card)
    }
}