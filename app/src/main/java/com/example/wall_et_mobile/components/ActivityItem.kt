package com.example.wall_et_mobile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.model.Activity
import com.example.wall_et_mobile.model.TransactionType
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
fun ActivityItem(activity: Activity = Activity.Test) {
    val timeFormatter = SimpleDateFormat("HH:MM", Locale.getDefault())
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    val formattedTime = timeFormatter.format(activity.transactionTime)
    val formattedCurrency = currencyFormatter.format(activity.amount.number)

    val titleColor = MaterialTheme.colorScheme.onSurface
    val subtitleColor = MaterialTheme.colorScheme.onTertiary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(activity.transactionType.iconInt),
                    contentDescription = "ActivityImage",
                    tint = MaterialTheme.colorScheme.secondary,
                )
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = activity.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = titleColor
                    )
                    Text(
                        text = stringResource(activity.transactionType.stringInt),
                        style = MaterialTheme.typography.labelSmall,
                        color = subtitleColor
                    )
                    Text(
                        text = stringResource(activity.paymentType.stringInt),
                        style = MaterialTheme.typography.labelSmall,
                        color = subtitleColor
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                if (activity.transactionType == TransactionType.TRANSFER_RECEIVED) {
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
                    text = formattedTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = subtitleColor
                )
            }
        }
    }
}