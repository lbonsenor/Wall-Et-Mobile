package com.example.wall_et_mobile.data.network.api

import android.content.Context;

import com.example.wall_et_mobile.SessionManager;
import okhttp3.Interceptor;
import okhttp3.Response;


public class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context);

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder();

        sessionManager.loadAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}