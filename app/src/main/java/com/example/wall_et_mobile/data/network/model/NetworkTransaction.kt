package com.example.wall_et_mobile.data.network.model

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.TransactionType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.Int

@Serializable
data class NetworkTransactionList(
    val payments: List<NetworkTransaction>
){
    fun asModel(): List<Transaction>{
        return payments.map { netTransaction -> netTransaction.asModel() }
    }

    fun asModelSwitched(): List<Transaction>{
        return payments.map { netTransaction -> netTransaction.asModelSwitched() }
    }

    fun switched(): NetworkTransactionList{
        return NetworkTransactionList(payments.map { netTransaction -> netTransaction.switched() })
    }
}

@Serializable
data class NetworkTransaction(
    var id : Int,
    var type: String,
    var amount: Double,
    var balanceBefore: Float,
    var balanceAfter : Float,
    var receiverBalanceBefore : Float,
    var receiverBalanceAfter : Float,
    var pending: Boolean,
    var linkUuid: String?,
    var createdAt: String, /* turn to local date afterwards*/
    var updatedAt: String,
    var card : NetworkCard?,
    var payer : NetworkUser,
    var receiver : NetworkUser
){
    fun switched(): NetworkTransaction{
        return NetworkTransaction(
            id = id,
            type = type,
            amount = amount,
            balanceBefore = receiverBalanceBefore,
            balanceAfter = receiverBalanceAfter,
            receiverBalanceBefore = balanceBefore,
            receiverBalanceAfter = balanceAfter,
            pending = pending,
            linkUuid = linkUuid,
            createdAt = createdAt,
            updatedAt = updatedAt,
            card = card,
            payer = receiver,
            receiver = payer
        )
    }

    fun asModel(): Transaction{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return Transaction(
            transactionId = id,
            amount = CurrencyAmount(amount, Currency.getInstance("ARS")),
            paymentType = when (type) {
                "CARD" -> PaymentType.CARD
                else -> PaymentType.BALANCE
            },
            transactionType = if (linkUuid != null) TransactionType.ONLINE_PAYMENT
                else if (balanceBefore >= balanceAfter) TransactionType.TRANSFER_SENT
                else TransactionType.TRANSFER_RECEIVED,
            card = card?.asModel(),
            createdAt = createdAt.let { dateFormat.parse(createdAt) },
            updatedAt = updatedAt.let { dateFormat.parse(updatedAt) },
            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            pending = pending,
            linkUuid = linkUuid,
            payer = payer.asModel(),
            receiver = receiver.asModel()
        )
    }

    fun asModelSwitched(): Transaction{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return Transaction(
            transactionId = id,
            amount = CurrencyAmount(amount, Currency.getInstance("ARS")),
            paymentType = when (type) {
                "CARD" -> PaymentType.CARD
                else -> PaymentType.BALANCE
            },
            transactionType = TransactionType.TRANSFER_RECEIVED,
            card = card?.asModel(),
            createdAt = createdAt.let { dateFormat.parse(createdAt) },
            updatedAt = updatedAt.let { dateFormat.parse(updatedAt) },
            balanceBefore = receiverBalanceBefore,
            balanceAfter = receiverBalanceAfter,
            pending = pending,
            linkUuid = linkUuid,
            payer = receiver.asModel(),
            receiver = payer.asModel()
        )
    }
}