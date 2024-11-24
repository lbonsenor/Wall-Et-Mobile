package com.example.wall_et_mobile.data.network.model

import com.example.wall_et_mobile.data.model.BalancePayment
import com.example.wall_et_mobile.data.model.CardPayment
import com.example.wall_et_mobile.data.model.LinkPayment
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.data.model.TransactionRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("NetworkTransactionRequest")
sealed class NetworkTransactionRequest {
    abstract val amount: Double
    abstract val description: String
    abstract val paymentType: PaymentType
}

@Serializable
@SerialName("BALANCE")
data class NetworkBalancePayment(
    override val amount: Double,
    override val description: String,
    val receiverEmail: String,
    override val paymentType: PaymentType = PaymentType.BALANCE
) : NetworkTransactionRequest()

@Serializable
@SerialName("CARD")
data class NetworkCardPayment(
    override val amount: Double,
    override val description: String,
    val cardId: Int,
    val receiverEmail: String,
    override val paymentType: PaymentType = PaymentType.CARD
) : NetworkTransactionRequest()

@Serializable
@SerialName("LINK")
data class NetworkLinkPayment(
    override val amount: Double,
    override val description: String,
    override val paymentType: PaymentType = PaymentType.LINK
) : NetworkTransactionRequest()

@Serializable
enum class PaymentType {
    @SerialName("BALANCE") BALANCE,
    @SerialName("CARD") CARD,
    @SerialName("LINK") LINK
}

fun NetworkTransactionRequest.asModel(): TransactionRequest {
    return when (this) {
        is NetworkBalancePayment -> BalancePayment(
            amount = amount,
            description = description,
            receiverEmail = receiverEmail
        )
        is NetworkCardPayment -> CardPayment(
            amount = amount,
            description = description,
            cardId = cardId,
            receiverEmail = receiverEmail
        )
        is NetworkLinkPayment -> LinkPayment(
            amount = amount,
            description = description
        )
    }
}
//
//@Serializable
//data class NetworkTransactionRequest (
//    var amount: Double ,
//    var description: String,
//    var type: String,
//   // var cardId: String?,
//    val receiverEmail: String?,
//){
//    fun asModel() : TransactionRequest {
//        return TransactionRequest(
//            amount = CurrencyAmount(amount, Currency.getInstance("ARS")),
//            description = description,
//            type = when (type) {
//                "CARD" -> PaymentType.CARD
//                else -> PaymentType.BALANCE
//            },
//            //cardId = cardId,
//            receiverEmail = receiverEmail
//        )
//    }
//}