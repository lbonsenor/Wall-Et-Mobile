package com.example.wall_et_mobile.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelResourceId: Int,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    validate: ((String) -> Boolean)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true
) {
    val isError = validate?.invoke(value) == false && value.isNotEmpty()
    
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                stringResource(labelResourceId),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
        ),
        trailingIcon = trailingIcon,
        modifier = modifier,
        isError = isError,
        enabled = enabled,
        supportingText = if (isError && errorMessage != null) {
            { Text(errorMessage) }
        } else null,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(15.dp)
    )
}