package com.example.wall_et_mobile.data.model

import java.util.Date

data class User(
    val id: Int?,
    val alias: String?,
    val CVU: String?,
    val name: String,
    val lastName: String,
    val natId: String?, //what is this
    val phoneNo: String?,
    val email: String,
    val birthDate: Date?
)
