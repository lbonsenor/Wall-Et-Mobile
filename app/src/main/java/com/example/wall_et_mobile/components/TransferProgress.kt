package com.example.wall_et_mobile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.ui.theme.Gray
import com.example.wall_et_mobile.ui.theme.TransparentPurple

enum class TransferProgressType(
    val labelInt: Int,
    val iconInt: Int,
){
    DESTINATARY(R.string.destinatary, R.drawable.person),
    AMOUNT(R.string.amount, R.drawable.money),
    PAYMENT(R.string.payment_method, R.drawable.credit_card)
}

@Composable
fun TransferProgress(current: Int){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TransferProgressType.entries.forEachIndexed { index, paymentType ->
            Surface (
                shape = CircleShape,
                color = if (index == current) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary,
                border = BorderStroke(0.4.dp, MaterialTheme.colorScheme.secondary),
                modifier = Modifier.size(if (index == current) 30.dp else 10.dp)
            ){
                Icon(painter = painterResource(paymentType.iconInt),
                    stringResource(paymentType.labelInt),
                    tint = if (index == current) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
                    modifier = Modifier.padding(4.dp)
                )
            }
            if (index != TransferProgressType.entries.size - 1){
                HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.secondary)
            }

        }
    }
}

@Preview
@Composable
fun PreviewTransfer(){
    WallEtTheme {
        Column(
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TransferProgress(0);
            TransferProgress(1);
            TransferProgress(2);

        }
    }
}