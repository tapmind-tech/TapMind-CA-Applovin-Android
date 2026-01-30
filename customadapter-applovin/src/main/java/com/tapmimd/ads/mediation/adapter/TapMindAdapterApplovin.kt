package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.util.Log
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.adapter.MaxAdViewAdapter
import com.applovin.mediation.adapter.MaxAdapter
import com.applovin.mediation.adapter.MaxAdapter.InitializationStatus
import com.applovin.mediation.adapter.MaxAppOpenAdapter
import com.applovin.mediation.adapter.MaxInterstitialAdapter
import com.applovin.mediation.adapter.MaxNativeAdAdapter
import com.applovin.mediation.adapter.MaxRewardedAdapter
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
import com.applovin.mediation.adapter.listeners.MaxAppOpenAdapterListener
import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
import com.applovin.mediation.adapter.listeners.MaxRewardedAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.mediation.adapters.MediationAdapterBase
import com.applovin.sdk.AppLovinSdk

class TapMindAdapterApplovin(appLovinSdk: AppLovinSdk) : MediationAdapterBase(appLovinSdk),
    MaxAdViewAdapter, MaxNativeAdAdapter, MaxAppOpenAdapter, MaxInterstitialAdapter,MaxRewardedAdapter {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindAdapterApplovin"

    override fun initialize(
        maxAdapterInitializationParameters: MaxAdapterInitializationParameters,
        activity: Activity,
        callback: MaxAdapter.OnCompletionListener?
    ) {
        callback?.onCompletion(InitializationStatus.INITIALIZING,"success")
    }

    override fun getSdkVersion(): String? {
        return "1.0.0"
    }

    override fun getAdapterVersion(): String? {
        return "1.0.0.0"
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
        val data = AppLovinBannerCustomEventLoader.getInstance(maxAdapterResponseParameters,maxAdFormat,activity,maxAdViewAdapterListener)
        data.loadAdd()
    }

    override fun loadNativeAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxNativeAdapterListener: MaxNativeAdAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 loadNativeAdMapper")
        AppLovinNativeCustomEventLoader.getInstance(maxAdapterResponseParameters,activity,maxNativeAdapterListener).loadAd()
    }

    override fun loadInterstitialAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxInterstitialAdapterListener: MaxInterstitialAdapterListener
    ) {
        Log.d(TAG, "$TAG1 loadInterstitialAd")
        AppLovinInterstitialCustomEventLoader.getInstance(maxAdapterResponseParameters,activity,maxInterstitialAdapterListener).loadAd()
    }

    override fun showInterstitialAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxInterstitialAdapterListener: MaxInterstitialAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 showInterstitialAd")
        AppLovinInterstitialCustomEventLoader.getInstance(maxAdapterResponseParameters,activity,maxInterstitialAdapterListener!!).showAd(activity!!)
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
        val data = AppLovinRewardedCustomEventLoader.getInstance(maxAdapterResponseParameters,activity,maxRewardedAdapterListener!!)
        data.loadAd()
    }


    override fun showRewardedAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxRewardedAdapterListener: MaxRewardedAdapterListener?
    ) {
        Log.d(TAG, "$TAG1 showRewardedAd")
        val data = AppLovinRewardedCustomEventLoader.getInstance(maxAdapterResponseParameters,activity,maxRewardedAdapterListener!!)
        data.showAd(activity!!)
    }

}