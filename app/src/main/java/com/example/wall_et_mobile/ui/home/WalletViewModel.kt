package com.example.wall_et_mobile.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.wall_et_mobile.model.RegisteredUser
import com.example.wall_et_mobile.network.WalletApi
import kotlinx.coroutines.launch
import okio.IOException

sealed interface WalletUiState {
    data class Success(val user: RegisteredUser) : WalletUiState
    data object Error : WalletUiState
    data object Loading : WalletUiState

}

class WalletViewModel : ViewModel() {
    var walletUiState : WalletUiState by mutableStateOf(WalletUiState.Loading)
        private set

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            walletUiState = WalletUiState.Loading

            walletUiState = try {
                val result = WalletApi.retrofitService.getUser()
                WalletUiState.Success(result)
            } catch (e: IOException) {
                WalletUiState.Error
            } catch (e: HttpException) {
                WalletUiState.Error
            }
        }
    }
}