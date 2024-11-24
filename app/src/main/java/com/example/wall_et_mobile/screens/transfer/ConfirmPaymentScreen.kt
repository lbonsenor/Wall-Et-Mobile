package com.example.wall_et_mobile.screens.transfer

import ContactCard
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ErrorDialog
import com.example.wall_et_mobile.components.SuccessDialog
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.model.BalancePayment
import com.example.wall_et_mobile.data.model.CardPayment
import com.example.wall_et_mobile.data.model.LinkPayment
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import kotlin.math.roundToInt

// should change name to last section screen or smth
@Composable
fun ConfirmPaymentScreen(
    innerPadding: PaddingValues,
    email: String,
    amount: String,
    paymentType: String,
    cardId: Int?,
    onPaymentComplete: () -> Unit = {},
    onChangeDestination: () -> Unit = {},
    onEditAmount: () -> Unit = {},
    viewModel: TransferViewModel = viewModel(
        factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
){
    val uiState = viewModel.uiState
    var note by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getBalance()
        viewModel.getCards()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            showSuccess = false
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
    ) {
        when {
            uiState.error != null -> {
                ErrorDialog(
                    visible = true,
                    message = uiState.error.message ?: "There has been an error in the transaction.",
                    onDismiss = viewModel::clearError
                )
            }
            showSuccess && !uiState.isFetching -> {
                SuccessDialog(
                    visible = true,
                    message = stringResource(R.string.payment_success),
                    onDismiss = { showSuccess = false },
                    onConfirm = onPaymentComplete
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            TransferProgress(2)

            Section(title = stringResource(R.string.transfer_to)) {
                ContactCard((email), Modifier.fillMaxWidth(), onChangeDestination)
            }

            Section(title = stringResource(R.string.transfer_amount)) {
                AmountDisplay(amount, onEditAmount)
            }

            Section(title = stringResource(R.string.payment_method)) {
                PaymentMethodDisplay(PaymentType.valueOf(paymentType), cardId, uiState.balance)
            }

            Section(title = "Message") {

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text(stringResource(R.string.add_note)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                        focusedBorderColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        }

        SwipeToSendButton(
            onSwipeComplete = {
                if (!uiState.isFetching) {
                    Log.d("SwipeToSendButton", "onSwipeComplete called")
                    Log.d("SwipeToSendButton", "email: $email, amount: $amount, note: $note")
                    try {
                        val parsedAmount = amount.toDoubleOrNull()
                        when {
                            parsedAmount == null || parsedAmount <= 0 -> {
                                throw IllegalArgumentException("Invalid amount")
                            }
                            email.isEmpty() -> {
                                throw IllegalArgumentException("Receiver email is required")
                            }
                            else -> {
                                val transaction = createTransactionRequest(
                                    paymentType = PaymentType.valueOf(paymentType),
                                    amount = parsedAmount,
                                    email = email,
                                    note = note,
                                    cardId = cardId
                                )
                                viewModel.makePayment(transaction)
                                showSuccess = true
                            }
                        }
                    } catch (e: Exception) {
                        viewModel.handleError(e)
                    }
                }
                 },
            isCompleted = showSuccess && !uiState.isFetching && uiState.error == null,
            isLoading = uiState.isFetching,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .fillMaxWidth()
        )
    }
}


@Composable
fun SwipeToSendButton(
    onSwipeComplete: () -> Unit,
    isCompleted: Boolean = false,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var width by remember { mutableStateOf(0) }
    val density = LocalDensity.current


    LaunchedEffect(isLoading) {
        if (isLoading) {
            offsetX = 0f
        }
    }

    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            offsetX = width - with(density) { 64.dp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .onSizeChanged { width = it.width }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.onSecondary
            )
        } else {
            Text(
                text = stringResource(R.string.swipe_to_send),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f)
            )

            // Swipe handle
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), 0) }
                    .size(64.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.onSecondary,
                                MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f)
                            )
                        )
                    )
                    .then(
                        if (!isLoading) {
                            Modifier.pointerInput(Unit) {
                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        if (offsetX > width * 0.5f) {
                                            offsetX = width - with(density) { 64.dp.toPx() }
                                            onSwipeComplete()
                                        } else {
                                            offsetX = 0f
                                        }
                                    },
                                    onDragCancel = {
                                        if (!isCompleted) offsetX = 0f
                                    },
                                    onHorizontalDrag = { change, dragAmount ->
                                        if (!isCompleted && !isLoading) {
                                            change.consume()
                                            offsetX = (offsetX + dragAmount).coerceIn(
                                                0f,
                                                width - with(density) { 64.dp.toPx() }
                                            )
                                        }
                                    }
                                )
                            }
                        } else Modifier
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = stringResource(R.string.swipe),
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(3) {
                    Text(
                        text = "›",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
    }

@Composable
private fun AmountDisplay(amount: String, onEditAmount: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Text(
                text = "$",
                style = TextStyle(
                    fontFamily = DarkerGrotesque,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp
                ),
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = amount.ifEmpty { "0.00" },
                style = TextStyle(
                    fontFamily = DarkerGrotesque,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 48.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        FilledTonalButton(
            onClick = onEditAmount,
            shape = CircleShape,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(stringResource(R.string.change))
        }
    }
}


@Composable
private fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        content()
    }
}





@Composable
private fun PaymentMethodDisplay(paymentType: PaymentType, cardId: Int?, balance: Double? = null) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = when (paymentType) {
                PaymentType.BALANCE -> painterResource(R.drawable.person)
                PaymentType.CARD -> painterResource(R.drawable.credit_card)
                PaymentType.LINK -> painterResource(R.drawable.transfer)
            },
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

        Column {
            Text(
                text = when (paymentType) {
                    PaymentType.BALANCE -> stringResource(R.string.wallet_balance)
                    PaymentType.CARD -> stringResource(R.string.card)
                    PaymentType.LINK -> stringResource(R.string.link)
                },
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (paymentType == PaymentType.CARD && cardId != null) {
                Text(
                    text = stringResource(R.string.card_number, cardId.toString()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (paymentType == PaymentType.BALANCE) {
                Text(
                    text = "$${balance}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun createTransactionRequest(
    paymentType: PaymentType,
    amount: Double,
    email: String,
    note: String,
    cardId: Int?
): TransactionRequest {
    val description = note.ifEmpty { "Payment to $email" }

    return when (paymentType) {
        PaymentType.BALANCE -> BalancePayment(
            amount = amount,
            description = description,
            receiverEmail = email
        )
        PaymentType.CARD -> {
            require(cardId != null) { "Card ID is required for card payments" }
            CardPayment(
                amount = amount,
                description = description,
                cardId = cardId.toLong(),
                receiverEmail = email
            )
        }
        PaymentType.LINK -> LinkPayment(
            amount = amount,
            description = description
        )
    }
}

@Composable
fun ConfirmPaymentScreenLandscape(
    innerPadding: PaddingValues,
    email: String,
    amount: String,
    paymentType: String,
    cardId: Int?,
    onPaymentComplete: () -> Unit = {},
    onChangeDestination: () -> Unit = {},
    onEditAmount: () -> Unit = {},
    viewModel: TransferViewModel = viewModel(
        factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
){
    val uiState = viewModel.uiState
    var note by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getBalance()
        viewModel.getCards()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            showSuccess = false
        }
    }

    Row {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 40.dp)
                .weight(0.4f)
                .verticalScroll(rememberScrollState())

        ) {
            TransferProgress(2)

            Section(title = stringResource(R.string.transfer_to)) {
                ContactCard((email), Modifier.fillMaxWidth(), onChangeDestination)
            }

            Section(title = stringResource(R.string.transfer_amount)) {
                AmountDisplay(amount, onEditAmount)
            }

            Section(title = stringResource(R.string.payment_method)) {
                PaymentMethodDisplay(PaymentType.valueOf(paymentType), cardId, uiState.balance)
            }
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .weight(0.4f)
        ) {
            Section(title = "Message") {

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text(stringResource(R.string.add_note)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                        focusedBorderColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
            SwipeToSendButton(
                onSwipeComplete = {
                    if (!uiState.isFetching) {
                        Log.d("SwipeToSendButton", "onSwipeComplete called")
                        Log.d("SwipeToSendButton", "email: $email, amount: $amount, note: $note")
                        try {
                            val parsedAmount = amount.toDoubleOrNull()
                            when {
                                parsedAmount == null || parsedAmount <= 0 -> {
                                    throw IllegalArgumentException("Invalid amount")
                                }
                                email.isEmpty() -> {
                                    throw IllegalArgumentException("Receiver email is required")
                                }
                                else -> {
                                    val transaction = createTransactionRequest(
                                        paymentType = PaymentType.valueOf(paymentType),
                                        amount = parsedAmount,
                                        email = email,
                                        note = note,
                                        cardId = cardId
                                    )
                                    viewModel.makePayment(transaction)
                                    showSuccess = true
                                }
                            }
                        } catch (e: Exception) {
                            viewModel.handleError(e)
                        }
                    }
                },
                isCompleted = showSuccess && !uiState.isFetching && uiState.error == null,
                isLoading = uiState.isFetching,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .fillMaxWidth()
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
    ) {
        when {
            uiState.error != null -> {
                ErrorDialog(
                    visible = true,
                    message = uiState.error.message ?: "There has been an error in the transaction.",
                    onDismiss = viewModel::clearError
                )
            }
            showSuccess && !uiState.isFetching -> {
                SuccessDialog(
                    visible = true,
                    message = stringResource(R.string.payment_success),
                    onDismiss = { showSuccess = false },
                    onConfirm = onPaymentComplete
                )
            }
        }




    }

}