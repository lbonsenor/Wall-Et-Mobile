package com.example.wall_et_mobile.data.network.model

import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionLinkRequest(
    var type: String,
    var cardId: Int? /* if pay with balance then its null */
){
    fun asModel(): TransactionLinkRequest{
        return TransactionLinkRequest(
            type= type,
            cardId = cardId
        )
    }
}

@Serializable
data class NetworkTransactionLinkResponse(
    var success: Boolean
) {
    fun asModel(): Boolean {
        return success
    }
}
