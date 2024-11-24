package com.example.wall_et_mobile.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PaymentResponse")
sealed class PaymentResponse {
    @Serializable
    @SerialName("BALANCE")
    data class BalanceResponse(
        val newBalance: Double
    ) : PaymentResponse()

    @Serializable
    @SerialName("LINK")
    data class LinkResponse(
        val linkUuid: String
    ) : PaymentResponse()
}