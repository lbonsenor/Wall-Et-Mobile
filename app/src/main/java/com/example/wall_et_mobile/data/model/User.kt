package com.example.wall_et_mobile.data.model

import com.example.wall_et_mobile.data.network.model.NetworkRegisterUser
import com.example.wall_et_mobile.data.network.model.NetworkUser

data class User(
    val id: Int?,
    val name: String,
    val lastName: String,
    val email: String = "clee@itba.edu.ar",
    val birthDate: String,
){
    fun asNetworkModel() : NetworkUser{
        return NetworkUser(
            id = id,
            firstName = name,
            lastName = lastName ,
            email = email,
            birthDate = birthDate,
        )
    }
}


data class RegisterUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String,
    val password: String
) {
    fun asNetworkModel(): NetworkRegisterUser {
        return NetworkRegisterUser(
            firstName = firstName,
            lastName = lastName,
            email = email,
            birthDate = birthDate,
            password = password
        )
    }
}