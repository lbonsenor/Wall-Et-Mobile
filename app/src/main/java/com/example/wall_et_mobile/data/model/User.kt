package com.example.wall_et_mobile.data.model

import com.example.wall_et_mobile.data.network.model.NetworkUser
import java.util.Date

data class User(
    val id: Int?,
    val name: String,
    val lastName: String,
    val natId: String?, //what is this
    val phoneNo: String?,
    val email: String,
    val birthDate: Date?,
    val password: String? /*only when registering , afterwards it will always be null*/
){
    fun asNetworkModel() : NetworkUser{
        return NetworkUser(
            id = id,
            firstName = name,
            lastName = lastName ,
            email = email,
            birthDate = birthDate.toString(),
            password = password
        )
    }
}

