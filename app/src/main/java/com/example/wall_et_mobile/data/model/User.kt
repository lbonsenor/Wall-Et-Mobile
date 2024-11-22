package com.example.wall_et_mobile.data.model

import com.example.wall_et_mobile.data.network.model.NetworkUser
import java.time.Instant
import java.util.Date

data class User(
    val id: Int?,
//    val alias: String?,
//    val cvu: String?,
    val name: String,
    val lastName: String,
    //what is this
    val phoneNo: String? = "+54 911 4447-3947",
    val email: String = "clee@itba.edu.ar",
    val birthDate: Date? = Date.from(Instant.now()),
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

