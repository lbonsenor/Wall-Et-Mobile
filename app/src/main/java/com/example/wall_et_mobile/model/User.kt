package com.example.wall_et_mobile.model

data class User(
    val id: Int,
    val alias: String,
    val CVU: String,
    val name: String,
    val lastName: String,
    val natId: String,
    val phoneNo: String,
    val email: String,
) {
}

data class RegisteredUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String
)