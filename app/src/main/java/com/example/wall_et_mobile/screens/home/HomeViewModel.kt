package com.example.wall_et_mobile.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.repository.TransactionRepository
import com.example.wall_et_mobile.data.repository.UserRepository
import com.example.wall_et_mobile.data.repository.WalletRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private var walletStreamJob : Job? = null
    private var paymentsStreamJob : Job? = null
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeWalletStream()
        observePaymentsStream()
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            try {
                val user = userRepository.getCurrentUser(refresh = true)
                _uiState.update { currentState ->
                    currentState.copy(user = user)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(error = handleError(e))
                }
            }
        }
    }

    private fun observePaymentsStream(){
        paymentsStreamJob = collectOnViewModelScope(
            transactionRepository.paymentStream
        ) { state, response -> state.copy(transactions = response) }
    }

    private fun observeWalletStream(){
        walletStreamJob = collectOnViewModelScope(
            walletRepository.walletStream
        ) { state, response -> state.copy(wallet = response) }
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (HomeUiState, T) -> HomeUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { currentState -> currentState.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { currentState -> updateState(currentState, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (HomeUiState, R) -> HomeUiState
    ) : Job = viewModelScope.launch {
        _uiState.update { currentState -> currentState.copy(isFetching = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { currentState -> updateState(currentState, response).copy(isFetching = false) }
        }.onFailure { e ->
            _uiState.update { currentState -> currentState.copy(isFetching = false, error = handleError(e)) }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.message)
        } else {
            Error(e.message ?: "")
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            application : MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    application.userRepository,
                    application.walletRepository,
                    application.transactionRepository
                ) as T
            }
        }
    }

}