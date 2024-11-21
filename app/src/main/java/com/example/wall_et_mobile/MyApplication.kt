package com.example.wall_et_mobile

import android.app.Application
import com.example.wall_et_mobile.data.network.UserRemoteDataSource
import com.example.wall_et_mobile.data.network.WalletRemoteDataSource
import com.example.wall_et_mobile.data.network.api.RetrofitClient
import com.example.wall_et_mobile.data.repository.UserRepository
import com.example.wall_et_mobile.data.repository.WalletRepository

class MyApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getUserApiService(this))

    private val walletRemoteDataSource: WalletRemoteDataSource
        get() = WalletRemoteDataSource(RetrofitClient.getWalletApiService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val walletRepository: WalletRepository
        get() = WalletRepository(walletRemoteDataSource)
}