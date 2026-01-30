package com.tapminds.network

import com.google.gson.annotations.SerializedName

data class ImpressionResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data : ImpressionData? = null
)

data class ImpressionData(
    @SerializedName("appId") val appId: String,
    @SerializedName("count") val count: Int
)

data class ImpressionRequest(
    val appId: String,
    val adapterId : String,
    val placementId : String,
    val adType : String,
    val partner : String,
    val result: String
)

