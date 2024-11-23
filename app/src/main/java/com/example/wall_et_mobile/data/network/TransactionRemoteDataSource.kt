package com.example.wall_et_mobile.data.network

import com.example.wall_et_mobile.data.network.api.TransactionApiService
import com.example.wall_et_mobile.data.network.model.NetworkTransaction
import com.example.wall_et_mobile.data.network.model.NetworkTransactionLinkRequest
import com.example.wall_et_mobile.data.network.model.NetworkTransactionList
import com.example.wall_et_mobile.data.network.model.NetworkTransactionRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TransactionRemoteDataSource (private val transactionApiService: TransactionApiService)
    : RemoteDataSource() {

    val paymentsStream: Flow<NetworkTransactionList> = flow {
        while (true) {
            val payments = handleApiResponse {
                transactionApiService.getPayments(
                    page = 1,
                    direction = "DESC",
                    pending = null,
                    type = null,
                    range = null,
                    source = null,
                    cardId = null
                )
            }
            emit(payments)
            delay(DELAY)
        }
    }

    suspend fun makePayment(paymentInfo : NetworkTransactionRequest) {
        return handleApiResponse {
            transactionApiService.makePayment(paymentInfo)
        }
    }

    suspend fun getPayments(page : Int, direction: String,
                            pending : Boolean?, type : String?,
                            range : String?, source : String?,
                            cardId: Int?) : NetworkTransactionList {
        return handleApiResponse {
            transactionApiService.getPayments(page,direction,
                pending,type,range,source,cardId)
        }
    }

    suspend fun getPayment(paymentId : Int) : NetworkTransaction {
        return handleApiResponse {  transactionApiService.getPayment(paymentId)}
    }

    suspend fun getPaymentLinkInfo(linkUuid : String) : NetworkTransaction {
        return handleApiResponse { transactionApiService.getPaymentLinkInfo(linkUuid) }
    }
    /* returns success on successful link payment */
    suspend fun settlePaymentLink(linkUuid: String, requestBody : NetworkTransactionLinkRequest) : Unit{
        return handleApiResponse { transactionApiService.settlePaymentLink(linkUuid, requestBody) }
    }

    companion object {
        const val DELAY : Long = 10000
    }
}