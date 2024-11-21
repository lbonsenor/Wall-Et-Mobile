package com.example.wall_et_mobile.data.network.model

import com.example.wall_et_mobile.data.model.RecoveryResponse
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRecovery(
    val success: Boolean,
    val message : String
){
    fun asModel() : RecoveryResponse {
        return RecoveryResponse(
            success = success,
            message = message
        )
    }
}