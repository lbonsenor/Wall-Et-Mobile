package com.example.wall_et_mobile.screens.transfer


import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.TransferCardSlider
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.mock.MockCards
import com.example.wall_et_mobile.data.mock.MockContacts
import com.example.wall_et_mobile.model.User
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SelectAmountScreen(
    innerPadding : PaddingValues,
    id: Int,
    onNavigateToSelectPayment : (Int, String) -> Unit
)
{
    val user : User = MockContacts.sampleContacts[id]
    var amount by remember { mutableStateOf("") }
    var cents by remember { mutableStateOf("00") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TransferProgress(1)
        ContactCard(user,
            Modifier
                .fillMaxWidth()
                .padding(25.dp), onClick = {})

        AmountTextField(amount, cents, { amount = it }, { cents = it })

        Box(
            contentAlignment = Alignment.Center,
        ) {
            TransferCardSlider(MockCards.sampleCards)
        }

        Button(
            onClick = {
                onNavigateToSelectPayment(user.id, amount)
            },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(stringResource(R.string.continue_button))
        }
    }

}

class AmountTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = text.text
        out = formatAmount(text.text)
        return TransformedText(
            AnnotatedString(out),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return when (offset) {
                        0,1,2,3 -> offset
                        4,5,6 -> offset + 1
                        else -> offset + 2
                    }
                }
                override fun transformedToOriginal(offset: Int): Int {
                    if (text.text.isEmpty()) return offset
                    return when (offset) {
                        0,1,2,3 -> offset
                        4,5,6 -> offset - 1
                        else -> offset - 2
                    }
                }
            }
        )
    }
}

@Composable
fun ContactCard(user: User, modifier : Modifier, onClick : () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.person),
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                    .padding(10.dp)
            )
            Text(
                text = "${user.name} ${user.lastName}",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        FilledTonalButton (
            onClick = {  },
            shape = CircleShape,
//            colors = ButtonDefaults.outlinedButtonColors(
//                contentColor = MaterialTheme.colorScheme.onSecondary,
//                containerColor = MaterialTheme.colorScheme.secondary,
//            ),
            //border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = stringResource(R.string.change))
        }
    }
}



@Composable
fun AmountTextField(whole: String, cents: String, setWhole: (String) -> Unit, setCents: (String) -> Unit) {
    Row (verticalAlignment = Alignment.Bottom){
        TextField(
            supportingText = {Text("${stringResource(R.string.max_amount)} $6,000,000.00")},
            value = whole,
            onValueChange = {
                if (it.isEmpty()) setWhole(it)
                else if (it.isDigitsOnly()) {
                    if (it.length > 1 && it[0] == '0') setWhole(it.trimStart('0').ifEmpty { "0" })
                    else if (it.toDouble() <= 6000000) setWhole(it)
                    else if (it.toDouble() >= 6000000) setCents("00")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = DarkerGrotesque,
                fontWeight = FontWeight.SemiBold,
                fontSize = 45.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,  // bottom line color when focused
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), // bottom line color when not focused
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.Transparent,  // background color
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            prefix = {
                Text(
                    text = "$ ",
                    style = TextStyle(
                        fontFamily = DarkerGrotesque,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 45.sp))
            },
            singleLine = true,
            modifier = Modifier.padding(bottom = 8.dp).weight(0.75f),
            visualTransformation = AmountTransformation(),
        )
        TextField(
            supportingText = {Text(" ")},
            value = cents,
            onValueChange = {
                if (it.isDigitsOnly() && it.length <= 2) setCents(it)
                if (whole == "6000000") setCents("00")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = DarkerGrotesque,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,  // bottom line color when focused
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), // bottom line color when not focused
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.Transparent,  // background color
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            prefix = {
                Text(
                    text = ". ",
                    style = TextStyle(
                        fontFamily = DarkerGrotesque,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp))
            },
            singleLine = true,
            modifier = Modifier.padding(bottom = 8.dp).weight(0.25f),
        )
    }

}

fun formatAmount(amount: String): String {
    if (amount.isEmpty()) return ""
    return try {
        val number = amount.toDoubleOrNull() ?: return ""
        if (number > 6000000) return "6,000,000"
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        formatter.minimumFractionDigits = 0
        formatter.maximumFractionDigits = 0
        formatter.format(number)
    } catch (e: Exception) {
        Log.e("Error on formatting string", e.toString())
        ""
    }

}