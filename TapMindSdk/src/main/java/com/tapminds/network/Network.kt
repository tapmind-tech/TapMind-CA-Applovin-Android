package com.tapminds.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TapMindApiService {

    @Headers("Content-Type: application/json")
    @JvmSuppressWildcards
    @POST("bid-request-v2")
    suspend fun bidRequest(@Body params: AdRequest): Response<AdResponse>
}

interface TapMindRestAdapter {
    suspend fun bidRequest(params: AdRequest): Response<AdResponse>
}

object TapMindRestAdapterImpl : TapMindRestAdapter {
    private var BASE_URL = "https://srv-core-engine.tapmind.io"
//    private var BASE_URL = "https://srv-core-dev.tapmind.io"
    private val restApi: TapMindApiService by lazy {
        val okHttpClient = OkHttpClient.Builder()
//        if(BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Set the log level
//            okHttpClient.addInterceptor(loggingInterceptor)
//        }
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TapMindApiService::class.java)
    }

    override suspend fun bidRequest(params: AdRequest): Response<AdResponse> =
        restApi.bidRequest(params)
}