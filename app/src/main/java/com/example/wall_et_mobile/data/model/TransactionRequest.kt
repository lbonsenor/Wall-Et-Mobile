package com.example.wall_et_mobile.data.model

import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.network.model.NetworkTransactionRequest

class TransactionRequest(
    var amount: CurrencyAmount ,
    var description: String,
    var type: PaymentType,
    var cardId: String?,
    val receiverEmail: String?,
) {
    fun asNetworkModel() : NetworkTransactionRequest{
        return NetworkTransactionRequest(
            amount = amount.number.toFloat(),
            description = description,
            type = type.toString(),
            cardId = cardId,
            receiverEmail = receiverEmail,
        )
    }
}