package com.example.wall_et_mobile.data.mock

import com.example.wall_et_mobile.data.model.User
import java.time.Instant
import java.util.Date

object MockContacts {
    val sampleContacts = listOf(
        User(
            id = 0,
            name = "Camila",
            lastName = "Lee",
            //phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
            //password = "holis",
        ),
        User(
            id = 1,
            name = "Lautaro",
            lastName = "Bonsenor",
            //phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
            //password = "holis",
        ),
        User(
            id = 2,
            name = "Matias",
            lastName = "Leporini",
            //phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
            //password = "holis",
        ),
        User(
            id = 3,
            name = "Ana Paula",
            lastName = "Negre",
            //phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
        ),
        User(
            id = 4,
            name = "Mary",
            lastName = "Jane",
            //phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
        ),
        User(
            id = 5,
            name = "Logan",
            lastName = "Paul",
            //phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
        ),
        User(
            id = 6,
            name = "Mike",
            lastName = "Tyson",
            email = "clee@itba.edu.ar",
            birthDate = "2022-12-2",
        )
    )

    fun getUserByEmail(cvu: String) : User?{
        val users = sampleContacts.filter { user ->
            user.email == cvu
        }

        return if (users.isEmpty()) null else users[0]
    }

//    fun getUserByPhoneNo(alias: String) : User?{
//        val users = sampleContacts.filter { user ->
//            user.phoneNo == alias
//        }
//
//        return if (users.isEmpty()) null else users[0]
//    }
}