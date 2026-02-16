package com.tapminds.ads.native

import android.app.Activity
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters

interface TapMindNativeAdapter {
    fun loadNativeAd(
        var1: TapMindAdapterResponseParameters?,
        var2: Activity?,
        index: Int,
        var3: TapMindNativeAdAdapterListener?
    )
}