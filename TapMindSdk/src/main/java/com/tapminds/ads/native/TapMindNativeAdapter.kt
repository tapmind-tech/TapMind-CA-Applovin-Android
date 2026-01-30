package com.tapminds.ads.native

import android.app.Activity
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters

interface TapMindNativeAdapter {
    open fun loadNativeAd(
        var1: TapMindAdapterResponseParameters?,
        var2: Activity?,
        var3: TapMindNativeAdAdapterListener?
    )
}