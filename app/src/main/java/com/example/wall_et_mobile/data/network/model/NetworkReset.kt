package com.example.wall_et_mobile.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkReset(
    val token : String?,
    val password : String
)