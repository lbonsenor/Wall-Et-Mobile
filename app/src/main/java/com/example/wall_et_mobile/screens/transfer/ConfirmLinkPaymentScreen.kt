package com.example.wall_et_mobile.screens.transfer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ErrorDialog
import com.example.wall_et_mobile.components.SelectedOption
import com.example.wall_et_mobile.components.SuccessDialog
import com.example.wall_et_mobile.components.TransferCardSlider
import com.example.wall_et_mobile.data.model.BalancePayment
import com.example.wall_et_mobile.data.model.CardPayment
import com.example.wall_et_mobile.data.model.LinkPayment
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque

@Composable
fun ConfirmLinkPaymentScreen(
    innerPadding: PaddingValues,
    link: String = "",
    onPaymentComplete: () -> Unit = {},
    viewModel: TransferViewModel = viewModel(
        factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
){
    val uiState = viewModel.uiState
    var showSuccess by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf<SelectedOption?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getBalance()
        viewModel.getCards()

        viewModel.getPaymentLinkInfo(linkUuid = link)
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
                    message = uiState.error.message
                        ?: "There has been an error in the payment link.",
                    onDismiss = onPaymentComplete

                )
            }

            uiState.settleSuccess == true && !uiState.isFetching -> {
                showSuccess = true
                SuccessDialog(
                    visible = true,
                    title = stringResource(R.string.success),
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
            Section(title = stringResource(R.string.transfer_to)) {
                uiState.transaction?.receiver?.let {
                    ContactCard(
                        it.name,
                        it.lastName,
                        Modifier.fillMaxWidth()
                    )
                }
            }

            Section(title = stringResource(R.string.transfer_amount)) {
                uiState.transaction?.amount.let {
                    AmountLinkDisplay(it.toString())
                }
            }

            Section(title = stringResource(R.string.payment_method)) {

                    TransferCardSlider(
                        cards = uiState.cards,
                        balance = uiState.balance ?: 0.0,
                        selectedOption = selectedPaymentMethod,
                        onSelectionChange = { selectedPaymentMethod = it }
                    )
            }

            SwipeToSendButton(
                onSwipeComplete = {
                    when (selectedPaymentMethod) {
                        is SelectedOption.CardOption -> viewModel.settlePaymentLink(
                            link,
                            TransactionLinkRequest(
                                "CARD",
                                (selectedPaymentMethod as SelectedOption.CardOption).card.cardId
                            )
                        )

                        is SelectedOption.WalletOption -> viewModel.settlePaymentLink(
                            link,
                            TransactionLinkRequest("BALANCE")
                        )

                        null -> TODO()
                    }
                },
                isCompleted = uiState.settleSuccess == true && !uiState.isFetching && uiState.error == null,
                isLoading = uiState.isFetching,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .fillMaxWidth()
            )
        }
    }

}

@Composable
private fun AmountLinkDisplay(amount: String) {
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
fun ContactCard(name: String, lastName: String, modifier : Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(R.drawable.link),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                .padding(10.dp)
        )
        Text(
            text = name + " " + lastName,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Clip
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
    val uiState = viewModel.uiState
    var showSuccess by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf<SelectedOption?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getBalance()
        viewModel.getCards()

        viewModel.getPaymentLinkInfo(linkUuid = link)
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            showSuccess = false
        }
    }

    Row(
        modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .weight(0.4f)
                .padding(vertical = 16.dp)
                .padding(end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when {
                uiState.error != null -> {
                    ErrorDialog(
                        visible = true,
                        message = uiState.error.message
                            ?: "There has been an error in the payment link.",
                        onDismiss = onPaymentComplete

                    )
                }

                uiState.settleSuccess == true && !uiState.isFetching -> {
                    showSuccess = true
                    SuccessDialog(
                        visible = true,
                        title = stringResource(R.string.success),
                        message = stringResource(R.string.payment_success),
                        onDismiss = { showSuccess = false },
                        onConfirm = onPaymentComplete
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
            ) {
                Section(title = stringResource(R.string.transfer_to)) {
                    uiState.transaction?.receiver?.let {
                        ContactCard(
                            it.name,
                            it.lastName,
                            Modifier.fillMaxWidth()
                        )
                    }
                }

                Section(title = stringResource(R.string.transfer_amount)) {
                    uiState.transaction?.amount.let {
                        AmountLinkDisplay(it.toString())
                    }
                }
            }


        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .weight(0.4f)
                .padding(vertical = 16.dp)
                .padding(end = 16.dp)
                .verticalScroll(rememberScrollState())
        ){
            Section(title = stringResource(R.string.payment_method)) {
            }
            TransferCardSlider(
                cards = uiState.cards,
                balance = uiState.balance ?: 0.0,
                selectedOption = selectedPaymentMethod,
                onSelectionChange = { selectedPaymentMethod = it }
            )

            SwipeToSendButton(
                onSwipeComplete = {
                    when (selectedPaymentMethod) {
                        is SelectedOption.CardOption -> viewModel.settlePaymentLink(
                            link,
                            TransactionLinkRequest(
                                "CARD",
                                (selectedPaymentMethod as SelectedOption.CardOption).card.cardId
                            )
                        )

                        is SelectedOption.WalletOption -> viewModel.settlePaymentLink(
                            link,
                            TransactionLinkRequest("BALANCE")
                        )

                        null -> TODO()
                    }
                },
                isCompleted = uiState.settleSuccess == true && !uiState.isFetching && uiState.error == null,
                isLoading = uiState.isFetching,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}