package com.example.wall_et_mobile.data.network

import com.example.wall_et_mobile.data.network.api.WalletApiService
import com.example.wall_et_mobile.data.network.model.NetworkCard
import retrofit2.Response

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

}