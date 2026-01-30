package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.ironsource.mediationsdk.adunit.adapter.BaseRewardedVideo
import com.ironsource.mediationsdk.adunit.adapter.listener.RewardedVideoAdListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrorType
import com.ironsource.mediationsdk.model.NetworkSettings
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class TapMindRewardedAdapter (networkSettings: NetworkSettings) : BaseRewardedVideo<TapMindAdapterIronSource>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindRewardedAdapter"

    private var  request : TapMindAdapterResponseParameters?= null
    private var tapMindRewardedAdapterListener : TapMindRewardedAdapterListener ?= null

    override fun loadAd(adData: AdData, context: Context, rewardedVideoAdListener: RewardedVideoAdListener) {

        val adUnitData = adData.adUnitData

        for ((key, value) in adUnitData) {
            Log.d("AdUnitData", "$key : $value")
        }

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = "",
            appVersion = getAppVersion(context),
            adType = "Rewarded",
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
                return false
            }
        }

        tapMindRewardedAdapterListener = object : TapMindRewardedAdapterListener{
            override fun onRewardedAdLoaded() {
                Log.d(TAG,"$TAG1 : onAdLoaded")
                rewardedVideoAdListener.onAdLoadSuccess()
            }

            override fun onRewardedAdLoaded(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onAdLoaded Bundle")
                rewardedVideoAdListener.onAdLoadSuccess()
            }

            override fun onRewardedAdLoadFailed(error: TapMindAdapterError) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                rewardedVideoAdListener.onAdLoadFailed(AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,error!!.getErrorCode(), error.getMessage())
            }

            override fun onRewardedAdDisplayed() {
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayed")
                rewardedVideoAdListener.onAdOpened()
            }

            override fun onRewardedAdDisplayed(bundle: Bundle) {
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayed Bundle")
                rewardedVideoAdListener.onAdOpened()
            }

            override fun onRewardedAdDisplayFailed(error: TapMindAdapterError?) {
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayFailed "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                rewardedVideoAdListener.onAdShowFailed(error!!.getErrorCode(), error.getMessage())
            }

            override fun onRewardedAdDisplayFailed(error: TapMindAdapterError?, bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayFailed Bundle "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                rewardedVideoAdListener.onAdShowFailed(error!!.getErrorCode(), error.getMessage())
            }

            override fun onRewardedAdClicked() {
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked")
                rewardedVideoAdListener.onAdClicked()
            }

            override fun onRewardedAdClicked(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked Bundle")
                rewardedVideoAdListener.onAdClicked()
            }

            override fun onRewardedAdHidden() {
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden")
                rewardedVideoAdListener.onAdClosed()
            }

            override fun onRewardedAdHidden(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden Bundle")
                rewardedVideoAdListener.onAdClosed()
            }

            override fun onUserRewarded(tapMindReward: TapMindReward) {
                rewardedVideoAdListener.onAdRewarded( mapOf(tapMindReward.label to tapMindReward.amount.toString()))
            }

            override fun onUserRewarded(tapMindReward: TapMindReward?, bundle: Bundle?) {
                rewardedVideoAdListener.onAdRewarded( mapOf(tapMindReward?.label to tapMindReward?.amount.toString()))
            }

        }

        TapMindsMediationAdapter.getInstance(context)
            .loadRewardedAd(request!!,context, tapMindRewardedAdapterListener!!)
    }

    override fun showAd(adData: AdData, activity: Activity, rewardedVideoAdListener: RewardedVideoAdListener) {
        TapMindsMediationAdapter.getInstance(activity)
            .showRewardedAd(request!!,activity as Activity,tapMindRewardedAdapterListener!!)
    }

    override fun isAdAvailable(adData: AdData): Boolean {
        return true
    }

    override fun destroyAd(adData: AdData) {
        tapMindRewardedAdapterListener?.onRewardedAdHidden()
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