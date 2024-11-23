package com.example.wall_et_mobile.screens.activities

import com.example.wall_et_mobile.data.model.Transaction

data class ActivitiesUiState (
    val isAuthenticated : Boolean = false,
    val isFetching: Boolean = false,
    val error: Error? = null,
    val transactions: List<Transaction> = emptyList<Transaction>(),
    )