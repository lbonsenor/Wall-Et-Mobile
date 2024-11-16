package com.example.wall_et_mobile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.example.wall_et_mobile.ui.theme.White

enum class CardType(val stringInt: Int) {
    CREDIT_CARD(R.string.credit),
    DEBIT_CARD(R.string.debit),
}

enum class CardBrand {
    VISA,
    MASTERCARD,
    AMEX,
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

data class Card(
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String,
    val cardBrand: CardBrand,
    val gradient: CardGradient = CardGradient.random(),
) {
    val backgroundColors: List<Color>
        get() = gradient.colors

    companion object {
        val sampleCards = listOf(
            Card(
                cardNumber = "1234 5678 9012 3456",
                cardType = CardType.CREDIT_CARD,
                cardHolder = "John Doe",
                cardExpiration = "01/25",
                cardCvv = "123",
                cardBrand = CardBrand.MASTERCARD,
                gradient = CardGradient.PURPLE_INDIGO
            ),
            Card(
                cardNumber = "9876 5432 1098 7654",
                cardType = CardType.DEBIT_CARD,
                cardHolder = "Jane Smith",
                cardExpiration = "03/29",
                cardCvv = "456",
                cardBrand = CardBrand.AMEX,
                gradient = CardGradient.DARK_TEAL
            ),
            Card(
                cardNumber = "2131 5234 6213 1231",
                cardType = CardType.DEBIT_CARD,
                cardHolder = "Juan José",
                cardExpiration = "12/31",
                cardCvv = "442",
                cardBrand = CardBrand.VISA,
                gradient = CardGradient.MIDNIGHT_BLUE
            )
        )
    }
}

@Composable
fun CardItem(card: Card) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = card.backgroundColors,
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                    tileMode = TileMode.Clamp
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text(
                text = card.cardBrand.name,
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = card.cardNumber,
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = card.cardHolder,
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = card.cardExpiration,
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CardItemPreview() {
    WallEtTheme {
        Column {
            CardItem(card = Card.sampleCards[0])
            CardItem(card = Card.sampleCards[1])
        }
    }
}