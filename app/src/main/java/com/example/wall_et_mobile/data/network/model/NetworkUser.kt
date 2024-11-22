package com.example.wall_et_mobile.data.network.model

import com.example.wall_et_mobile.data.model.RegisterUser
import com.example.wall_et_mobile.data.model.User
import kotlinx.serialization.Serializable

@Serializable
class NetworkUser(
    var id: Int?,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: String,
) {
    fun asModel(): User {
        //val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return User(
            id = id,
            name = firstName,
            lastName = lastName,
            email = email,
            //birthDate = dateFormat.parse(birthDate)!!,
            birthDate = birthDate
        )
    }
}

@Serializable
class NetworkRegisterUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String,
    val password: String
) {
    fun asModel(): RegisterUser {
        return RegisterUser(
            firstName = firstName,
            lastName = lastName,
            email = email,
            birthDate = birthDate,
            password = password
        )
    }
}