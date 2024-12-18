package com.example.wall_et_mobile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.CardBrand
import com.example.wall_et_mobile.data.model.CardType
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import java.time.Instant
import java.util.Date


@Composable
fun TransferCardItem(
    card: Card,
    isSelected: Boolean,
    onSelect: () -> Unit,
    enabled: Boolean = false,
) {
    val maskedNumber = "${stringResource(R.string.card_number_finish)} ${card.cardNumber.takeLast(4)}"
    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
        ),
        enabled = enabled,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) {
                if (isSelected) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)  // Faded border for disabled state
            }
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .aspectRatio(8 / 3f)
            .height(100.dp)
            .padding(8.dp),
        onClick = onSelect
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(card.getCardBrand().iconInt),
                contentDescription = "Card Brand",
                tint = if (card.getCardBrand() == CardBrand.VISA) MaterialTheme.colorScheme.secondary else Color.Unspecified,
                //modifier = Modifier.size(40.dp)
            )
            Text(
                text = maskedNumber,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
//                fontSize = 15.sp,
//                fontFamily = DarkerGrotesque,
//                letterSpacing = 2.sp
            )
        }

    }
}

@Preview(name = "Light Mode")
@Composable
fun TransferCardItemPreview() {
    WallEtTheme {
        Column {
            val card = Card(
                cardId = 1,
                cardNumber = "1234567890123456",
                createdAt = Date.from(Instant.now()),
                cardExpiration = "04/28",
                cardType = CardType.CREDIT_CARD,
                cardCvv = "123",
                cardHolder = "Johnathon Klemp",
                updatedAt = Date.from(Instant.now())
            )
            TransferCardItem(card, isSelected = false, onSelect = {}, enabled = true)
        }
    }
}