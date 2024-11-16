package com.example.wall_et_mobile.components

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque

@Composable
fun Balance(balance: CurrencyAmount = CurrencyAmount(123000.0, Currency.getInstance("ARS"))){
    val whole = NumberFormat.getCurrencyInstance().format(balance.number).split(".")[0]
    val decimal = NumberFormat.getCurrencyInstance().format(balance.number).split(".")[1]

    val (isShown, setShown) = remember { mutableStateOf(false) }



        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(end = 30.dp),
        ){
//            Text(
//                text = stringResource(R.string.balance),
//                color = MaterialTheme.colorScheme.onPrimary,
//                style = MaterialTheme.typography.labelMedium
//            )
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (verticalAlignment = Alignment.Top) {
                    if (isShown) {
                        Text(
                            text = whole,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.displayMedium,
                            fontFamily = DarkerGrotesque,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = decimal,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = DarkerGrotesque,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(3.dp, 5.dp, 0.dp, 0.dp)
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
            IconToggleButton(
                checked = isShown,
                onCheckedChange = { setShown(!isShown) }
            ) {
                Icon(
                    painter = if (isShown) painterResource(R.drawable.visibility) else painterResource(R.drawable.visibility_off),
                    contentDescription = "Show Balance",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

