package com.example.wall_et_mobile.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
    onSubmit: (Card) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var number by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

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
        validateCvv(cvv)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false, usePlatformDefaultWidth = false),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(if (isLandscape) 0.6f else 0.95f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_new_card),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    OutlinedTextField(
                        value = number,
                        onValueChange = { if (it.length <= 16) number = it },
                        label = { Text(stringResource(R.string.card_number)) },
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = { painterResource(R.drawable.credit_card) },
                        isError = number.isNotEmpty() && !validateCardNumber(number),
                        supportingText = {
                            if (number.isNotEmpty() && !validateCardNumber(number)) {
                                Text(
                                    text = stringResource(R.string.card_number_error),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            errorBorderColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error
                        ),
                        visualTransformation = CardNumberTransformation()
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(stringResource(R.string.card_holder)) },
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = fullName.isNotEmpty() && !validateCardHolder(fullName),
                        supportingText = {
                            if (fullName.isNotEmpty() && !validateCardHolder(fullName)) {
                                Text(
                                    text = stringResource(R.string.card_holder_error),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            errorBorderColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = expirationDate,
                            onValueChange = { if (it.length <= 4) expirationDate = it },
                            label = { Text(stringResource(R.string.card_date)) },
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = expirationDate.isNotEmpty() && !validateExpirationDate(expirationDate),
                            supportingText = {
                                if (expirationDate.isNotEmpty() && !validateExpirationDate(expirationDate)) {
                                    Text(
                                        text = stringResource(R.string.expiration_date_error),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                errorBorderColor = MaterialTheme.colorScheme.error,
                                errorLabelColor = MaterialTheme.colorScheme.error
                            ),
                            visualTransformation = ExpirationDateTransformation()
                        )

                        OutlinedTextField(
                            value = cvv,
                            onValueChange = { if (it.length <= 3) cvv = it },
                            label = { Text("CVV") },
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = cvv.isNotEmpty() && !validateCvv(cvv),
                            supportingText = {
                                if (cvv.isNotEmpty() && !validateCvv(cvv)) {
                                    Text(
                                        text = stringResource(R.string.cvv_error),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                errorBorderColor = MaterialTheme.colorScheme.error,
                                errorLabelColor = MaterialTheme.colorScheme.error
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                        Button(
                            onClick = {
                                val cardDetails = Card(
                                    cardNumber = number,
                                    cardType = CardType.CREDIT_CARD,
                                    cardHolder = fullName,
                                    cardExpiration = expirationDate,
                                    cardCvv = cvv,
                                    cardId = 1
                                )
                                onSubmit(cardDetails)
                                number = ""
                                fullName = ""
                                expirationDate = ""
                                cvv = ""
                                type = ""
                                onDismiss()
                            },
                            enabled = isEnabled
                        ) {
                            Text(stringResource(R.string.accept))
                        }
                    }
                }
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
