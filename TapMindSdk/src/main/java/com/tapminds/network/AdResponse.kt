package com.tapminds.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class AdRequest(
    @SerializedName("appName") val appName: String? = null,
    @SerializedName("placementName") val placementName: String? = null,
    @SerializedName("version") val version: String? = null,
    @SerializedName("platform") val platform: String? = null,
    @SerializedName("environment") val environment: String? = null,
    @SerializedName("adType") val adType: String? = null,
    @SerializedName("country") val country: String? = null
)

@Keep
data class AdResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
//    @SerializedName("ads") val ads: List<Ad> = emptyList(),
    @SerializedName("error") val error: Error? = null,
    @SerializedName("data") val data: AdData? = null
)

@Keep
data class Ad(
    @SerializedName("title") val title: String? = null,
    @SerializedName("body") val body: String? = null,
)

@Keep
data class Error(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: Int
)

@Keep
data class AdData(
    @SerializedName("adapters")
    val adapters: List<DataItem>? = emptyList(),

    @SerializedName("appId")
    val appId: String? = null,

    @SerializedName("placementId")
    val placementId: String? = null
)

data class DataItem(

    @field:SerializedName("ad_unit_id")
    val adUnitId: String? = null,

    @field:SerializedName("partner")
    val partner: String? = null,

    @field:SerializedName("priority")
    val priority: Int? = null,

    @field:SerializedName("adapterId")
    val adapterId: String? = null
)

data class AdError(val errorCode: Int, val errorMessage: String? = null)

data class AdRequestPayload(
    val appName: String? = null,
    val placementId: String? = null,
    val appVersion: String? = null,
    val adType: String? = null,
    val country: String? = null
)

object AdRequestPayloadHolder {
    var playLoad: AdRequestPayload? = null
}