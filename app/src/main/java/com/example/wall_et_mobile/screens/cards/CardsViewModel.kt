package com.example.wall_et_mobile.screens.cards

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.repository.WalletRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CardsViewModel (
    private val walletRepository: WalletRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CardsUiState())
        private set

    fun getCards() = runOnViewModelScope(
        block = { walletRepository.getCards() },
        updateState = { state, cards ->
            state.copy(cards = cards)
        }
    )

    fun addCard(card : Card) = runOnViewModelScope(
        { walletRepository.addCard(card) },
        { state, _ -> state.copy() }
    )

    fun deleteCard(cardId : Int) = runOnViewModelScope(
        { walletRepository.deleteCard(cardId) },
        { state, _ -> state.copy() }
    )
    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (CardsUiState, R) -> CardsUiState
    ): Job = viewModelScope.launch {
        Log.d("TransferViewModel", "Starting operation: ${block.toString()}")
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            Log.d("TransferViewModel", "Operation completed successfully")
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            Log.d("TransferViewModel", "Operation failed: ${e.message}")
            uiState = uiState.copy(isFetching = false, error = handleError(e))
            Log.e(TAG, "Coroutine exec failed", e)
        }
    }

    fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.message ?: "")
        } else {
            Error(e.message ?: "")
        }
    }

    fun clearError() {
        uiState = uiState.copy(error = null)
    }

    companion object {
        const val TAG = "CardsViewModel"

        fun provideFactory(
            application: MyApplication
        ) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CardsViewModel(
                    application.walletRepository,
                ) as T
            }
        }
    }

}