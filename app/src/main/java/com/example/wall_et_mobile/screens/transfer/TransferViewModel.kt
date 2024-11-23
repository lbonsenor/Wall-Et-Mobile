package com.example.wall_et_mobile.screens.transfer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.data.repository.TransactionRepository
import com.example.wall_et_mobile.data.repository.WalletRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TransferViewModel (
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    var uiState by mutableStateOf(TransferUiState())
        private set

    fun getBalance() = runOnViewModelScope(
        block = { walletRepository.getBalance() },
        updateState = { state, balance ->
            state.copy(balance = balance)
        },
    )

    fun getCards() = runOnViewModelScope(
        block = { walletRepository.getCards() },
        updateState = { state, cards ->
            state.copy(cards = cards)
        }
    )

    fun makePayment(payment: TransactionRequest) = runOnViewModelScope(
        { transactionRepository.makePayment(payment) },
        { state, _ -> state.copy() }
    )

    fun getPaymentLinkInfo(linkUuid : String) = runOnViewModelScope(
        { transactionRepository.getPaymentLinkInfo(linkUuid) },
        { state, _ -> state.copy() }
    )

    fun settlePaymentLink(linkUuid: String, requestBody : TransactionLinkRequest) = runOnViewModelScope(
        { transactionRepository.settlePaymentLink(linkUuid,requestBody) },
        { state, _ -> state.copy() }
    )

    fun getWallet() = runOnViewModelScope(
        { walletRepository.getWallet() },
        { state, _ -> state.copy() }
    )

    fun getPayment(paymentId : Int) = runOnViewModelScope(
        { transactionRepository.getPayment(false, paymentId) },
        { state, _ -> state.copy() }
    )

    fun getPayments(page: Int, direction : String, pending : Boolean?, type : String?, range : String?, source : String?, cardId : Int?) = runOnViewModelScope(
        { transactionRepository.getPayments(false, page,direction,pending,type,range,source,cardId) },
        { state, payments -> state.copy(transactions = payments)  }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (TransferUiState, R) -> TransferUiState
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
        const val TAG = "TransferViewModel"

        fun provideFactory(
            application: MyApplication
        ) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TransferViewModel(
                    application.walletRepository,
                    application.transactionRepository
                ) as T
            }
        }
    }

}