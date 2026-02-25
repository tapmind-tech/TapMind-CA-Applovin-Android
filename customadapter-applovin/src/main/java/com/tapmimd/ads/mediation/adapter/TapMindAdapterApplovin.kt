package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.adapter.MaxAdViewAdapter
import com.applovin.mediation.adapter.MaxAdapter
import com.applovin.mediation.adapter.MaxAdapter.InitializationStatus
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.MaxInterstitialAdapter
import com.applovin.mediation.adapter.MaxRewardedAdapter
import com.applovin.mediation.adapter.MaxSignalProvider
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
import com.applovin.mediation.adapter.listeners.MaxAppOpenAdapterListener
import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
import com.applovin.mediation.adapter.listeners.MaxRewardedAdapterListener
import com.applovin.mediation.adapter.listeners.MaxSignalCollectionListener
import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.mediation.adapter.parameters.MaxAdapterSignalCollectionParameters
import com.applovin.mediation.adapters.MediationAdapterBase
import com.applovin.sdk.AppLovinSdk

class TapMindAdapterApplovin(appLovinSdk: AppLovinSdk) : MediationAdapterBase(appLovinSdk),
    MaxInterstitialAdapter, MaxRewardedAdapter, MaxAdViewAdapter, MaxSignalProvider {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindAdapterApplovin"

    override fun initialize(
        parameters: MaxAdapterInitializationParameters,
        activity: Activity,
        callback: MaxAdapter.OnCompletionListener?
    ) {
        Log.d(TAG, "initialize: AppLovin 321")
        callback?.onCompletion(InitializationStatus.INITIALIZED_SUCCESS, null)
    }

    override fun getSdkVersion(): String {
        return "1.0.0"
    }

    override fun getAdapterVersion(): String {
        return "1.0.0"
    }

    override fun onDestroy() {
    }

    override fun loadAdViewAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters,
        maxAdFormat: MaxAdFormat,
        activity: Activity,
        maxAdViewAdapterListener: MaxAdViewAdapterListener
    ) {
        Log.d(TAG, "$TAG1 loadAdViewAd")
        val data = AppLovinBannerCustomEventLoader.getInstance(
            maxAdapterResponseParameters,
            maxAdFormat,
            activity,
            object : MaxAdViewAdapterListener {
                override fun onAdViewAdLoaded(p0: View?) {
                    maxAdViewAdapterListener.onAdViewAdLoaded(p0)
                }

                override fun onAdViewAdLoaded(p0: View?, p1: Bundle?) {
                    maxAdViewAdapterListener.onAdViewAdLoaded(p0, p1)
                }

                override fun onAdViewAdLoadFailed(p0: MaxAdapterError?) {
                    maxAdViewAdapterListener.onAdViewAdLoadFailed(p0)
                }

                override fun onAdViewAdDisplayed() {
                    maxAdViewAdapterListener.onAdViewAdDisplayed()
                }

                override fun onAdViewAdDisplayed(p0: Bundle?) {
                    maxAdViewAdapterListener.onAdViewAdDisplayed(p0)
                }

                override fun onAdViewAdDisplayFailed(p0: MaxAdapterError?) {
                    maxAdViewAdapterListener.onAdViewAdDisplayFailed(p0)
                }

                override fun onAdViewAdDisplayFailed(
                    p0: MaxAdapterError?,
                    p1: Bundle?
                ) {
                    maxAdViewAdapterListener.onAdViewAdDisplayFailed(p0, p1)
                }

                override fun onAdViewAdClicked() {
                    maxAdViewAdapterListener.onAdViewAdClicked()
                }

                override fun onAdViewAdClicked(p0: Bundle?) {
                    maxAdViewAdapterListener.onAdViewAdClicked(p0)
                }

                override fun onAdViewAdHidden() {
                    maxAdViewAdapterListener.onAdViewAdHidden()
                }

                override fun onAdViewAdHidden(p0: Bundle?) {
                    maxAdViewAdapterListener.onAdViewAdHidden(p0)
                }

                override fun onAdViewAdExpanded() {
                    maxAdViewAdapterListener.onAdViewAdExpanded()
                }

                override fun onAdViewAdExpanded(p0: Bundle?) {
                    maxAdViewAdapterListener.onAdViewAdExpanded(p0)
                }

                override fun onAdViewAdCollapsed() {
                    maxAdViewAdapterListener.onAdViewAdCollapsed()
                }

                override fun onAdViewAdCollapsed(p0: Bundle?) {
                    maxAdViewAdapterListener.onAdViewAdCollapsed(p0)
                }
            }
        )
        data.loadAdd()
    }

    override fun loadNativeAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxNativeAdapterListener: MaxNativeAdAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 loadNativeAdMapper")
        AppLovinNativeCustomEventLoader.getInstance(
            maxAdapterResponseParameters,
            activity,
            maxNativeAdapterListener
        ).loadAd()
    }

    override fun loadInterstitialAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxInterstitialAdapterListener: MaxInterstitialAdapterListener
    ) {
        Log.d(TAG, "$TAG1 loadInterstitialAd")
        AppLovinInterstitialCustomEventLoader.getInstance(
            maxAdapterResponseParameters,
            activity,
            maxInterstitialAdapterListener
        ).loadAd()
    }

    override fun showInterstitialAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxInterstitialAdapterListener: MaxInterstitialAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 showInterstitialAd")
        AppLovinInterstitialCustomEventLoader.getInstance(
            maxAdapterResponseParameters,
            activity,
            maxInterstitialAdapterListener!!
        ).showAd(activity!!)
    }

    override fun loadAppOpenAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters,
        activity: Activity?,
        maxAppOpenAdapterListener: MaxAppOpenAdapterListener
    ) {

    }

    override fun showAppOpenAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters,
        activity: Activity?,
        maxAppOpenAdapterListener: MaxAppOpenAdapterListener
    ) {

    }

    override fun loadRewardedAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxRewardedAdapterListener: MaxRewardedAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 loadRewardedAd")
        val data = AppLovinRewardedCustomEventLoader.getInstance(
            maxAdapterResponseParameters,
            activity,
            maxRewardedAdapterListener!!
        )
        data.loadAd()
    }

    override fun showRewardedAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxRewardedAdapterListener: MaxRewardedAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 showRewardedAd")
        val data = AppLovinRewardedCustomEventLoader.getInstance(
            maxAdapterResponseParameters,
            activity,
            maxRewardedAdapterListener!!
        )
        data.showAd(activity!!)
    }

    override fun collectSignal(
        parameters: MaxAdapterSignalCollectionParameters?,
        activity: Activity?,
        callback: MaxSignalCollectionListener?
    ) {
        Log.d(TAG, "collectSignal: AppLovin 321")
    }
}