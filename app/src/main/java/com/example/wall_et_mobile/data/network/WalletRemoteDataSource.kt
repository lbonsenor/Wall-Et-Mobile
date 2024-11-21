package com.example.wall_et_mobile.data.network

import com.example.wall_et_mobile.data.network.api.WalletApiService
import com.example.wall_et_mobile.data.network.model.NetworkAliasUpdate
import com.example.wall_et_mobile.data.network.model.NetworkBalanceResponse
import com.example.wall_et_mobile.data.network.model.NetworkCard
import com.example.wall_et_mobile.data.network.model.NetworkWallet

class WalletRemoteDataSource(
    private val walletApiService: WalletApiService
) : RemoteDataSource() {

    suspend fun getCards(): List<NetworkCard> {
        return handleApiResponse {
            walletApiService.getCards()
        }
    }

    suspend fun addCard(card: NetworkCard): NetworkCard {
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

}