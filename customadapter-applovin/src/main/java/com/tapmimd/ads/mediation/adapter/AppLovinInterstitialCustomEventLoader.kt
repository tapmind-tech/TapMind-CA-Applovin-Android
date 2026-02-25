package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.tapmimd.ads.mediation.adapter.GeoProviderAppLovin.getAppInfo
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class AppLovinInterstitialCustomEventLoader private constructor(
    val maxAdapterResponseParameters: MaxAdapterResponseParameters?,
    val activity: Activity?,
    val maxAdViewAdapterListener: MaxInterstitialAdapterListener
) {

    private val TAG = "APP@@@"
    private val TAG1 = "AppLovinInterstitialCustomEventLoader"


    private var request: TapMindAdapterResponseParameters? = null
    private var tapMindInterstitialAdapterListener: TapMindInterstitialAdapterListener? = null

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppLovinInterstitialCustomEventLoader? = null

        fun getInstance(
            maxAdapterResponseParameters: MaxAdapterResponseParameters?,
            activity: Activity?,
            callback: MaxInterstitialAdapterListener
        ): AppLovinInterstitialCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppLovinInterstitialCustomEventLoader(
                    maxAdapterResponseParameters,
                    activity,
                    callback
                ).also {
                    INSTANCE = it
                }
            }
        }

    }

    fun loadAd() {
        activity?.runOnUiThread {

            request = object : TapMindAdapterResponseParameters {
                override fun getThirdPartyAdPlacementId() = ""
                override fun getBidResponse() = ""

                // Use dynamic placement here as fallback
                override fun getAdUnitId() =
                    maxAdapterResponseParameters?.adUnitId ?: "tapminds_interstitial_default"

                override fun getLocalExtraParameters() = emptyMap<String, Any>()

                override fun getServerParameters() = Bundle()

                override fun getCustomParameters(): Bundle {
                    val bundle = Bundle()
                    val customParams = maxAdapterResponseParameters?.customParameters
                    val placementId = customParams?.getString("placement_id") ?: getAdUnitId()
                    bundle.putString("placement_id", placementId)
                    return bundle
                }

                override fun hasUserConsent() = true
                override fun isAgeRestrictedUser() = false
                override fun isDoNotSell() = false
                override fun getConsentString() = ""
                override fun isTesting() = false
            }

            val customParams = maxAdapterResponseParameters?.customParameters
            val placementName = customParams?.getString("placement_id") ?: "tapminds_interstitial"
            val country = Locale.getDefault().country
            val geo = GeoProviderAppLovin.get(country)
            val appData = getAppInfo(activity)

            AdRequestPayloadHolder.playLoad = AdRequestPayload(
                appName = appData.appName,
                placementId = placementName,
                appVersion = appData.versionName,
                adType = "Interstitial",
                country = country,
                packageName = appData.packageName,
                "AppLovinInterstitialCustomEventLoader",
            )

            tapMindInterstitialAdapterListener = object : TapMindInterstitialAdapterListener {
                override fun onInterstitialAdLoaded() {
                    Log.d(TAG, "$TAG1 : onAdLoaded")
                    maxAdViewAdapterListener.onInterstitialAdLoaded()
                }

                override fun onInterstitialAdLoaded(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onAdLoaded Bundle")
                    maxAdViewAdapterListener.onInterstitialAdLoaded(bundle)
                }

                override fun onInterstitialAdLoadFailed(tapMindAdapterError: TapMindAdapterError?) {
                    Log.d(
                        TAG,
                        "$TAG1 : onAdFailedToLoad " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                    )
                    maxAdViewAdapterListener.onInterstitialAdLoadFailed(
                        MaxAdapterError(
                            tapMindAdapterError!!.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        )
                    )
                }

                override fun onInterstitialAdDisplayed() {
                    Log.d(TAG, "$TAG1 : onInterstitialAdDisplayed")
                    maxAdViewAdapterListener.onInterstitialAdDisplayed()
                }

                override fun onInterstitialAdDisplayed(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onInterstitialAdDisplayed Bundle")
                    maxAdViewAdapterListener.onInterstitialAdDisplayed(bundle)
                }

                override fun onInterstitialAdClicked() {
                    Log.d(TAG, "$TAG1 : onInterstitialAdClicked")
                    maxAdViewAdapterListener.onInterstitialAdClicked()
                }

                override fun onInterstitialAdClicked(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onInterstitialAdClicked Bundle")
                    maxAdViewAdapterListener.onInterstitialAdClicked(bundle)
                }

                override fun onInterstitialAdHidden() {
                    Log.d(TAG, "$TAG1 : onInterstitialAdHidden")
                    maxAdViewAdapterListener.onInterstitialAdHidden()
                }

                override fun onInterstitialAdHidden(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onInterstitialAdHidden Bundle")
                    maxAdViewAdapterListener.onInterstitialAdHidden(bundle)
                }

                override fun onInterstitialAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?) {
                    Log.d(
                        TAG,
                        "$TAG1 : onAdFailedToLoad " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                    )
                    maxAdViewAdapterListener.onInterstitialAdDisplayFailed(
                        MaxAdapterError(
                            tapMindAdapterError!!.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        )
                    )
                }

                override fun onInterstitialAdDisplayFailed(
                    tapMindAdapterError: TapMindAdapterError?,
                    bundle: Bundle?
                ) {
                    Log.d(
                        TAG,
                        "$TAG1 : onAdFailedToLoad " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                    )
                    maxAdViewAdapterListener.onInterstitialAdDisplayFailed(
                        MaxAdapterError(
                            tapMindAdapterError!!.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        ), bundle
                    )
                }
            }

            TapMindsMediationAdapter.getInstance()
                .loadInterstitialAd(request!!, activity, tapMindInterstitialAdapterListener!!)
        }
    }

    fun showAd(context: Context) {
        Log.d(TAG, "$TAG1 : showAd")
        TapMindsMediationAdapter.getInstance()
            .showInterstitialAd(
                request!!,
                context as Activity,
                tapMindInterstitialAdapterListener!!
            )
    }
}