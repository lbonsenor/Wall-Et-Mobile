package com.example.wall_et_mobile.screens.transfer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.BalancePayment
import com.example.wall_et_mobile.data.model.CardPayment
import com.example.wall_et_mobile.data.model.LinkPayment
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque

@Composable
fun ConfirmLinkPaymentScreen(
    innerPadding: PaddingValues,
    link: String? = null,
    onPaymentComplete: () -> Unit = {},
    viewModel: TransferViewModel = viewModel(
        factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
){
    TODO()
//    val uiState = viewModel.uiState
//    var showSuccess by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        viewModel.getBalance()
//        viewModel.getCards()
//    }
//
//    LaunchedEffect(uiState.error) {
//        if (uiState.error != null) {
//            showSuccess = false
//        }
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
//    ) {
//        when {
//            uiState.error != null -> {
//                ErrorDialog(
//                    visible = true,
//                    message = uiState.error.message ?: "There has been an error in the transaction.",
//                    onDismiss = viewModel::clearError
//                )
//            }
//            showSuccess && !uiState.isFetching -> {
//                SuccessDialog(
//                    visible = true,
//                    message = stringResource(R.string.payment_success),
//                    onDismiss = { showSuccess = false },
//                    onConfirm = onPaymentComplete
//                )
//            }
//        }
//
//        Column(
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.spacedBy(24.dp),
//            modifier = Modifier
//                .padding(innerPadding)
//                .padding(24.dp)
//        ) {
//            TransferProgress(2)
//
//            Section(title = stringResource(R.string.transfer_to)) {
//                ContactCard((link), Modifier.fillMaxWidth(), onChangeDestination)
//            }
//
//            Section(title = stringResource(R.string.transfer_amount)) {
//                AmountLinkDisplay(uiState.transaction!!.amount.number.toString(), onEditAmount)
//            }
//
//
//
//            Section(title = "Message") {
//
//            }
//        }
//
//        SwipeToSendButton(
//            onSwipeComplete = {
//                if (!uiState.isFetching) {
//                    try {
//                        viewModel.settlePaymentLink(link, TransactionLinkRequest(TODO()))
//                        showSuccess = true
//                    } catch (e: Exception) {
//                        viewModel.handleError(e)
//                    }
//                }
//                 },
//            isCompleted = showSuccess && !uiState.isFetching && uiState.error == null,
//            isLoading = uiState.isFetching,
//            modifier = Modifier
//                .padding(horizontal = 24.dp)
//                .padding(WindowInsets.navigationBars.asPaddingValues())
//                .fillMaxWidth()
//        )
//    }
}

@Composable
private fun AmountLinkDisplay(amount: String, onEditAmount: () -> Unit) {
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
fun ConfirmLinkPaymentScreenLandscape(
    innerPadding: PaddingValues,
    link: String,
    onPaymentComplete: () -> Unit = {},
    viewModel: TransferViewModel = viewModel(
        factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
){
//    val uiState = viewModel.uiState
//    var note by remember { mutableStateOf("") }
//    var showSuccess by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        viewModel.getBalance()
//        viewModel.getCards()
//    }
//
//    LaunchedEffect(uiState.error) {
//        if (uiState.error != null) {
//            showSuccess = false
//        }
//    }
//
//    Row {
//        Column(
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
//            modifier = Modifier
//                .padding(innerPadding)
//                .padding(horizontal = 40.dp)
//                .weight(0.4f)
//                .verticalScroll(rememberScrollState())
//
//        ) {
//            TransferProgress(2)
//
//            Section(title = stringResource(R.string.transfer_to)) {
////                ContactCard((email), Modifier.fillMaxWidth(), onChangeDestination)
//            }
//
//            Section(title = stringResource(R.string.transfer_amount)) {
//                AmountLinkDisplay(uiState.transaction!!.amount.number.toString(), onEditAmount)
//            }
//
//        }
//
//        Column (
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceAround,
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxHeight()
//                .weight(0.4f)
//        ) {
//            Section(title = "Message") {
//
//                OutlinedTextField(
//                    value = note,
//                    onValueChange = { note = it },
//                    placeholder = { Text(stringResource(R.string.add_note)) },
//                    shape = RoundedCornerShape(12.dp),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
//                        focusedBorderColor = MaterialTheme.colorScheme.secondary
//                    ),
//                    modifier = Modifier.fillMaxWidth(),
//                    maxLines = 3
//                )
//            }
//            SwipeToSendButton(
//                onSwipeComplete = {
////                    if (!uiState.isFetching) {
////                        try {
////                            val parsedAmount = amount.toDoubleOrNull()
////                            when {
////                                parsedAmount == null || parsedAmount <= 0 -> {
////                                    throw IllegalArgumentException("Invalid amount")
////                                }
////                                email.isEmpty() -> {
////                                    throw IllegalArgumentException("Receiver email is required")
////                                }
////                                else -> {
////                                    val transaction = createTransactionRequest(
////                                        paymentType = PaymentType.valueOf(paymentType),
////                                        amount = parsedAmount,
////                                        email = email,
////                                        note = note,
////                                        cardId = cardId
////                                    )
////                                    viewModel.makePayment(transaction)
////                                    showSuccess = true
////                                }
////                            }
////                        } catch (e: Exception) {
////                            viewModel.handleError(e)
////                        }
////                    }
//                },
//                isCompleted = showSuccess && !uiState.isFetching && uiState.error == null,
//                isLoading = uiState.isFetching,
//                modifier = Modifier
//                    .padding(horizontal = 24.dp)
//                    .padding(WindowInsets.navigationBars.asPaddingValues())
//                    .fillMaxWidth()
//            )
//        }
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
//    ) {
//        when {
//            uiState.error != null -> {
//                ErrorDialog(
//                    visible = true,
//                    message = uiState.error.message ?: "There has been an error in the transaction.",
//                    onDismiss = viewModel::clearError
//                )
//            }
//            showSuccess && !uiState.isFetching -> {
//                SuccessDialog(
//                    visible = true,
//                    message = stringResource(R.string.payment_success),
//                    onDismiss = { showSuccess = false },
//                    onConfirm = onPaymentComplete
//                )
//            }
//        }
//
//
//
//
//    }

}