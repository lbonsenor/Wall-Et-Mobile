package com.example.wall_et_mobile.screens.signup

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
import com.example.wall_et_mobile.data.model.RegisterUser
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.data.repository.UserRepository
import com.example.wall_et_mobile.screens.login.LoginUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignUpViewModel (
    private val userRepository: UserRepository
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun signup(user: RegisterUser) = runOnViewModelScope(
        { userRepository.register(user) },
        { state, _ -> state.copy(isRegistered = true) }
    )

    fun verify() = runOnViewModelScope(
        { userRepository.verify() },
        { state, _ -> state.copy(isVerified = true) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (SignUpUiState, R) -> SignUpUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))
            Log.e(TAG, "Registration Failed", e)
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.message ?: "")
        } else {
            Error(e.message ?: "")
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            application: MyApplication
        ) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignUpViewModel(
                    application.userRepository
                ) as T
            }
        }
    }
}