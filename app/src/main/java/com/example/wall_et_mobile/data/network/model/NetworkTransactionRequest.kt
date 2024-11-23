package com.example.wall_et_mobile.data.network.model

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.TransactionRequest
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionRequest (
    var amount: Double ,
    var description: String,
    var type: String,
   // var cardId: String?,
    val receiverEmail: String?,
){
    fun asModel() : TransactionRequest {
        return TransactionRequest(
            amount = CurrencyAmount(amount, Currency.getInstance("ARS")),
            description = description,
            type = when (type) {
                "CARD" -> PaymentType.CARD
                else -> PaymentType.BALANCE
            },
            //cardId = cardId,
            receiverEmail = receiverEmail
        )
    }
}