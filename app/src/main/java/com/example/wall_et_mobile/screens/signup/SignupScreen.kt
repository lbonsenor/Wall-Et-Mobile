package com.example.wall_et_mobile.screens.signup

import android.content.res.Configuration
import android.util.Log
import android.util.Patterns
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import com.example.wall_et_mobile.components.PasswordField
import com.example.wall_et_mobile.data.model.RegisterUser

@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToVerification: () -> Unit,
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    var user by remember {
        mutableStateOf(
            RegisterUser(
                firstName = "",
                lastName = "",
                email = "",
                birthDate = "2000-10-04",
                password = ""
            )
        )
    }
    val uiState = viewModel.uiState
    var confirmPassword by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onNavigateToVerification()
        }
    }

    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        val passwordPattern = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    val isFormValid = remember(user, confirmPassword) {
        user.firstName.isNotEmpty() &&
                user.lastName.isNotEmpty() &&
                validateEmail(user.email) &&
                validatePassword(user.password) &&
                user.password == confirmPassword
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
                    text = stringResource(R.string.create_account),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.signup_to_continue),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .weight(0.6f, fill = false)
                    .widthIn(max = 450.dp)
                    .fillMaxHeight(0.95f),
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
                        CustomTextField(
                            value = user.firstName,
                            onValueChange = { user = user.copy(firstName = it) },
                            labelResourceId = R.string.name,
                            modifier = Modifier.weight(1f)
                        )

                        CustomTextField(
                            value = user.lastName,
                            onValueChange = { user = user.copy(lastName = it) },
                            labelResourceId = R.string.last_name,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    CustomTextField(
                        value = user.email,
                        onValueChange = { user = user.copy(email = it) },
                        labelResourceId = R.string.email,
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = stringResource(R.string.invalid_email),
                        validate = { newEmail -> validateEmail(newEmail) }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PasswordField(
                            password = user.password,
                            onPasswordChange = { user = user.copy(password = it) },
                            modifier = Modifier.weight(1f),
                            label = R.string.password,
                            errorMessage = stringResource(R.string.password_format),
                            validate = { newPassword -> validatePassword(newPassword) }
                        )

                        PasswordField(
                            password = confirmPassword,
                            onPasswordChange = { confirmPassword = it },
                            modifier = Modifier.weight(1f),
                            isRepeatPassword = true,
                            originalPassword = user.password,
                            label = R.string.repeat,
                            errorMessage = stringResource(R.string.passwords_dont_match)
                        )
                    }

                    EndFormButton(
                        textResourceId = R.string.sign_up,
                        onClick = { 
                            viewModel.signup(user)
                            Log.d("User", user.toString())
                        },
                        enabled = isFormValid && !uiState.isFetching,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.already_have_account),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextButton(onClick = onNavigateUp) {
                            Text(
                                stringResource(R.string.log_in),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                    }
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
                text = stringResource(R.string.create_account),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.signup_to_continue),
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
                    CustomTextField(
                        value = user.firstName,
                        onValueChange = { user = user.copy(firstName = it) },
                        labelResourceId = R.string.name,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomTextField(
                        value = user.lastName,
                        onValueChange = { user = user.copy(lastName = it) },
                        labelResourceId = R.string.last_name,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomTextField(
                        value = user.email,
                        onValueChange = { user = user.copy(email = it) },
                        labelResourceId = R.string.email,
                        modifier = Modifier.fillMaxWidth(),
                        errorMessage = stringResource(R.string.invalid_email),
                        validate = { newEmail -> validateEmail(newEmail) }
                    )

                    PasswordField(
                        password = user.password, // not right, should change later
                        onPasswordChange = { user = user.copy(password = it) },
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
                        originalPassword = user.password, // !!!
                        label = R.string.repeat_password,
                        errorMessage = stringResource(R.string.passwords_dont_match)
                    )

                    EndFormButton(
                        textResourceId = R.string.sign_up,
                        onClick = {
                            viewModel.signup(user)
                            Log.d("User", user.toString())
                        },
                        enabled = isFormValid && !uiState.isFetching
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.already_have_account),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextButton(
                            onClick = onNavigateUp
                        ) {
                            Text(
                                stringResource(R.string.log_in),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                    }
                }
            }
        }
    }
}
