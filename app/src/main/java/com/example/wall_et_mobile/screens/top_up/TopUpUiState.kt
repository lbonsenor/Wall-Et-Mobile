package com.example.wall_et_mobile.screens.top_up

import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.Wallet

data class TopUpUiState(
    val balance: Double? = null,
    val isFetching: Boolean = false,
    val error: Error? = null,
)


