package com.example.wall_et_mobile.data.model

import com.example.wall_et_mobile.data.network.model.NetworkTransactionLinkRequest

class TransactionLinkRequest(
    val type : String,
    val cardId: String? = null
) {
    fun asNetworkModel() : NetworkTransactionLinkRequest {
        return NetworkTransactionLinkRequest(
            type = type,
            cardId = cardId
        )
    }
}