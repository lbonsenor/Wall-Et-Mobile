package com.example.wall_et_mobile.screens.transfer

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
import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.data.repository.TransactionRepository
import com.example.wall_et_mobile.data.repository.UserRepository
import com.example.wall_et_mobile.data.repository.WalletRepository
import com.example.wall_et_mobile.screens.login.LoginUiState
import com.example.wall_et_mobile.screens.login.LoginViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransferViewModel (
    sessionManager: SessionManager,
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    var uiState by mutableStateOf(TransferUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun makePayment(payment: TransactionRequest) = runOnViewModelScope(
        { transactionRepository.makePayment(payment) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun getPaymentLinkInfo(linkUuid : String) = runOnViewModelScope(
        { transactionRepository.getPaymentLinkInfo(linkUuid) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun settlePaymentLink(linkUuid: String, requestBody : TransactionLinkRequest) = runOnViewModelScope(
        { transactionRepository.settlePaymentLink(linkUuid,requestBody) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun getWallet() = runOnViewModelScope(
        { walletRepository.getWallet() },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun getPayment(paymentId : Int) = runOnViewModelScope(
        { transactionRepository.getPayment(false, paymentId) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun getPayments(page: Int, direction : String, pending : String?, type : String?, range : String?, source : String?, cardId : Int?) = runOnViewModelScope(
        { transactionRepository.getPayments(false, page,direction,pending,type,range,source,cardId) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (TransferUiState, R) -> TransferUiState
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
        const val TAG = "UI Layer"

        fun provideFactory(
            application: MyApplication
        ) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TransferViewModel(
                    application.sessionManager,
                    application.walletRepository,
                    application.transactionRepository
                ) as T
            }
        }
    }

}