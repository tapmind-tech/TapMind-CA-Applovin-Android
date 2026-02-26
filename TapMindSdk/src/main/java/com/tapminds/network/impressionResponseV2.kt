package com.tapminds.network

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("adType")
	val adType: String? = null,

	@field:SerializedName("placementId")
	val placementId: String? = null,

	@field:SerializedName("appId")
	val appId: String? = null,

	@field:SerializedName("event")
	val event: String? = null
)

data class ImpressionResponseV2(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ImpressionRequestV2(
	val appId: String,
	val adapterId: String,
	val placementId: String,
	val adType: String,
	val event: String?,
	val errorMessage: String?,
	val partner: String,
	val result: String,
	val requestId: String?,
	val versionId: String?,
	val response_id : String?,
	val adapter_name : String,
	val timestamp_utc : String,
)

data class ImpressionRequestV2Amount(
    val appId: String,
    val adapterId: String,
    val placementId: String,
    val adType: String,
    val event: String?,
    val errorMessage: String?,
    val partner: String,
    val result: String,
    val requestId: String?,
    val versionId: String?,
    val response_id : String?,
    val adapter_name : String,
    val timestamp_utc : String,
    val amount : String?,
)
