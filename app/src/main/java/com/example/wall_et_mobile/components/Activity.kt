package com.example.wall_et_mobile.components

import android.content.res.Configuration
import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import java.sql.Timestamp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.ui.theme.Green
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

enum class TransactionType (val stringInt: Int, val iconInt: Int){
    ONLINE_PAYMENT(R.string.online_payment, R.drawable.qrcode_scan),
    TRANSFER_RECEIVED(R.string.transfer_received , R.drawable.payments),
    TRANSFER_SENT(R.string.transfer_sent, R.drawable.payments),
    LOCAL_STORE(R.string.offline_payment, R.drawable.local_mall),
}

enum class PaymentType (val stringInt: Int){
    CREDIT_CARD(R.string.credit),
    DEBIT_CARD(R.string.debit),
    AVAILABLE(R.string.available_balance)
}

sealed class Activity (
    val amount: CurrencyAmount,
    val transactionTime: Timestamp,
    val name: String,
    val transactionType: TransactionType,
    val paymentType: PaymentType = PaymentType.AVAILABLE,
) {
    object Test : Activity(
        name = "Farmacity",
        transactionType = TransactionType.ONLINE_PAYMENT,
        amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
        transactionTime = Timestamp(System.currentTimeMillis())
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Preview(name = "LightMode")
@Composable
fun Preview(){
    WallEtTheme {
        ActivityItem()
    }
}

@Composable
fun ActivityItem(activity: Activity = Activity.Test){
    val timeFormatter = SimpleDateFormat("HH:MM", Locale.getDefault())
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    val formattedTime = timeFormatter.format(activity.transactionTime)
    val formattedCurrency = currencyFormatter.format(activity.amount.number)

    val titleColor = MaterialTheme.colorScheme.onSurface
    val subtitleColor = MaterialTheme.colorScheme.onTertiary

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)


    ){
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(activity.transactionType.iconInt),
                    contentDescription = "ActivityImage",
                    tint = MaterialTheme.colorScheme.secondary,
                )
                Column (modifier = Modifier.padding(start = 10.dp)) {
                    Text(text = activity.name,
                        style = MaterialTheme.typography.titleMedium,
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
            Column (horizontalAlignment = Alignment.End) {
                if (activity.transactionType == TransactionType.TRANSFER_RECEIVED){
                    Text(text = "+$formattedCurrency", color = Green, style = MaterialTheme.typography.labelLarge)
                } else
                    Text(text = "-$formattedCurrency", style = MaterialTheme.typography.labelLarge, color = titleColor)

                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = subtitleColor
                )
            }

        }
    }
}