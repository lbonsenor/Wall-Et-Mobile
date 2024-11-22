package com.example.wall_et_mobile.data.repository
import android.util.Log
import com.example.wall_et_mobile.data.model.RecoveryResponse
import com.example.wall_et_mobile.data.model.RegisterUser
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.data.network.UserRemoteDataSource

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {

    // Mutex to make writes to cached values thread-safe. omg SO hi!
    private val currentUserMutex = Mutex()
    // Cache of the current user got from the network.
    private var currentUser: User? = null

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun register(user: RegisterUser){
        Log.d("UserRepository", "register: $user")
        remoteDataSource.register(user.asNetworkModel())
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }
    suspend fun verify() : User? {
        return remoteDataSource.verify().asModel()
    }

    suspend fun getCurrentUser(refresh: Boolean) : User? {
        if (refresh || currentUser == null) {
            val result = remoteDataSource.getCurrentUser()
            // Thread-safe write to latestNews
            currentUserMutex.withLock {
                this.currentUser = result.asModel()
            }
        }

        return currentUserMutex.withLock { this.currentUser }
    }

    suspend fun resetPassword( newPass: String){
        remoteDataSource.resetPassword(newPass)
    }

    suspend fun recoverPassword(email: String) : RecoveryResponse{
        return remoteDataSource.recoverPassword(email).asModel()
    }
}