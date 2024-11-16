package com.example.wall_et_mobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wall_et_mobile.components.Card
import com.example.wall_et_mobile.components.CardList

@Composable
fun CardsScreen(innerPadding: PaddingValues) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CardList(Card.sampleCards)
    }
}