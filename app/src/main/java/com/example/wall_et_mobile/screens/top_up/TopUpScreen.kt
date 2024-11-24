package com.example.wall_et_mobile.screens.top_up


import AmountTextField
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ErrorDialog
import com.example.wall_et_mobile.components.SelectedOption
import com.example.wall_et_mobile.components.SuccessDialog
import com.example.wall_et_mobile.data.model.RechargeRequest
import com.example.wall_et_mobile.screens.transfer.TransferViewModel
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import com.example.wall_et_mobile.ui.theme.Gray
import parseAmount
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun TopUpScreen(
    innerPadding : PaddingValues,
    onConfirm: () -> Unit = {},
    viewModel: TopUpViewModel = viewModel(factory = TopUpViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
)
{
    val uiState = viewModel.uiState

    var showSuccess by remember { mutableStateOf(false) }

    var amount by remember { mutableStateOf("0") }
    var cents by remember { mutableStateOf("00") }

    val amountValue by derivedStateOf { parseAmount(amount, cents) }
    val isEnabled = when {
        amountValue <= 0.0 -> false
        else -> true
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        AmountTextField(amount, cents, { amount = it }, { cents = it })

        Button(
            onClick = {
                viewModel.recharge(RechargeRequest(amountValue))
            },
            enabled = isEnabled,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = Gray.copy(0.1f),
                disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
        ) {
            when {
                uiState.isFetching -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        strokeWidth = 2.dp)
                }
                uiState.error != null -> {
                    ErrorDialog(
                        visible = true,
                        message = uiState.error.message ?: "There has been an error in the top up.",
                        onDismiss = viewModel::clearError
                    )
                }
                uiState.success && !uiState.isFetching -> {
                    SuccessDialog(
                        visible = true,
                        title = stringResource(R.string.add_funds_success_title),
                        message = stringResource(R.string.add_funds_success),
                        onDismiss = { showSuccess = false },
                        onConfirm = onConfirm
                    )
                }
                else -> {
                    Text(stringResource(R.string.continue_button))
                }
            }

        }
    }

}

fun formatAmount(amount: String): String {
    if (amount.isEmpty()) return "0.00"
    return try {
        val number = amount.toDoubleOrNull() ?: return ""
        if (number >= 6000000) return "6,000,000.00"
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        formatter.format(number)
    } catch (e: Exception) {
        ""
    }
}

