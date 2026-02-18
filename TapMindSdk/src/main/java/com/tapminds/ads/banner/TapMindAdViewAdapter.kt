package com.tapminds.ads.banner

import android.content.Context
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.tapmindsads.TapMindAdFormat

interface TapMindAdViewAdapter {
    fun loadAdViewAd(
        params: TapMindAdapterResponseParameters,
        format: TapMindAdFormat,
        activity: Context,
        listener: TapMindAdViewAdapterListener
    )
}