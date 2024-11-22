package com.example.wall_et_mobile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.TransactionType
import com.example.wall_et_mobile.ui.theme.Green
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Preview(name = "LightMode")
@Composable
fun Preview() {
    WallEtTheme {
        ActivityItem()
    }
}

@Composable
fun ActivityItem(transaction: Transaction = Transaction.Test) {
    val timeFormatter = SimpleDateFormat("HH:MM", Locale.getDefault())
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    val formattedTime = timeFormatter.format(transaction.createdAt!!) //Obs ! This needs a change to date or smth
    val formattedCurrency = currencyFormatter.format(transaction.amount.number)

    val titleColor = MaterialTheme.colorScheme.onSurface
    val subtitleColor = MaterialTheme.colorScheme.onTertiary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RectangleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(transaction.transactionType.iconInt),
                        contentDescription = "ActivityImage",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = transaction.receiver?.name ?: "Couldn't find",
                        style = MaterialTheme.typography.titleSmall,
                        color = titleColor
                    )
                    Text(
                        text = stringResource(transaction.transactionType.stringInt),
                        style = MaterialTheme.typography.labelSmall,
                        color = subtitleColor
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                if (transaction.transactionType == TransactionType.TRANSFER_RECEIVED) {
                    Text(
                        text = "+$formattedCurrency",
                        color = Green,
                        style = MaterialTheme.typography.labelLarge
                    )
                } else {
                    Text(
                        text = "-$formattedCurrency",
                        style = MaterialTheme.typography.labelLarge,
                        color = titleColor
                    )
                }
                Text(
                    text = "$formattedTime",
                    style = MaterialTheme.typography.labelSmall,
                    color = subtitleColor
                )
            }
        }
    }
}