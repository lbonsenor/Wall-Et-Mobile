package com.example.wall_et_mobile.screens.login

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.CustomTextField
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.components.PasswordField
import com.example.wall_et_mobile.screens.home.HomeViewModel

@Composable
fun LoginScreen(
    onNavigateToHome : () -> Unit,
    onNavigateToForgotPassword : () -> Unit,
    onNavigateToSignUp : () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))

) {

    val uiState = viewModel.uiState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onNavigateToHome()
        }
    }

    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val isFormValid = remember(email, password) {
        validateEmail(email) && password.isNotEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.log_in),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.welcome_back),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Left
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                if (uiState.isFetching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp)
                    )
                }
                uiState.error?.let { error ->
                    Text(
                        text = "ERROR",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    labelResourceId = R.string.email,
                    modifier = Modifier.fillMaxWidth(),
                    errorMessage = stringResource(R.string.invalid_email),
                    validate = { newEmail -> validateEmail(newEmail) },
                    enabled = !uiState.isFetching
                )

                Column(

                ) {
                    PasswordField(
                        password = password,
                        onPasswordChange = { password = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextButton(onClick = onNavigateToForgotPassword,
                        modifier = Modifier.align(Alignment.End)) {
                        Text(
                            text = stringResource(R.string.forgot_password),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }
                }

                EndFormButton(
                    textResourceId = R.string.log_in,
                    onClick = {
                        viewModel.login(email, password)
                    },
                    enabled = isFormValid && !uiState.isFetching
                )

                EndFormButton(
                    textResourceId = R.string.fast_login,
                    onClick = onNavigateToHome,
                    enabled = !uiState.isFetching
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = 16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.dont_have_account), color = MaterialTheme.colorScheme.onBackground)
                    TextButton(
                        onClick = onNavigateToSignUp
                    ) {
                        Text(
                            stringResource(R.string.sign_up),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToHome = {},
        onNavigateToForgotPassword = {},
        onNavigateToSignUp = {},
    )
}