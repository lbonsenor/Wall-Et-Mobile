package com.example.wall_et_mobile.data.mock

import com.example.wall_et_mobile.data.model.User
import java.time.Instant
import java.util.Date

object MockContacts {
    val sampleContacts = listOf(
        User(
            id = 0,
            natId = "43719388",
            name = "Camila",
            lastName = "Lee",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        ),
        User(
            id = 1,
            natId = "43719388",
            name = "Lautaro",
            lastName = "Bonsenor",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        ),
        User(
            id = 2,
            natId = "43719388",
            name = "Matias",
            lastName = "Leporini",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        ),
        User(
            id = 3,
            natId = "43719388",
            name = "Ana Paula",
            lastName = "Negre",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        ),
        User(
            id = 4,
            natId = "43719388",
            name = "Mary",
            lastName = "Jane",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        ),
        User(
            id = 5,
            natId = "43719388",
            name = "Logan",
            lastName = "Paul",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        ),
        User(
            id = 6,
            natId = "43719388",
            name = "Mike",
            lastName = "Tyson",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
            birthDate = Date.from(Instant.now()),
            password = "holis",
        )
    )

    fun getUserByEmail(cvu: String) : User?{
        val users = sampleContacts.filter { user ->
            user.email == cvu
        }

        return if (users.isEmpty()) null else users[0]
    }

    fun getUserByPhoneNo(alias: String) : User?{
        val users = sampleContacts.filter { user ->
            user.phoneNo == alias
        }

        return if (users.isEmpty()) null else users[0]
    }
}