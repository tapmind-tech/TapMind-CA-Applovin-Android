package com.tapminds.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
                const val BASE_URL = "https://srv-core-dev.tapmind.io/"
//        const val BASE_URL = "https://srv-core-engine.tapmind.io"

        val retrofit: Retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}