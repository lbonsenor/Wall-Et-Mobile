package com.example.wall_et_mobile.data.network.api


import android.content.Context
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {
    /*
    In order to run this on a physical phone, we need the PC's IP Address, and Kotlin does not support
    .env files the same way React/Vue does

    Therefore, CREATE AN EMPTY git-ignored .kt file on data.network.api called IPAddress.kt
    As an example: IPAddress.kt contains: 192.168.0.10
     */

    private const val BASE_URL = "http://${ip}:8080/api/"

    @Volatile
    private var instance: Retrofit? = null

    private fun getInstance(context: Context): Retrofit =
        instance?: synchronized(this)
    {
        instance?: buildRetrofit(context).also { instance = it }
    }

    private fun buildRetrofit(context: Context): Retrofit {
        val httpLoginInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(httpLoginInterceptor).build()


        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient).build()

    }

    fun getUserApiService(context: Context): UserApiService {
        return getInstance(context).create(UserApiService::class.java)
    }

    fun getWalletApiService(context: Context): WalletApiService {
        return getInstance(context).create(WalletApiService::class.java)
    }
    fun getTransactionApiService(context: Context): TransactionApiService{
        return getInstance(context).create(TransactionApiService::class.java)
    }
}
