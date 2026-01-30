package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.ironsource.mediationsdk.adunit.adapter.BaseInterstitial
import com.ironsource.mediationsdk.adunit.adapter.listener.InterstitialAdListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrorType
import com.ironsource.mediationsdk.model.NetworkSettings
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class TapMindInterstitialAdapter(networkSettings: NetworkSettings) : BaseInterstitial<TapMindAdapterIronSource>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobInterstitialCustomEventLoader"

    private var  request : TapMindAdapterResponseParameters?= null
    private var tapMindInterstitialAdapterListener : TapMindInterstitialAdapterListener ?= null

    override fun loadAd(
        adData: AdData,
        context: Context,
        interstitialAdListener: InterstitialAdListener
    ) {

        val adUnitData = adData.adUnitData

        for ((key, value) in adUnitData) {
            Log.d("AdUnitData", "$key : $value")
        }

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = "",
            appVersion = getAppVersion(context),
            adType = "Interstitial",
            country = Locale.getDefault().country
        )

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
                return true
            }
        }

        tapMindInterstitialAdapterListener = object : TapMindInterstitialAdapterListener {
            override fun onInterstitialAdLoaded() {
                Log.d(TAG,"$TAG1 : onAdLoaded")
                interstitialAdListener.onAdLoadSuccess()
            }

            override fun onInterstitialAdLoaded(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onAdLoaded Bundle")
                interstitialAdListener.onAdLoadSuccess()
            }

            override fun onInterstitialAdLoadFailed(error: TapMindAdapterError?) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                interstitialAdListener.onAdLoadFailed(AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,error!!.getErrorCode(), error.getMessage())
            }

            override fun onInterstitialAdDisplayed() {
                Log.d(TAG,"$TAG1 : onInterstitialAdDisplayed")
                interstitialAdListener.onAdOpened()
            }

            override fun onInterstitialAdDisplayed(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdDisplayed Bundle")
                interstitialAdListener.onAdOpened()
            }

            override fun onInterstitialAdClicked() {
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked")
                interstitialAdListener.onAdClicked()
            }

            override fun onInterstitialAdClicked(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked Bundle")
                interstitialAdListener.onAdClicked()
            }

            override fun onInterstitialAdHidden() {
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden")
                interstitialAdListener.onAdClosed()
            }

            override fun onInterstitialAdHidden(bundle: Bundle?) {
                interstitialAdListener.onAdClosed()
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden Bundle")
            }

            override fun onInterstitialAdDisplayFailed(error: TapMindAdapterError?) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                interstitialAdListener.onAdShowFailed(error!!.getErrorCode(), error.getMessage())
            }

            override fun onInterstitialAdDisplayFailed(error: TapMindAdapterError?, bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                interstitialAdListener.onAdShowFailed(error!!.getErrorCode(), error.getMessage())
            }
        }


        TapMindsMediationAdapter.getInstance(context)
            .loadInterstitialAd(request!!,context, tapMindInterstitialAdapterListener!!)

    }

    override fun showAd(
        adData: AdData,
        activity: Activity,
        interstitialAdListener: InterstitialAdListener
    ) {
        TapMindsMediationAdapter.getInstance(activity).showInterstitialAd(request!!,activity,tapMindInterstitialAdapterListener!!)
    }

    override fun isAdAvailable(adData: AdData): Boolean {
      return true
    }

    override fun destroyAd(adData: AdData) {
        tapMindInterstitialAdapterListener?.onInterstitialAdHidden()
    }

    private fun getAppVersion(context: Context): String {
        return try {
            val pIInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val appVersion = pIInfo.versionName.toString()
            return appVersion
        } catch (_: Exception) {
            "unknown"
        }
    }

    private fun getAppName(context: Context): String {
        return try {
            val applicationInfo = context.applicationInfo
            val appName = context.packageManager.getApplicationLabel(applicationInfo).toString()
            return appName
        } catch (_: Exception) {
            "Unknown"
        }
    }
}