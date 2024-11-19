package com.example.wall_et_mobile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.mock.MockCards
import com.example.wall_et_mobile.model.CardDetails
import com.example.wall_et_mobile.ui.theme.WallEtTheme

@Composable
fun TransferCardSlider(cards: List<CardDetails>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .horizontalScroll(rememberScrollState())
        ,
    ) {
        WalletCard()
        cards.forEach { card ->
            TransferCardItem(card = card)
        }
    }
}

@Preview(name = "Light Mode")
@Composable
fun TransferCardSlidePreview() {
    WallEtTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ){
            TransferCardSlider(MockCards.sampleCards)
        }
    }
}

@Composable
fun WalletCard() {
    var isClicked by remember { mutableStateOf(false) }
    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Gray,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(1.dp, if (isClicked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .aspectRatio(8 / 3f)
            .height(100.dp)
            .padding(8.dp)
        ,
        onClick = { isClicked = !isClicked }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Wall-Et Card",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(40.dp).padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.wallet_balance),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
//                fontSize = 15.sp,
//                fontFamily = DarkerGrotesque,
//                letterSpacing = 2.sp
            )
        }
    }
}