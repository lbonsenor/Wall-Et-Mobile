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
import com.example.wall_et_mobile.components.CustomTextField
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.model.Screen
import com.example.wall_et_mobile.ui.theme.WallEtTheme


@Composable
fun VerificationCodeScreen(navController: NavController) {
    var code by remember { mutableStateOf("") }

    val isFormValid = remember(code) {
        code.isNotEmpty()
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
                CustomTextField(
                    value = code,
                    onValueChange = { code = it },
                    labelResourceId = R.string.verification_code,
                    modifier = Modifier.fillMaxWidth(),
                )

                EndFormButton(
                    textResourceId = R.string.validate,
                    onClick = { navController.navigate(Screen.NewPassword.route) },
                    enabled = isFormValid
                )
            }
        }
    }
}

@Preview(name = "LightMode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Composable
fun VerificationCodePreview() {
    WallEtTheme {
        VerificationCodeScreen(navController = NavController(LocalContext.current))
    }
}