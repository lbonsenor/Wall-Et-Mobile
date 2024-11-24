package com.example.wall_et_mobile.screens.transfer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.components.SelectedOption
import com.example.wall_et_mobile.data.DataSourceException
import com.example.wall_et_mobile.data.model.LinkPayment
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.data.model.asNetworkModel
import com.example.wall_et_mobile.data.repository.TransactionRepository
import com.example.wall_et_mobile.data.repository.WalletRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock

sealed class PaymentState {
    object Idle : PaymentState()
    object Loading : PaymentState()
    object Success : PaymentState()
    data class Error(val message: String) : PaymentState()
}

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
        block = {
            walletRepository.getCards()
            walletRepository.getCards(refresh = true)
        },
        updateState = { state, cards ->
            state.copy(cards = cards)
        }
    )

    fun makePayment(payment: TransactionRequest) = runOnViewModelScope(
        {
            uiState = uiState.copy(paymentState = PaymentState.Loading)
            transactionRepository.makePayment(payment)
            getPayments(
                page = 1,
                direction = "desc",
                pending = null,
                type = null,
                range = null,
                source = null,
                cardId = null
            )

            when (payment) {
                is LinkPayment -> {
                    val linkInfo = transactionRepository.getPayments(refresh = true)
                    linkInfo.firstOrNull() ?: throw IllegalStateException("Link payment info not found")
                }
                else -> {
                    walletRepository.getBalance()
                }
            }
        },
        { state, response ->
            when (payment) {
                is LinkPayment -> {
                    state.copy(
                        transactions = listOf(response as Transaction),
                        paymentState = PaymentState.Success,
                        isFetching = false
                    )
                }
                else -> {
                    state.copy(
                        balance = response as Double,
                        paymentState = PaymentState.Success,
                        isFetching = false
                    )
                }
            }
        }
    )

    fun getPaymentLinkInfo(linkUuid : String) = runOnViewModelScope(
        { transactionRepository.getPaymentLinkInfo(linkUuid) },
        { state, transaction -> state.copy(
            transaction = transaction
        ) }
    )

    fun settlePaymentLink(linkUuid: String, requestBody : TransactionLinkRequest) = runOnViewModelScope(
        { transactionRepository.settlePaymentLink(linkUuid,requestBody) },
        { state, response -> state.copy(
            settleSuccess = response
        ) }
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
        uiState = uiState.copy(
            error = null,
            paymentState = PaymentState.Idle
        )
    }

    fun resetPaymentState() {
        uiState = uiState.copy(paymentState = PaymentState.Idle)
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