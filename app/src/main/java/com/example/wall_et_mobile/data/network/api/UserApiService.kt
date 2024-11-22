package com.example.wall_et_mobile.data.network.api

import com.example.wall_et_mobile.data.network.model.*

import kotlin.Unit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @POST("/user")
    suspend fun register(@Body user : NetworkRegisterUser) : Response<NetworkUser>

    @POST("/user/login")
    suspend fun login(@Body credentials: NetworkCredentials) : Response<NetworkToken>

    @POST("/user/logout")
    suspend fun logout(): Response<Unit>

    @POST("/user/verify")
    suspend fun verify(@Body token : String?) : Response<NetworkUser>

    @GET("user")
    suspend fun getCurrentUser(): Response<NetworkUser>

    @POST("user/recover-password")
    suspend fun recoverPassword(@Body email : String) : Response<NetworkRecovery>

    @POST("user/reset-password")
    suspend fun resetPassword(@Body resetInfo : NetworkReset) : Response<Unit>
}