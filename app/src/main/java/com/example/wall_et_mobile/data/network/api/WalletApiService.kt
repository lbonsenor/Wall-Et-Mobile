package com.example.wall_et_mobile.data.network.api


import com.example.wall_et_mobile.data.network.model.NetworkAliasUpdate
import com.example.wall_et_mobile.data.network.model.NetworkBalanceResponse
import com.example.wall_et_mobile.data.network.model.NetworkCard
import com.example.wall_et_mobile.data.network.model.NetworkWallet

import kotlin.Unit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface WalletApiService {
    @GET("wallet/cards")
    suspend fun getCards(): Response<List<NetworkCard>>

    @POST("wallet/cards")
    suspend fun addCard(@Body card: NetworkCard): Response<NetworkCard>

    @DELETE("wallet/cards/{cardId}")
    suspend fun deleteCard(@Path("cardId") cardId: Int) : Response<Unit>

    @GET("wallet/balance")
    suspend fun getBalance() : Response<NetworkBalanceResponse>

    @PUT("wallet/update-alias")
    suspend fun updateAlias(@Body alias : NetworkAliasUpdate) : Response<NetworkWallet>

    @GET("wallet/details")
    suspend fun getWallet() : Response<NetworkWallet>
}