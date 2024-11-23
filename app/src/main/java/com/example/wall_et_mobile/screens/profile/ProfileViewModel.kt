package com.example.wall_et_mobile.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.SessionManager
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.data.model.Wallet
import com.example.wall_et_mobile.data.repository.UserRepository
import com.example.wall_et_mobile.data.repository.WalletRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val wallet: Wallet? = null,
    val isFetching: Boolean = false,
    val error: Error? = null
)

class ProfileViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {
    private var walletStreamJob: Job? = null
    var uiState by mutableStateOf(ProfileUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    init {
        if (uiState.isAuthenticated) {
            observeWalletStream()
            fetchUserData()
        }
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            uiState = uiState.copy(isFetching = true)
            try {
                val user = userRepository.getCurrentUser(true)
                uiState = uiState.copy(user = user, isFetching = false)
            } catch (e: Exception) {
                uiState = uiState.copy(error = handleError(e), isFetching = false)
            }
        }
    }

    private fun observeWalletStream() {
        walletStreamJob = viewModelScope.launch {
            walletRepository.walletStream
                .distinctUntilChanged()
                .catch { e -> uiState = uiState.copy(error = handleError(e)) }
                .collect { wallet -> 
                    uiState = uiState.copy(wallet = wallet)
                }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.message ?: "")
        } else {
            Error(e.message ?: "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        walletStreamJob?.cancel()
    }

    companion object {
        fun provideFactory(
            application: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(
                    sessionManager = application.sessionManager,
                    userRepository = application.userRepository,
                    walletRepository = application.walletRepository
                ) as T
            }
        }
    }
}