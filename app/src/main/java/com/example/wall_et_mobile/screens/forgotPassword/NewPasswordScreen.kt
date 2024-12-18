package com.example.wall_et_mobile.screens.forgotPassword

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.components.PasswordField

@Composable
fun NewPasswordScreen(
    onNavigateToSuccess: () -> Unit,
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun validatePassword(password: String): Boolean {
        val passwordPattern = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    val isFormValid = remember(password, confirmPassword) {
        validatePassword(password) && password == confirmPassword
    }

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(end = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.set_new_password),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.create_new_password_string),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .weight(0.6f, fill = false)
                    .widthIn(max = 450.dp)
                    .fillMaxHeight(0.7f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PasswordField(
                            password = password,
                            onPasswordChange = { password = it },
                            modifier = Modifier.weight(1f),
                            label = R.string.new_password,
                            errorMessage = stringResource(R.string.password_format),
                            validate = { newPassword -> validatePassword(newPassword) }
                        )

                        PasswordField(
                            password = confirmPassword,
                            onPasswordChange = { confirmPassword = it },
                            modifier = Modifier.weight(1f),
                            isRepeatPassword = true,
                            originalPassword = password,
                            label = R.string.repeat_password,
                            errorMessage = stringResource(R.string.passwords_dont_match)
                        )
                    }

                    EndFormButton(
                        textResourceId = R.string.confirm,
                        onClick = onNavigateToSuccess,
                        enabled = isFormValid,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.set_new_password),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.create_new_password_string),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PasswordField(
                        password = password,
                        onPasswordChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = R.string.new_password,
                        errorMessage = stringResource(R.string.password_format),
                        validate = { newPassword -> validatePassword(newPassword) }
                    )

                    PasswordField(
                        password = confirmPassword,
                        onPasswordChange = { confirmPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        isRepeatPassword = true,
                        originalPassword = password,
                        label = R.string.repeat_password,
                        errorMessage = stringResource(R.string.passwords_dont_match)
                    )

                    EndFormButton(
                        textResourceId = R.string.confirm,
                        onClick = onNavigateToSuccess,
                        enabled = isFormValid
                    )
                }
            }
        }
    }
}