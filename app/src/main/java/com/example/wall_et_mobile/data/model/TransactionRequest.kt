package com.example.wall_et_mobile.data.model

import android.icu.util.CurrencyAmount
import com.example.wall_et_mobile.data.network.model.NetworkBalancePayment
import com.example.wall_et_mobile.data.network.model.NetworkCardPayment
import com.example.wall_et_mobile.data.network.model.NetworkLinkPayment
import com.example.wall_et_mobile.data.network.model.NetworkTransactionRequest
import kotlinx.serialization.Serializable

//class TransactionRequest(
//    var amount: CurrencyAmount ,
//    var description: String,
//    var type: PaymentType,
//    //var cardId: String?,
//    val receiverEmail: String?,
//) {
//    fun asNetworkModel() : NetworkTransactionRequest{
//        return NetworkTransactionRequest(
//            amount = amount.number.toDouble(),
//            description = description,
//            type = type.toString(),
//            //cardId = cardId,
//            receiverEmail = receiverEmail,
//        )
//    }
//}

@Serializable
enum class PaymentType(val str: String) {
    BALANCE("BALANCE"),
    CARD("CARD"),
    LINK("LINK"),
}
sealed class TransactionRequest {
    abstract val amount: Double
    abstract val description: String
}

data class BalancePayment(
    override val amount: Double,
    override val description: String,
    val receiverEmail: String
) : TransactionRequest()

data class CardPayment(
    override val amount: Double,
    override val description: String,
    val cardId: Long,
    val receiverEmail: String,
) : TransactionRequest()

data class LinkPayment(
    override val amount: Double,
    override val description: String,
) : TransactionRequest()

fun TransactionRequest.asNetworkModel(): NetworkTransactionRequest {
    return when (this) {
        is BalancePayment -> NetworkBalancePayment(
            amount = amount,
            description = description,
            receiverEmail = receiverEmail,
            paymentType = PaymentType.BALANCE
        )
        is CardPayment -> NetworkCardPayment(
            amount = amount,
            description = description,
            cardId = cardId,
            receiverEmail = receiverEmail,
            paymentType = PaymentType.CARD
        )
        is LinkPayment -> NetworkLinkPayment(
            amount = amount,
            description = description,
            paymentType = PaymentType.LINK
        )
    }
}

