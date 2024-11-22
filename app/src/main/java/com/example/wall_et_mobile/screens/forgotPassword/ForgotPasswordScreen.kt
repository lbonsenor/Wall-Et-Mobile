package com.example.wall_et_mobile.screens.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.CustomTextField
import com.example.wall_et_mobile.components.EndFormButton

@Composable
fun ForgotPasswordScreen(
    onNavigateToLogin : () -> Unit
) {
    var email by remember { mutableStateOf("") }

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val isFormValid = remember(email) {
        validateEmail(email)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.forgot_password),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.no_worries),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Left
        )

//        Text(
//            text = stringResource(R.string.recover_password),
//            color = MaterialTheme.colorScheme.onBackground,
//            style = MaterialTheme.typography.headlineMedium
//        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    labelResourceId = R.string.email,
                    modifier = Modifier.fillMaxWidth(),
                    errorMessage = stringResource(R.string.invalid_email),
                    validate = { newEmail -> validateEmail(newEmail) }
                )

                EndFormButton(
                    textResourceId = R.string.send,
                    onClick = {  },
                    enabled = isFormValid
                )

                TextButton(
                    onClick = onNavigateToLogin,
                    ) {
                    Icons.Default.ArrowBack
                    Text(
                        stringResource(R.string.back_to_login),
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }
            }
        }
    }
}
