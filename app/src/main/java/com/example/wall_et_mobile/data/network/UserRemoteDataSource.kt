package com.example.wall_et_mobile.data.network

import com.example.wall_et_mobile.SessionManager
import com.example.wall_et_mobile.data.network.api.UserApiService
import com.example.wall_et_mobile.data.network.model.NetworkCredentials
import com.example.wall_et_mobile.data.network.model.NetworkRecovery
import com.example.wall_et_mobile.data.network.model.NetworkRegisterUser
import com.example.wall_et_mobile.data.network.model.NetworkReset
import com.example.wall_et_mobile.data.network.model.NetworkUser

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val userApiService: UserApiService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String){
            val response = handleApiResponse {
                userApiService.login(NetworkCredentials(username,password))
            }
            sessionManager.saveAuthToken(response.token)
        }

    suspend fun register(user : NetworkRegisterUser) : NetworkUser {
        return handleApiResponse { userApiService.register(user) }
    }

    suspend fun verify(code: String) : NetworkUser {
        return handleApiResponse { userApiService.verify(code) }
    }
    suspend fun logout(){
        handleApiResponse { userApiService.logout()}
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser() : NetworkUser {
        return handleApiResponse {userApiService.getCurrentUser()}
    }

    suspend fun recoverPassword(email : String) : NetworkRecovery {
        return handleApiResponse { userApiService.recoverPassword(email) }
    }

    suspend fun resetPassword(newPass : String ) {
        handleApiResponse { userApiService.resetPassword(NetworkReset(password = newPass, token = sessionManager.loadAuthToken())) }
    }
}