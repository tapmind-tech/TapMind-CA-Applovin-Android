package com.tapminds.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APInterface {

//    @GET("dashboard/impressions")
//    fun impressionRequest(
//        @Body impressionRequest: ImpressionRequest
//    ): Call<ImpressionResponse>

    @POST("impression")
    fun impressionRequest(
        @Body impressionRequest: ImpressionRequest
    ): Call<ImpressionResponse>

    @POST("impression-v2")
    fun impressionRequestV2(
        @Body impressionRequest: ImpressionRequestV2
    ): Call<ImpressionResponseV2>


    @POST("impression-v2")
    fun impressionRequestV2Amount(
        @Body impressionRequest: ImpressionRequestV2Amount
    ): Call<ImpressionResponseV2>
}