package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.applovin.mediation.MaxReward
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxRewardedAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.tapmimd.ads.mediation.adapter.GeoProviderAppLovin.getAppInfo
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class AppLovinRewardedCustomEventLoader private constructor(
    val maxAdapterResponseParameters: MaxAdapterResponseParameters?,
    val activity: Activity?,
    val maxAdViewAdapterListener: MaxRewardedAdapterListener
) {

    private val TAG = "APP@@@"
    private val TAG1 = "AppLovinRewardedCustomEventLoader"


    private var request: TapMindAdapterResponseParameters? = null
    private var tapMindRewardedAdapterListener: TapMindRewardedAdapterListener? = null

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppLovinRewardedCustomEventLoader? = null

        fun getInstance(
            maxAdapterResponseParameters: MaxAdapterResponseParameters?, activity: Activity?,
            callback: MaxRewardedAdapterListener
        ): AppLovinRewardedCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppLovinRewardedCustomEventLoader(
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

                override fun hasUserConsent(): Boolean {
                    return true
                }

                override fun isAgeRestrictedUser(): Boolean {
                    return false
                }

                override fun isDoNotSell(): Boolean {
                    return false
                }

                override fun getConsentString(): String {
                    return ""
                }

                override fun isTesting(): Boolean {
                    return false
                }
            }

            val customParams = maxAdapterResponseParameters?.customParameters
            val placementName = customParams?.getString("placement_id") ?: "tapminds_reward"

            val country = Locale.getDefault().country
            val geo = GeoProviderAppLovin.get(country)
            val appData = getAppInfo(activity)

            AdRequestPayloadHolder.playLoad = AdRequestPayload(
                appName = appData.appName,
                placementId = placementName,
                appVersion = appData.versionName,
                adType = "Rewarded",
                country = country,
                packageName = appData.packageName,
                "AppLovinRewardedCustomEventLoader",
            )

            tapMindRewardedAdapterListener = object : TapMindRewardedAdapterListener {
                override fun onRewardedAdLoaded() {
                    Log.d(TAG, "$TAG1 : onAdLoaded")
                    maxAdViewAdapterListener.onRewardedAdLoaded()
                }

                override fun onRewardedAdLoaded(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onAdLoaded Bundle")
                    maxAdViewAdapterListener.onRewardedAdLoaded(bundle)
                }

                override fun onRewardedAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                    Log.d(
                        TAG,
                        "$TAG1 : onAdFailedToLoad " + tapMindAdapterError.getErrorCode() + " " + tapMindAdapterError.getErrorMessage()
                    )
                    maxAdViewAdapterListener.onRewardedAdLoadFailed(
                        MaxAdapterError(
                            tapMindAdapterError.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        )
                    )
                }

                override fun onRewardedAdDisplayed() {
                    Log.d(TAG, "$TAG1 : onRewardedAdDisplayed")
                    maxAdViewAdapterListener.onRewardedAdDisplayed()
                }

                override fun onRewardedAdDisplayed(bundle: Bundle) {
                    Log.d(TAG, "$TAG1 : onRewardedAdDisplayed Bundle")
                    maxAdViewAdapterListener.onRewardedAdDisplayed(bundle)
                }

                override fun onRewardedAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?) {
                    Log.d(
                        TAG,
                        "$TAG1 : onRewardedAdDisplayFailed " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                    )
                    maxAdViewAdapterListener.onRewardedAdDisplayFailed(
                        MaxAdapterError(
                            tapMindAdapterError!!.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        )
                    )
                }

                override fun onRewardedAdDisplayFailed(
                    tapMindAdapterError: TapMindAdapterError?,
                    bundle: Bundle?
                ) {
                    Log.d(
                        TAG,
                        "$TAG1 : onRewardedAdDisplayFailed Bundle " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                    )
                    maxAdViewAdapterListener.onRewardedAdDisplayFailed(
                        MaxAdapterError(
                            tapMindAdapterError!!.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        )
                    )
                }

                override fun onRewardedAdClicked() {
                    Log.d(TAG, "$TAG1 : onInterstitialAdClicked")
                    maxAdViewAdapterListener.onRewardedAdClicked()
                }

                override fun onRewardedAdClicked(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onInterstitialAdClicked Bundle")
                    maxAdViewAdapterListener.onRewardedAdClicked(bundle)
                }

                override fun onRewardedAdHidden() {
                    Log.d(TAG, "$TAG1 : onInterstitialAdHidden")
                    maxAdViewAdapterListener.onRewardedAdHidden()
                }

                override fun onRewardedAdHidden(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onInterstitialAdHidden Bundle")
                    maxAdViewAdapterListener.onRewardedAdHidden(bundle)
                }

                override fun onUserRewarded(tapMindReward: TapMindReward) {
                    maxAdViewAdapterListener.onUserRewarded(object : MaxReward {
                        override fun getLabel(): String {
                            return tapMindReward.label
                        }

                        override fun getAmount(): Int {
                            return tapMindReward.amount
                        }

                    })
                }

                override fun onUserRewarded(tapMindReward: TapMindReward?, bundle: Bundle?) {
                    maxAdViewAdapterListener.onUserRewarded(object : MaxReward {
                        override fun getLabel(): String? {
                            return tapMindReward?.label
                        }

                        override fun getAmount(): Int {
                            return tapMindReward!!.amount
                        }

                    }, bundle)
                }
            }

            TapMindsMediationAdapter.getInstance()
                .loadRewardedAd(request!!, activity, tapMindRewardedAdapterListener!!)
        }
    }

    fun showAd(context: Context) {
        TapMindsMediationAdapter.getInstance()
            .showRewardedAd(request!!, context as Activity, tapMindRewardedAdapterListener!!)
    }
}