package com.example.wall_et_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
class RechargeResponse {
    val newBalance: Double = 0.0
}

@Serializable
data class RechargeRequest(
    val amount: Double
)