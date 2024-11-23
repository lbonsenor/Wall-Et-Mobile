package com.example.wall_et_mobile.screens.transfer

import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.data.model.Wallet

data class TransferUiState(
    val isAuthenticated : Boolean = false,
    val wallet: Wallet? = null,
    val transaction: Transaction? = null,
    val isFetching: Boolean = false,
    val error: Error? = null,
    val transactions: List<Transaction> = emptyList(),
)

