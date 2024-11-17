package com.example.wall_et_mobile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.mock.MockCards
import com.example.wall_et_mobile.model.CardDetails
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.example.wall_et_mobile.ui.theme.White



@Composable
fun CardItem(card: CardDetails) {
    var isNumberVisible by remember { mutableStateOf(false) }

    val maskedNumber = if (isNumberVisible) {
        card.cardNumber
    } else {
        card.cardNumber.replace(Regex(".{14}(.{4})"), "**** **** **** $1")
    }

    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
//            .height(200.dp)
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
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(card.getCardBrand().iconInt),
                    contentDescription = "Card Brand",
                    tint = Color.Unspecified
                )

                IconButton(
                    onClick = { isNumberVisible = !isNumberVisible }
                ) {
                    Icon(
                        painter = painterResource(
                            if (isNumberVisible) R.drawable.visibility
                            else R.drawable.visibility_off
                        ),
                        contentDescription = if (isNumberVisible) "Hide card number" else "Show card number",
                    )
                }
            }

            Text(
                text = maskedNumber,
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

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
@Composable
fun CardItemPreview() {
    WallEtTheme {
        Column {
            CardItem(card = MockCards.sampleCards[0])
            CardItem(card = MockCards.sampleCards[1])
        }
    }
}