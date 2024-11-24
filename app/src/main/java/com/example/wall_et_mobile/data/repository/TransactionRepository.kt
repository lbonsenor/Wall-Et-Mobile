package com.example.wall_et_mobile.data.repository


import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import com.example.wall_et_mobile.data.model.TransactionRequest
import com.example.wall_et_mobile.data.model.asNetworkModel
import com.example.wall_et_mobile.data.network.TransactionRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TransactionRepository(
    private val remoteDataSource: TransactionRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe.
    private val transMutex = Mutex()
    // Cache of the latest  got from the network.
    private var transactions: List<Transaction> = emptyList()

    val paymentStream: Flow<List<Transaction>> =
        remoteDataSource.paymentsStream
            .map {  it.asModel() }

    suspend fun getPayments(
        refresh: Boolean = false,
        page: Int = 1,
        direction : String = "DESC",
        pending : Boolean? = null,
        type : String? = null,
        range : String? = null,
        source : String? = null,
        cardId : Int? = null
    ): List<Transaction> {
        if (refresh || transactions.isEmpty()) {
            when (source) {
                "RECEIVER" -> {
                    val result = remoteDataSource.getPayments(page,direction,pending,type,range,source,cardId)
                    // Thread-safe write to payments
                    transMutex.withLock {
                        this.transactions = result.asModelSwitched()
                    }
                }

                "PAYER" -> {
                    val result = remoteDataSource.getPayments(page,direction,pending,type,range,source,cardId)
                    // Thread-safe write to payments
                    transMutex.withLock {
                        this.transactions = result.asModel()
                    }
                }

                else -> {
                    val result = remoteDataSource.getPayments(page,direction,pending,type,range, source="PAYER",cardId)
                    val result2 = remoteDataSource.getPayments(page,direction,pending,type,range,source="RECEIVER",cardId)

                    transMutex.withLock {
                        this.transactions = result2.asModelSwitched() + result.asModel()
                    }
                }

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

