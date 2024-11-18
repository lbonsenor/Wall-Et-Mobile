package com.example.wall_et_mobile.data.mock

import com.example.wall_et_mobile.model.User

object MockContacts {
    val sampleContacts = listOf(
        User(
            id = 0,
            natId = "43719388",
            alias = "clee",
            CVU = "5438723091",
            name = "Camila",
            lastName = "Lee",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        ),
        User(
            id = 1,
            natId = "43719388",
            alias = "lbonsenor",
            CVU = "5438723090",
            name = "Lautaro",
            lastName = "Bonsenor",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        ),
        User(
            id = 2,
            natId = "43719388",
            alias = "mleporini",
            CVU = "5438723099",
            name = "Matias",
            lastName = "Leporini",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        ),
        User(
            id = 3,
            natId = "43719388",
            alias = "anegre",
            CVU = "5438723098",
            name = "Ana Paula",
            lastName = "Negre",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        ),
        User(
            id = 4,
            natId = "43719388",
            alias = "mjane",
            CVU = "5438723097",
            name = "Mary",
            lastName = "Jane",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        ),
        User(
            id = 5,
            natId = "43719388",
            alias = "lpaul",
            CVU = "5438723096",
            name = "Logan",
            lastName = "Paul",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        ),
        User(
            id = 6,
            natId = "43719388",
            alias = "mtyson",
            CVU = "5438723095",
            name = "Mike",
            lastName = "Tyson",
            phoneNo = "1121860958",
            email = "clee@itba.edu.ar",
        )
    )

    fun getUserByCVU(cvu: String) : User?{
        val users = sampleContacts.filter { user ->
            user.CVU == cvu
        }

        return if (users.isEmpty()) null else users[0]
    }

    fun getUserByAlias(alias: String) : User?{
        val users = sampleContacts.filter { user ->
            user.alias == alias
        }

        return if (users.isEmpty()) null else users[0]
    }
}