package com.example.wall_et_mobile.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.Card

sealed class SelectedOption {
    data class CardOption(val card: Card) : SelectedOption()
    data class WalletOption(val balance: Double) : SelectedOption()

    override fun toString() : String {
        return when (this) {
            is SelectedOption.CardOption -> "CARD"
            is SelectedOption.WalletOption -> "BALANCE"
        }
    }
}
@Composable
fun TransferCardSlider(
    cards: List<Card>,
    balance: Double,
    onSelectionChange: (SelectedOption) -> Unit,
    selectedOption: SelectedOption? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .horizontalScroll(rememberScrollState())
        ,
    ) {
            WalletCard(
                isSelected = selectedOption is SelectedOption.WalletOption,
                onSelect = {
                    onSelectionChange(SelectedOption.WalletOption(balance))
                },
                balance = balance
            )

        cards.forEach {card ->
            TransferCardItem(
                card = card,
                isSelected = selectedOption is SelectedOption.CardOption &&
                        (selectedOption as SelectedOption.CardOption).card.cardId == card.cardId,
                onSelect = { onSelectionChange(SelectedOption.CardOption(card)) },
                enabled = true
            )
        }
    }
}

@Composable
fun WalletCard(
    isSelected: Boolean,
    onSelect: () -> Unit,
    balance: Double = 0.0
) {
    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Gray,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .aspectRatio(8 / 3f)
            .height(100.dp)
            .padding(8.dp)
        ,
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
                imageVector = Icons.Filled.Lock,
                contentDescription = "Wall-Et Card",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(40.dp).padding(end = 8.dp)
            )
            Column() {
            Text(
                text = stringResource(R.string.wallet_balance),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
//                fontSize = 15.sp,
//                fontFamily = DarkerGrotesque,
//                letterSpacing = 2.sp
            )
            if (isSelected) {
                Text(
                    text = "$${balance}",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            }
        }
    }
}