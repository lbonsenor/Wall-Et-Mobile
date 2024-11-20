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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.data.mock.MockCards
import com.example.wall_et_mobile.model.CardBrand
import com.example.wall_et_mobile.model.CardDetails
import com.example.wall_et_mobile.ui.theme.WallEtTheme


@Composable
fun TransferCardItem(card: CardDetails) {
    var isClicked by remember { mutableStateOf(false) }
    val maskedNumber = card.cardNumber.replace(Regex(".{14}(.{4})"), "Termina en$1")
    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,

        ),
        border = BorderStroke(1.dp, if (isClicked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .aspectRatio(8 / 3f)
            .height(100.dp)
            .padding(8.dp),
        onClick = {
            isClicked = !isClicked
        }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(card.cardBrand.iconInt),
                contentDescription = "Card Brand",
                tint = if (card.cardBrand == CardBrand.VISA) MaterialTheme.colorScheme.secondary else Color.Unspecified,
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
            TransferCardItem(card = MockCards.sampleCards[0])
            TransferCardItem(card = MockCards.sampleCards[1])
        }
    }
}