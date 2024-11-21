package com.example.wall_et_mobile.data.model

import androidx.compose.ui.graphics.Color
import com.example.wall_et_mobile.R

enum class CardType(val stringInt: Int) {
    CREDIT_CARD(R.string.credit),
    DEBIT_CARD(R.string.debit),
}

enum class CardBrand (val iconInt: Int){
    VISA(R.drawable.visa),
    MASTERCARD(R.drawable.master_card),
}

enum class CardGradient(val colors: List<Color>) {
    PURPLE_INDIGO(listOf(Color(0xFF4A0E4E), Color(0xFF3F51B5))),
    MIDNIGHT_BLUE(listOf(Color(0xFF1A237E), Color(0xFF0288D1))),
    DARK_TEAL(listOf(Color(0xFF00695C), Color(0xFF00BCD4))),
    DEEP_MAGENTA(listOf(Color(0xFF880E4F), Color(0xFF9C27B0))),
    NAVY_CERULEAN(listOf(Color(0xFF0D47A1), Color(0xFF03A9F4)));

    companion object {
        fun random(): CardGradient = entries.random()
    }
}