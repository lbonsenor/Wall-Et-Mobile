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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

data class Card(
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val cardExpiration: String,
    val cardCvv: String,
    val cardBrand: CardBrand,
    val backgroundColor: List<Long> = listOf(0xFF8B62E9, 0xFF3C0F8A),
) {
    companion object {
        val sampleCards = listOf(
            Card(
                cardNumber = "1234 5678 9012 3456",
                cardType = CardType.CREDIT_CARD,
                cardHolder = "John Doe",
                cardExpiration = "01/25",
                cardCvv = "123",
                cardBrand = CardBrand.MASTERCARD,
                backgroundColor = listOf(0xFF8B62E9, 0xFF3C0F8A)
            ),
            Card(
                cardNumber = "9876 5432 1098 7654",
                cardType = CardType.DEBIT_CARD,
                cardHolder = "Jane Smith",
                cardExpiration = "03/29",
                cardCvv = "456",
                cardBrand = CardBrand.AMEX,
                backgroundColor = listOf(0xFF4CAF50, 0xFF2E7D32)
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
                brush = Brush.verticalGradient(
                    colors = card.backgroundColor.map { Color(it) }
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
