package com.example.wall_et_mobile.components

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.Wallet
import com.example.wall_et_mobile.screens.ErrorScreen
import com.example.wall_et_mobile.screens.LoadingScreen
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import java.text.NumberFormat

@Composable
fun Balance(
    wallet: Wallet?,
    isFetching: Boolean = false,
    error: Error? = null
    //balance: CurrencyAmount = CurrencyAmount(123000.0, Currency.getInstance("ARS"))
){
    if (isFetching) {
        LoadingScreen()
        return
    }

    error?.let {
        ErrorScreen()
        return
    }

    wallet?.let { currentWallet ->
        val balance = CurrencyAmount(currentWallet.balance, Currency.getInstance("ARS"))
        val whole = NumberFormat.getCurrencyInstance().format(balance.number).split(".")[0]
        val decimal = NumberFormat.getCurrencyInstance().format(balance.number).split(".")[1]

        val (isShown, setShown) = remember { mutableStateOf(false) }

        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ){
            Spacer(modifier = Modifier.weight(1/6f))
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.balance),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelMedium
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (isShown) {
                        Text(
                            text = whole,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.displayMedium,
                            fontFamily = DarkerGrotesque,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = ".$decimal",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = DarkerGrotesque,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    } else {
                        Text(
                            text = "$******",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.displayMedium,
                            fontFamily = DarkerGrotesque,
                            fontWeight = FontWeight.Black,
                        )
                    }

                }
            }
            FilledTonalIconToggleButton(
                checked = isShown,
                onCheckedChange = { setShown(!isShown) },
                colors = IconToggleButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary,

                    checkedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    checkedContentColor = MaterialTheme.colorScheme.onPrimary,

                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(
                    painter = if (isShown) painterResource(R.drawable.visibility) else painterResource(R.drawable.visibility_off),
                    contentDescription = "Show Balance",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}

