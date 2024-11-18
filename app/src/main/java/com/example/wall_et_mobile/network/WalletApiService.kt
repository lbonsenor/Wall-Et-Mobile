package com.example.wall_et_mobile.network

import com.example.wall_et_mobile.model.RegisteredUser
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://localhost:8080/api/"

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface WalletApiService {
    @POST
    fun postUser(): RegisteredUser

    @GET("user")
    suspend fun getUser(): RegisteredUser
}

object WalletApi {
    val retrofitService: WalletApiService by lazy {
        retrofit.create(WalletApiService::class.java)
    }

}