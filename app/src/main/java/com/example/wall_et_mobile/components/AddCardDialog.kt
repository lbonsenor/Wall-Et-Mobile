package com.example.wall_et_mobile.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.CardType

@Composable
fun AddCardDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (Card) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var number by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var isCredit by remember { mutableStateOf(false) }

    fun validateCardNumber(number: String): Boolean {
        return number.length == 16 && number.all { it.isDigit() }
    }

    fun validateCardHolder(name: String): Boolean {
        return name.isNotEmpty() && name.trim().split(" ").size >= 2
    }

    fun validateCvv(cvv: String): Boolean {
        return cvv.length == 3 && cvv.all { it.isDigit() }
    }

    fun validateExpirationDate(date: String): Boolean {
        if (date.length != 4) return false
        if (!date.all { it.isDigit() }) return false

        val month = date.substring(0, 2).toInt()
        val year = date.substring(2, 4).toInt()

        if (month < 1 || month > 12) return false

        val currentYear = java.time.LocalDate.now().year % 100
        val currentMonth = java.time.LocalDate.now().monthValue

        return when {
            year < currentYear -> false
            year == currentYear -> month >= currentMonth
            else -> true
        }
    }

    val isEnabled = remember(number, fullName, cvv, expirationDate) {
        validateCardNumber(number) &&
        validateCardHolder(fullName) &&
        validateExpirationDate(expirationDate) &&
        validateCvv(cvv) && fullName.isNotBlank()
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false, usePlatformDefaultWidth = false),

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(if (isLandscape) 0.5f else 0.9f)
                    .padding(vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_new_card),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SelectableCardType(
                                isCredit = isCredit,
                                onSelectionChange = { isCredit = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = if (isCredit) stringResource(R.string.credit_card) else stringResource(R.string.debit_card),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.credit_card),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }

                                Text(
                                    text = number.ifEmpty { "****************" },
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            text = stringResource(R.string.card_holder),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                                        )
                                        Text(
                                            text = fullName.ifEmpty { stringResource(R.string.your_name) },
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = stringResource(R.string.expires),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                                        )
                                        Text(
                                            text = if (expirationDate.length == 4)
                                                "${expirationDate.substring(0,2)}/${expirationDate.substring(2,4)}"
                                            else "MM/YY",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = number,
                            onValueChange = { if (it.length <= 16) number = it },
                            label = { Text(stringResource(R.string.card_number)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            visualTransformation = CardNumberTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )

                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text(stringResource(R.string.card_holder)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = expirationDate,
                                onValueChange = { if (it.length <= 4) expirationDate = it },
                                label = { Text("MM/YY") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                visualTransformation = ExpirationDateTransformation(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                )
                            )

                            OutlinedTextField(
                                value = cvv,
                                onValueChange = { if (it.length <= 3) cvv = it },
                                label = { Text("CVV") },
                                modifier = Modifier.width(120.dp),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(stringResource(R.string.cancel))
                        }

                        Button(
                            onClick = {
                                val card = Card(
                                    cardId = null,
                                    cardType = if (isCredit) CardType.CREDIT_CARD else CardType.DEBIT_CARD,
                                    cardCvv = cvv,
                                    cardExpiration = "${expirationDate.substring(0,2)}/${expirationDate.substring(2,4)}",
                                    cardHolder = fullName,
                                    cardNumber = number
                                )
                                onSubmit(card)
                                onDismiss()
                            },
                            enabled = isEnabled,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(stringResource(R.string.accept))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectableCardType(
    isCredit: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.1f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(false to stringResource(R.string.debit), true to stringResource(R.string.credit)).forEach { (isCardCredit, text) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isCredit == isCardCredit)
                            MaterialTheme.colorScheme.secondary
                        else
                            Color.Transparent
                    )
                    .clickable { onSelectionChange(isCardCredit) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = if (isCredit == isCardCredit)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

class ExpirationDateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = text.text
        out = formatDate(text.text)
        return TransformedText(
            AnnotatedString(out),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int =
                    if (offset <= 2) offset else offset + 1
                override fun transformedToOriginal(offset: Int): Int =
                    if (offset <= 2) offset else offset - 1
            }
        )
    }
}

class CardNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = text.text
        out = formatCardNumber(text.text)

        return TransformedText(
            AnnotatedString(out),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int =
                    when (offset) {
                        0,1,2,3,4 -> offset
                        5,6,7,8 -> offset + 1
                        9,10,11,12 -> offset + 2
                        else -> offset + 3
                    }
                override fun transformedToOriginal(offset: Int): Int =
                    when (offset) {
                        0,1,2,3,4 -> offset
                        5,6,7,8 -> offset - 1
                        9,10,11,12 -> offset - 2
                        else -> offset - 3
                    }
            }
        )
    }
}

fun formatDate(str: String): String{
    return when (str.length) {
        0, 1, 2 -> str
        3 -> "${str[0]}${str[1]}/${str[2]}"
        else -> "${str[0]}${str[1]}/${str[2]}${str[3]}"
    }
}

fun formatCardNumber(str : String): String{
    return str.chunked(4).joinToString(" ")
}
