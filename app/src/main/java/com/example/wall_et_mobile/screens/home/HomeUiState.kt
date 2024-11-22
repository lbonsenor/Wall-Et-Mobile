package com.example.wall_et_mobile.screens.home

import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.data.model.Wallet

data class HomeUiState(
    val isAuthenticated : Boolean = false,
    val isFetching: Boolean = false,
    val wallet: Wallet? = null,
    val transactions: List<Transaction> = emptyList<Transaction>(),
    val user: User? = null,
    val error: Error? = null
)

