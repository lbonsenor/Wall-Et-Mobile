package com.example.wall_et_mobile.data.model

import android.icu.util.Currency
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
    //val transactionTime: Timestamp?,
    val transactionType: TransactionType,
    val paymentType: PaymentType,
    val name: String,
    val card: Card?,
    var linkUuid: String?,
    var createdAt: Date? = null,
    var updatedAt: Date? = null
) {
    /*
    fun toLocalDate() : LocalDate{
        return transactionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }*/

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
            name = "Farmacity",
            transactionType = TransactionType.ONLINE_PAYMENT,
            amount = CurrencyAmount(100.0, Currency.getInstance("ARS")),
            //transactionTime = Timestamp(System.currentTimeMillis()),
            paymentType = PaymentType.BALANCE,
            card = null,
            transactionId = 10,
            balanceBefore = 20.0.toFloat(),
            balanceAfter = 10.0.toFloat(),
            pending = false,
            linkUuid = null,
            createdAt = null,
            updatedAt = null,
        )
    }
}
