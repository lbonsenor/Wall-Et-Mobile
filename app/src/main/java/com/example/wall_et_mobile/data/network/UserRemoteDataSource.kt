package com.example.wall_et_mobile.data.network

import com.example.wall_et_mobile.SessionManager
import com.example.wall_et_mobile.data.network.api.UserApiService
import com.example.wall_et_mobile.data.network.model.NetworkCredentials
import com.example.wall_et_mobile.data.network.model.NetworkUser

class UserRemoteDataSource(private val sessionManager: SessionManager,
                           private val userApiService: UserApiService)
    : RemoteDataSource() {

    suspend fun login(username: String, password: String){
            val response = handleApiResponse {
                userApiService.login(NetworkCredentials(username,password))
            }
            sessionManager.saveAuthToken(response.token)
        }

    suspend fun logout(){
        handleApiResponse { userApiService.logout()}
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser() : NetworkUser {
        handleApiResponse {userApiService.getCurrentUser()}
    }
}