package com.example.wall_et_mobile.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.CustomTextField
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.components.PasswordField
import com.example.wall_et_mobile.model.Screen

@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        val passwordPattern = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    val isFormValid = remember(name, email, password, confirmPassword) {
        name.isNotEmpty() && validateEmail(email) && validatePassword(password) && password == confirmPassword
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = stringResource(R.string.sign_up),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomTextField(
                    value = name,
                    onValueChange = { name = it },
                    labelResourceId = R.string.name,
                    modifier = Modifier.fillMaxWidth()
                )

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    labelResourceId = R.string.email,
                    modifier = Modifier.fillMaxWidth(),
                    errorMessage = stringResource(R.string.invalid_email),
                    validate = { newEmail -> validateEmail(newEmail) }
                )

                PasswordField(
                    password = password,
                    onPasswordChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.password,
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
                    textResourceId = R.string.sign_up,
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    enabled = isFormValid
                )

                TextButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Text(
                        stringResource(R.string.already_have_account),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(name = "LightMode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Composable
fun SignupScreenPreview() {
    WallEtTheme {
        SignupScreen(navController = NavController(LocalContext.current))
    }
}