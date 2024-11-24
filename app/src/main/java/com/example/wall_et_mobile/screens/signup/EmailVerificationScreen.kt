package com.example.wall_et_mobile.screens.signup

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.CustomTextField
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.components.ErrorDialog

@Composable
fun EmailVerificationScreen(
    onNavigateToSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    var code by remember { mutableStateOf("") }
    val uiState = viewModel.uiState
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(uiState.isVerified) {
        if (uiState.isVerified) {
            onNavigateToSuccess()
        }
    }

    if (uiState.error != null) {
        val errorMessage = when (uiState.error.message) {
            "Invalid code" -> stringResource(R.string.invalid_code)
            else -> stringResource(R.string.unexpected_error)
        }
    
        ErrorDialog(
            visible = true,
            message = errorMessage,
            onDismiss = { viewModel.clearError() }
        )
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
                    text = stringResource(R.string.verification),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.enter_code),
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
                    if (uiState.isFetching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    CustomTextField(
                        value = code,
                        onValueChange = { code = it },
                        labelResourceId = R.string.verification_code,
                        modifier = Modifier.fillMaxWidth()
                    )

                    EndFormButton(
                        textResourceId = R.string.verify_email,
                        onClick = { viewModel.verify(code) },
                        enabled = code.isNotEmpty() && !uiState.isFetching,
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
                text = stringResource(R.string.verification),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.enter_code),
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
                    if (uiState.isFetching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    CustomTextField(
                        value = code,
                        onValueChange = { code = it },
                        labelResourceId = R.string.verification_code,
                        modifier = Modifier.fillMaxWidth()
                    )

                    EndFormButton(
                        textResourceId = R.string.verify_email,
                        onClick = { viewModel.verify(code) },
                        enabled = code.isNotEmpty() && !uiState.isFetching
                    )
                }
            }
        }
    }
}