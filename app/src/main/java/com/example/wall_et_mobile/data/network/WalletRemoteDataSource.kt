package com.example.wall_et_mobile.data.network

import com.example.wall_et_mobile.data.network.api.WalletApiService
import com.example.wall_et_mobile.data.network.model.NetworkAliasUpdate
import com.example.wall_et_mobile.data.network.model.NetworkBalanceResponse
import com.example.wall_et_mobile.data.network.model.NetworkCard
import com.example.wall_et_mobile.data.network.model.NetworkCardRequest
import com.example.wall_et_mobile.data.network.model.NetworkWallet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WalletRemoteDataSource(
    private val walletApiService: WalletApiService
) : RemoteDataSource() {

    val walletStream : Flow<NetworkWallet> = flow {
        while (true) {
            val wallet = handleApiResponse {
                walletApiService.getWallet()
            }
            emit(wallet)
            delay(DELAY)
        }
    }

    suspend fun getCards(): List<NetworkCard> {
        return handleApiResponse {
            walletApiService.getCards()
        }
    }

    suspend fun addCard(card: NetworkCardRequest): NetworkCard {
        return handleApiResponse {
            walletApiService.addCard(card)
        }
    }

    suspend fun deleteCard(cardId: Int){
        handleApiResponse {
            walletApiService.deleteCard(cardId)
        }
    }

    suspend fun getBalance() : NetworkBalanceResponse {
        return handleApiResponse { walletApiService.getBalance() }
    }

    suspend fun updateAlias(alias : NetworkAliasUpdate) : NetworkWallet {
        return handleApiResponse { walletApiService.updateAlias(alias) }
    }

    suspend fun getWallet() : NetworkWallet {
        return handleApiResponse { walletApiService.getWallet() }
    }

    companion object {
        const val DELAY : Long = 5000
    }

}