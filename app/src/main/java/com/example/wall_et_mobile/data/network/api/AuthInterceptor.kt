package com.example.wall_et_mobile.data.network.api

import android.content.Context;
import android.util.Log

import com.example.wall_et_mobile.SessionManager;
import okhttp3.Interceptor;
import okhttp3.Response;


class AuthInterceptor(context: Context) : Interceptor {

    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        Log.d("AuthInterceptor", "Adding token to request: ${chain.request().url}")
        sessionManager.loadAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        } ?: Log.d("AuthInterceptor", "No token available for request: ${chain.request().url}")

        val request = requestBuilder.build()
        Log.d("AuthInterceptor", "Final headers: ${request.headers}")
        return chain.proceed(request)
        //return chain.proceed(requestBuilder.build())
    }
}