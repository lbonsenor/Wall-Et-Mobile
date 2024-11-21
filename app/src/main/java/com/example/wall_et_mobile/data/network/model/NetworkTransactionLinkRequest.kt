package com.example.wall_et_mobile.data.network.model

import com.example.wall_et_mobile.data.model.TransactionLinkRequest
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionLinkRequest(
    var type: String,
    var cardId: String? /* if pay with balance then its null */
){
    fun asModel(): TransactionLinkRequest{
        return TransactionLinkRequest(
            type= type,
            cardId = cardId
        )
    }
}