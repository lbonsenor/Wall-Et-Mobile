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
data class NetworkTransaction(
    var id : Int,
    var type: String,
    var amount: Double,
    var balanceBefore: Float,
    var balanceAfter : Float,
    var pending: Boolean,
    var linkUuid: String?,
    var createdAt: String, /* turn to local date afterwards*/
    var updatedAt: String,
    var card : NetworkCard?,
){
    fun asModel(): Transaction{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Transaction(
            transactionId = id,
            amount = CurrencyAmount(amount, Currency.getInstance("ARS")),
            paymentType = when (type) {
                "CARD" -> PaymentType.CARD
                else -> PaymentType.BALANCE
            },
            transactionType = TransactionType.ONLINE_PAYMENT,
            card = card?.asModel(),
            createdAt = createdAt.let { dateFormat.parse(createdAt) },
            updatedAt = updatedAt.let { dateFormat.parse(updatedAt) },
            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            pending = pending,
            linkUuid = linkUuid,
        )
    }
}