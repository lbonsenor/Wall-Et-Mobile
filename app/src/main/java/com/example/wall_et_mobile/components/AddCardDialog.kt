package com.example.wall_et_mobile.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
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
import com.example.wall_et_mobile.model.CardDetails
import com.example.wall_et_mobile.model.CardType

@Composable
fun AddCardDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (CardDetails) -> Unit
) {
    var number by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    val isEnabled = number.length == 19 && fullName.isNotEmpty() && expirationDate.length == 4 && cvv.length == 3

    Log.d("AddCardDialog", """
    Button enabled: $isEnabled
    Number (${number.length}): $number
    Name: $fullName
    Expiration (${expirationDate.length}): $expirationDate
    CVV (${cvv.length}): $cvv
""".trimIndent())


    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false, usePlatformDefaultWidth = false),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Add New Card",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = number,
                        onValueChange = {if (it.length <= 5) number = it},
//                        visualTransformation = CardNumberTransformation(),
                        label = { Text("Card Number") },
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = { painterResource(R.drawable.credit_card) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            focusedLeadingIconColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Card Holder") },
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = expirationDate,
                            onValueChange = { if (it.length <= 5) expirationDate = it },
                            label = { Text("MM/YY") },
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            visualTransformation = ExpirationDateTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            )
                        )
                        OutlinedTextField(
                            value = cvv,
                            onValueChange = { if (it.length <= 3) cvv = it },
                            label = { Text("CVV") },
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                cursorColor = MaterialTheme.colorScheme.onBackground,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            )
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton (
                            onClick = onDismiss,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                val cardDetails = CardDetails(
                                    cardNumber = number,
                                    cardType = CardType.CREDIT_CARD,
                                    cardHolder = fullName,
                                    cardExpiration = expirationDate,
                                    cardCvv = cvv
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
                            Text("Add Card")
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
        if (text.text.length >= 2) {
            out = text.text.substring(0, 2) + "/" + text.text.substring(2)
        }
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

//class CardNumberTransformation : VisualTransformation {
//
//        return TransformedText(
//
//        )
//    }
//}