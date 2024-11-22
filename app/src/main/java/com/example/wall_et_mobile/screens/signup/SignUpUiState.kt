package com.example.wall_et_mobile.screens.signup

data class SignUpUiState(
    val isRegistered: Boolean = false,
    val isVerified: Boolean = false,
    val isFetching: Boolean = false,
    val error: Error? = null
)
