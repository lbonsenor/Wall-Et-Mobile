package com.example.wall_et_mobile.data.model

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.mock.MockContacts
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
    var payer: User? = null,
    var receiver: User? = MockContacts.sampleContacts[0]
) {
    fun asNetworkModel() : NetworkTransaction{
        return NetworkTransaction(
            id = transactionId,
            type = paymentType.toString(),
            amount = amount.number as Double,
            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            pending = pending,
            card = card?.asNetworkModel(),
            linkUuid = linkUuid,
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString(),
        )
    }

    companion object {
        val Test = Transaction(
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            paymentType = PaymentType.BALANCE,
            card = null,
            transactionId = 10,
            balanceBefore = 20.0.toFloat(),
            balanceAfter = 10.0.toFloat(),
            pending = false,
            linkUuid = null,
            createdAt = null,
            updatedAt = null,
            receiver = MockContacts.sampleContacts[0]
        )
    }
}
