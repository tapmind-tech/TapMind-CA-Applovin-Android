package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener

class AppLovinInterstitialCustomEventLoader private constructor(
    val maxAdapterResponseParameters: MaxAdapterResponseParameters?,
    val activity: Activity?,
    val maxAdViewAdapterListener: MaxInterstitialAdapterListener
){

    private val TAG = "APP@@@"
    private val TAG1 = "AppLovinInterstitialCustomEventLoader"


    private var  request : TapMindAdapterResponseParameters?= null
    private var tapMindInterstitialAdapterListener : TapMindInterstitialAdapterListener ?= null

    companion object{

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppLovinInterstitialCustomEventLoader? = null

        fun getInstance(maxAdapterResponseParameters: MaxAdapterResponseParameters?,
                        activity: Activity?,
                        callback: MaxInterstitialAdapterListener): AppLovinInterstitialCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppLovinInterstitialCustomEventLoader(maxAdapterResponseParameters,activity,callback).also {
                    INSTANCE = it
                }
            }
        }

    }

    fun loadAd(){
        request = object : TapMindAdapterResponseParameters {

            override fun getThirdPartyAdPlacementId(): String {
                return ""
            }

            override fun getBidResponse(): String {
                return ""
            }

            override fun getAdUnitId(): String {
                return ""
            }

            override fun getLocalExtraParameters(): Map<String, Any> {
                return emptyMap()
            }

            override fun getServerParameters(): Bundle {
                return Bundle()
            }

            override fun getCustomParameters(): Bundle {
                // If you need custom params, pass using adConfig.mediationExtrasBundle (optional)
                return Bundle()
            }

            override fun hasUserConsent(): Boolean? {
                return true
            }

            override fun isAgeRestrictedUser(): Boolean? {
                return false
            }

            override fun isDoNotSell(): Boolean? {
                return false
            }

            override fun getConsentString(): String? {
                return ""
            }

            override fun isTesting(): Boolean {
                return false
            }
        }

        tapMindInterstitialAdapterListener = object : TapMindInterstitialAdapterListener {
            override fun onInterstitialAdLoaded() {
                Log.d(TAG,"$TAG1 : onAdLoaded")
                maxAdViewAdapterListener.onInterstitialAdLoaded()
            }

            override fun onInterstitialAdLoaded(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onAdLoaded Bundle")
                maxAdViewAdapterListener.onInterstitialAdLoaded(bundle)
            }

            override fun onInterstitialAdLoadFailed(error: TapMindAdapterError?) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                maxAdViewAdapterListener.onInterstitialAdLoadFailed(MaxAdapterError(error!!.getErrorCode(), error!!.getMessage()))
            }

            override fun onInterstitialAdDisplayed() {
                Log.d(TAG,"$TAG1 : onInterstitialAdDisplayed")
                maxAdViewAdapterListener.onInterstitialAdDisplayed()
            }

            override fun onInterstitialAdDisplayed(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdDisplayed Bundle")
                maxAdViewAdapterListener.onInterstitialAdDisplayed(bundle)
            }

            override fun onInterstitialAdClicked() {
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked")
                maxAdViewAdapterListener.onInterstitialAdClicked()
            }

            override fun onInterstitialAdClicked(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked Bundle")
                maxAdViewAdapterListener.onInterstitialAdClicked(bundle)
            }

            override fun onInterstitialAdHidden() {
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden")
                maxAdViewAdapterListener.onInterstitialAdHidden()
            }

            override fun onInterstitialAdHidden(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden Bundle")
                maxAdViewAdapterListener.onInterstitialAdHidden(bundle)
            }

            override fun onInterstitialAdDisplayFailed(error: TapMindAdapterError?) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                maxAdViewAdapterListener.onInterstitialAdDisplayFailed(MaxAdapterError(error!!.getErrorCode(), error.getMessage()))
            }

            override fun onInterstitialAdDisplayFailed(error: TapMindAdapterError?, bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                maxAdViewAdapterListener.onInterstitialAdDisplayFailed(MaxAdapterError(error!!.getErrorCode(), error.getMessage()),bundle)
            }
        }


        TapMindsMediationAdapter.getInstance(activity!!)
            .loadInterstitialAd(request!!,activity!!, tapMindInterstitialAdapterListener!!)
    }

    fun showAd(context: Context) {
        Log.d(TAG, "$TAG1 : showAd")
        TapMindsMediationAdapter.getInstance(context)
            .showInterstitialAd(request!!,context as Activity,tapMindInterstitialAdapterListener!!)
    }
}