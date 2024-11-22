package com.example.wall_et_mobile.data.network.api

import com.example.wall_et_mobile.data.network.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.Unit

interface TransactionApiService {
    @POST("payment")
    suspend fun makePayment(@Body paymentInfo: NetworkTransactionRequest) : Response<Unit>

    @GET("payment") /* use this for the filter */
    suspend fun getPayments(@Query("page") page : Int,
                            @Query("direction") direction: String,
                            @Query("pending") pending : String?,
                            @Query("type") type : String?,
                            @Query("range") range : String?,
                            @Query("source") source : String?,
                            @Query("cardId") cardId: Int?): Response<NetworkTransactionList>

    @GET("payment/{paymentId}")
    suspend fun getPayment(@Path("paymentId") paymentId: Int) : Response<NetworkTransaction>

    @GET("payment/link/{linkUuid}") /* get the transaction that will execute*/
    suspend fun getPaymentLinkInfo(@Path("linkUuid") linkUuid : String): Response<NetworkTransaction>

    @POST("payment/link/{linkUuid}") /* make the link payment itself*/
    suspend fun settlePaymentLink(@Path("linkUuid") linkUuid: String, @Body paymentInfo : NetworkTransactionLinkRequest) : Response<Unit>

}