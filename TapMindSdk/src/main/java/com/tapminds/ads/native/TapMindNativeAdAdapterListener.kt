package com.tapminds.ads.native

import android.os.Bundle
import com.tapminds.adapter.listener.TapMindAdapterError

interface TapMindNativeAdAdapterListener {

    open fun onNativeAdLoaded(var1: TapMindNativeAd?, var2: Bundle?)

    open fun onNativeAdLoadFailed(tapMindAdapterError: TapMindAdapterError)

    open fun onNativeAdDisplayed(bundle: Bundle?)

    open fun onNativeAdClicked()

    open fun onNativeAdClicked(var1: Bundle?)

}