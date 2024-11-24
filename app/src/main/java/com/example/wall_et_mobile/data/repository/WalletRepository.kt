package com.example.wall_et_mobile.data.repository

import android.util.Log
import com.example.wall_et_mobile.data.model.Card
import com.example.wall_et_mobile.data.model.Wallet
import com.example.wall_et_mobile.data.network.WalletRemoteDataSource
import com.example.wall_et_mobile.data.network.model.NetworkAliasUpdate
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class WalletRepository(
    private val remoteDataSource: WalletRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe.
    private val cardsMutex = Mutex()
    // Cache of the latest sports got from the network.
    private var cards: List<Card> = emptyList()
    private val walletMutex = Mutex()

    private var wallet : Wallet? = null

    val walletStream: Flow<Wallet> =
        remoteDataSource.walletStream
            .map { it.asModel() }

    suspend fun getCards(refresh: Boolean = true): List<Card> {
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
        val newCard = remoteDataSource.addCard(card.asNetworkRequest()).asModel()
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

//    suspend fun getBalance() : Double {
//        /* always update wallet, bc maybe a payment has been made*/
//        this.wallet = remoteDataSource.getWallet().asModel()
//        val currentBalance = remoteDataSource.getBalance().asDouble()
//        Log.d("WalletRepository", "Balance: $currentBalance")
//        return currentBalance
//    }
suspend fun getBalance(): Double {
    try {
        // Get fresh balance from API
        val currentBalance = remoteDataSource.getBalance().asDouble()

        // Update wallet cache in background
        coroutineScope {
            launch {
                walletMutex.withLock {
                    wallet = remoteDataSource.getWallet().asModel()
                }
            }
        }

        Log.d("WalletRepository", "Balance: $currentBalance")
        return currentBalance
    } catch (e: Exception) {
        Log.e("WalletRepository", "Error getting balance", e)
        throw e
    }
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