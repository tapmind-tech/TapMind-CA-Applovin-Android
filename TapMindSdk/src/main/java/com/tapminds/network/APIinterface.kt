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
}