package com.example.wall_et_mobile.data.repository

import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.Wallet
import com.example.wall_et_mobile.data.network.WalletRemoteDataSource
import com.example.wall_et_mobile.data.network.model.NetworkAliasUpdate
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class WalletRepository(
    private val remoteDataSource: WalletRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe.
    private val cardsMutex = Mutex()
    // Cache of the latest sports got from the network.
    private var cards: List<Card> = emptyList()

    private var wallet : Wallet? = null

    suspend fun getCards(refresh: Boolean = false): List<Card> {
        if (refresh || cards.isEmpty()) {
            val result = remoteDataSource.getCards()
            // Thread-safe write to sports
            cardsMutex.withLock {
                this.cards = result.map { it.asModel() }
            }
        }

        return cardsMutex.withLock { this.cards }
    }

    suspend fun addCard(card: Card) : Card {
        val newCard = remoteDataSource.addCard(card.asNetworkModel()).asModel()
        cardsMutex.withLock {
            this.cards = emptyList()
        }
        return newCard
    }

    suspend fun deleteCard(cardId: Int) {
        remoteDataSource.deleteCard(cardId)
        cardsMutex.withLock {
            this.cards = emptyList()
        }
    }

    suspend fun getBalance() : Double {
        /* always update wallet, bc maybe a payment has been made*/
        this.wallet = null
        return remoteDataSource.getBalance().asDouble()
    }

    suspend fun updateAlias(newAlias : String) : Wallet {
        this.wallet = null
        return remoteDataSource.updateAlias(NetworkAliasUpdate(alias = newAlias)).asModel()
    }

    suspend fun getWallet() : Wallet? {
        if(this.wallet === null){
            this.wallet = remoteDataSource.getWallet().asModel()
        }
        return this.wallet
    }
}