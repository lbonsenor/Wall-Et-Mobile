package com.example.wall_et_mobile.data.repository

import com.example.wall_et_mobile.data.network.TransactionRemoteDataSource


import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import com.example.wall_et_mobile.data.model.TransactionRequest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.collections.map

class TransactionRepository(
    private val remoteDataSource: TransactionRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe.
    private val transMutex = Mutex()
    // Cache of the latest  got from the network.
    private var transactions: List<Transaction> = emptyList()

    suspend fun getPayments(refresh: Boolean = false, page: Int,
    direction : String,
    pending : String?,
    type : String?,
    range : String?,
    source : String?,
    cardId : Int?): List<Transaction> {
        if (refresh || transactions.isEmpty()) {
            val result = remoteDataSource.getPayments(page,direction,pending,type,range,source,cardId)
            // Thread-safe write to payments
            transMutex.withLock {
                this.transactions = result.map { it.asModel() }
            }
        }

        return transMutex.withLock { this.transactions }
    }

    suspend fun getPayment(refresh: Boolean = false, paymentId : Int) : Transaction{
        if(refresh || !isCached(paymentId)){
            val result = remoteDataSource.getPayment(paymentId)
            transMutex.withLock {
                // Add the fetched payment to the cached list
                val updatedTransaction = result.asModel()
                // Add or replace the specific payment in the cache
                this.transactions = transactions.filter { it.transactionId != paymentId } + updatedTransaction
            }

        }
        return transMutex.withLock {
            transactions.first { it.transactionId == paymentId }
        }
    }
    private fun isCached(paymentId: Int): Boolean {
        return transactions.any { it.transactionId == paymentId }
    }
    suspend fun makePayment(payment: TransactionRequest) {
        remoteDataSource.makePayment(payment.asNetworkModel())
        transMutex.withLock {
            this.transactions = emptyList()
        }
    }
    suspend fun getPaymentLinkInfo(linkUuid : String) : Transaction{
        return remoteDataSource.getPaymentLinkInfo(linkUuid).asModel()
    }
    suspend fun settlePaymentLink(linkUuid: String, requestBody : TransactionLinkRequest){
        remoteDataSource.settlePaymentLink(linkUuid,requestBody.asNetworkModel())
        transMutex.withLock {
            this.transactions = emptyList()
        }
    }




}
