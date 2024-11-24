package com.example.wall_et_mobile.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.SessionManager
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun checkAuthenticationState() {
        uiState = uiState.copy(isAuthenticated = sessionManager.loadAuthToken() != null)
    }

    fun login(username: String, password: String) = runOnViewModelScope(
        { userRepository.login(username, password) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun logout() = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ -> state.copy(isAuthenticated = false) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (LoginUiState, R) -> LoginUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))
            Log.e(TAG, "Coroutine exec failed", e)
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            when (e.message) {
                "User not found" -> Error("user_not_found")
                "Invalid credentials" -> Error("invalid_credentials")
                else -> Error("unexpected_error")
            }
        } else {
            Error("unexpected_error")
        }
    }

    fun clearError() {
        uiState = uiState.copy(error = null)
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            application: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    application.sessionManager,
                    application.userRepository
                ) as T
            }
        }
    }
}