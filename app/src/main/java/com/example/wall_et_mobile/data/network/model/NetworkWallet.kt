package com.example.wall_et_mobile.data.network.model

import com.example.wall_et_mobile.data.model.Wallet
import com.example.wall_et_mobile.data.network.api.WalletApiService
import kotlinx.serialization.Serializable


@Serializable
data class NetworkWallet(
    val id : Int,
    val balance : Double = 0.0,
    val cvu : String,
    val alias : String,
    val invested : Int,
    val createdAt : String,
    val updatedAt : String
) {
    fun asModel() : Wallet{
        return Wallet(
            walletId = id,
            invested = invested,
            balance = balance,
            cvu = cvu,
            alias = alias
        )
    }
}