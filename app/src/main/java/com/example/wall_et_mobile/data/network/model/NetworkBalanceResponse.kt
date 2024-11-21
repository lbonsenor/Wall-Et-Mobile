package com.example.wall_et_mobile.data.network.model

data class NetworkBalanceResponse(
    val balance : Double
){
    fun asDouble(): Double {
        return balance
    }
}