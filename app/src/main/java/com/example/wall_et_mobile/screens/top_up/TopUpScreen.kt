package com.example.wall_et_mobile.screens.top_up


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.SuccessDialog
import com.example.wall_et_mobile.components.TransferCardSlider
import com.example.wall_et_mobile.data.mock.MockCards
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TopUpScreen(
    innerPadding : PaddingValues
)
{
    var amount by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SuccessDialog(
            visible = showSuccess,
            message = stringResource(R.string.add_funds_success),
            onDismiss = { showSuccess = false }
        )

        Text(text = stringResource(R.string.add_funds), style = MaterialTheme.typography.titleLarge)

        TextField(
            supportingText = {Text("${stringResource(R.string.max_amount)} $6,000,000.00")},
            value = amount,
            onValueChange = {
                if (it.length <= 12 ) amount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = DarkerGrotesque,
                fontWeight = FontWeight.SemiBold,
                fontSize = 50.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,  // bottom line color when focused
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), // bottom line color when not focused
                cursorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.Transparent,  // background color
                unfocusedContainerColor = Color.Transparent,
            ),
            prefix = {
                Text(
                    text = "$ ",
                    style = TextStyle(
                        fontFamily = DarkerGrotesque,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 50.sp))
            },
            singleLine = true,
            modifier = Modifier.padding(bottom = 8.dp),
            visualTransformation = VisualTransformation { it ->
                val formattedAmount = formatAmount(it.text)

                val originalToTransformed = object : OffsetMapping{
                    override fun originalToTransformed(offset: Int): Int {
                        if (it.text.isEmpty()) return 0
                        return formattedAmount.length
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        if (formattedAmount.isEmpty()) return 0
                        return it.text.length
                    }
                }
                TransformedText(AnnotatedString(formattedAmount), originalToTransformed)
            },
        )

        Button(
            onClick = {
                 showSuccess = true
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

fun formatAmount(amount: String): String {
    if (amount.isEmpty()) return "0.00"
    return try {
        val number = amount.toDoubleOrNull() ?: return ""
        if (number >= 6000000) return "6,000,000.00"
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        formatter.format(number)
    } catch (e: Exception) {
        ""
    }

}
