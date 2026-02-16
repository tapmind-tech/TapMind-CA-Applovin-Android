package com.tapminds.ads.interstitial

import android.os.Bundle
import com.tapminds.adapter.listener.TapMindAdapterError

interface TapMindInterstitialAdapterListener {

    fun onInterstitialAdLoaded()

    fun onInterstitialAdLoaded(bundle: Bundle?)

    fun onInterstitialAdLoadFailed(tapMindAdapterError: TapMindAdapterError?)

    fun onInterstitialAdDisplayed()

    fun onInterstitialAdDisplayed(bundle: Bundle?)

    fun onInterstitialAdClicked()

    fun onInterstitialAdClicked(bundle: Bundle?)

    fun onInterstitialAdHidden()

    fun onInterstitialAdHidden(bundle: Bundle?)

    fun onInterstitialAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?)

    fun onInterstitialAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?, bundle: Bundle?)
}