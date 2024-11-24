package com.example.wall_et_mobile.screens.transfer

import com.example.wall_et_mobile.components.SelectedOption
import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.Wallet

data class TransferUiState(
    //val isAuthenticated : Boolean = false,
    val wallet: Wallet? = null,
    val balance: Double? = null,
    val transaction: Transaction? = null,
    val cards: List<Card> = emptyList(),
    val isFetching: Boolean = true,
    val error: Error? = null,
    val transactions: List<Transaction> = emptyList(),
    val linkPaymentInfo: Transaction? = null,
    val paymentState: PaymentState = PaymentState.Idle,
)


