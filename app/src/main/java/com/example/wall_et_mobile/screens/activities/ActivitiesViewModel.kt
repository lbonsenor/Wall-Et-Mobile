package com.example.wall_et_mobile.screens.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.repository.TransactionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActivitiesViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private var paymentsStreamJob : Job? = null
    private val _uiState = MutableStateFlow(ActivitiesUiState())
    val uiState: StateFlow<ActivitiesUiState> = _uiState.asStateFlow()

    init {
        observePaymentsStream()

    }

    private fun observePaymentsStream(){
        paymentsStreamJob = collectOnViewModelScope(
            transactionRepository.paymentStream
        ) { state, response -> state.copy(transactions = response) }
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (ActivitiesUiState, T) -> ActivitiesUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { currentState -> currentState.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { currentState -> updateState(currentState, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (ActivitiesUiState, R) -> ActivitiesUiState
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
                return ActivitiesViewModel(
                    application.transactionRepository
                ) as T
            }
        }
    }
}