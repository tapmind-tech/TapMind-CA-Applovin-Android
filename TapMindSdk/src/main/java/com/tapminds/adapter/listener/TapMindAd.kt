package com.tapminds.adapter.listener

import androidx.annotation.Nullable
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.tapmindsads.TapMindAdFormat
import com.tapminds.tapmindsads.TapmindsSdkUtils

interface TapMindAd{


    fun getFormat(): TapMindAdFormat

    fun getSize(): TapmindsSdkUtils.Size

    fun getAdUnitId(): String

    fun getNetworkName(): String

    fun getNetworkPlacement(): String

    fun getPlacement(): String

    fun getWaterfall(): MaxAdWaterfallInfo

    fun getRequestLatencyMillis(): Long

    @Nullable
    fun getCreativeId(): String?

    fun getRevenue(): Double

    fun getRevenuePrecision(): String

    @Nullable
    fun getDspName(): String?

    @Nullable
    fun getDspId(): String?

    fun getAdValue(key: String): String

    fun getAdValue(key: String, defaultValue: String): String

    @Nullable
    fun getNativeAd(): TapMindNativeAd?

    /** @deprecated */
    @Deprecated("Use updated API if available")
    @Nullable
    fun getAdReviewCreativeId(): String?
}