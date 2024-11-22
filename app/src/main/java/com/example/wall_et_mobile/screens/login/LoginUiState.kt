package com.example.wall_et_mobile.screens.login

data class LoginUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val error: Error? = null
)

val LoginUiState.canGetCurrentUser: Boolean get() = isAuthenticated
