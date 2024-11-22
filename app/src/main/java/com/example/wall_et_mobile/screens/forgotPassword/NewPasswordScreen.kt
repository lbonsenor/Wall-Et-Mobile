package com.example.wall_et_mobile.screens.forgotPassword

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.components.PasswordField
import com.example.wall_et_mobile.data.model.Screen
import com.example.wall_et_mobile.ui.theme.WallEtTheme

@Composable
fun NewPasswordScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    fun validatePassword(password: String): Boolean {
        val passwordPattern = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    val isFormValid = remember(password, confirmPassword) {
        validatePassword(password) && password == confirmPassword
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
            text = stringResource(R.string.recover_password),
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
                    textResourceId = R.string.confirm,
                    onClick = { navController.navigate(Screen.Login.route) },
                    enabled = isFormValid
                )
            }
        }
    }
}

@Preview(name = "LightMode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Composable
fun NewPasswordScreenPreview() {
    WallEtTheme {
        NewPasswordScreen(navController = NavController(LocalContext.current))
    }
}