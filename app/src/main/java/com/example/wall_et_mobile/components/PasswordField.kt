package com.example.wall_et_mobile.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.wall_et_mobile.R

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit, modifier: Modifier = Modifier, isRepeatPassword: Boolean = false, originalPassword: String = "", label: Int = R.string.password) {
    var passwordVisible by remember { mutableStateOf(false) }
    val isError = isRepeatPassword && password.isNotEmpty() && password != originalPassword

    CustomTextField(
        value = password,
        onValueChange = onPasswordChange,
        labelResourceId = label,
        modifier = modifier,
        isError = isError,
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) R.drawable.visibility
                        else R.drawable.visibility_off,
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                )
            }
        }
    )
}