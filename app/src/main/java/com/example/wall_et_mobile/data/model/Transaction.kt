package com.example.wall_et_mobile.data.model

import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.network.model.NetworkTransaction
import java.util.Date
import kotlin.Float

data class Transaction(
    val transactionId: Int = 0,
    val amount: CurrencyAmount,
    var balanceBefore: Float,
    var balanceAfter : Float,
    var pending : Boolean,
    val transactionType: TransactionType,
    val paymentType: PaymentType,
    val card: Card? = null,
    var linkUuid: String? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var payer: User,
    var receiver: User
) {
    fun asNetworkModel() : NetworkTransaction{
        return NetworkTransaction(
            id = transactionId,
            type = paymentType.toString(),
            amount = amount.number as Double,
            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            pending = pending,
            card = card?.asNetworkCard(),
            linkUuid = linkUuid,
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString(),
            payer = payer.asNetworkModel(),
            receiver = receiver.asNetworkModel(),
            receiverBalanceBefore = 0f,
            receiverBalanceAfter = 0f
        )
    }
}
