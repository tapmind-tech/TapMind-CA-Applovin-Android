package com.ironsource.adapters.custom.istapmindcustomadapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.ironsource.mediationsdk.adunit.adapter.BaseRewardedVideo
import com.ironsource.mediationsdk.adunit.adapter.listener.RewardedVideoAdListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrorType
import com.ironsource.mediationsdk.model.NetworkSettings
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class ISTapMindCustomAdapterCustomRewardedVideo(networkSettings: NetworkSettings) :
    BaseRewardedVideo<ISTapMindCustomAdapterCustomAdapter>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "ISTapMindCustomAdapterCustomRewardedVideo"

    private var request: TapMindAdapterResponseParameters? = null
    private var tapMindRewardedAdapterListener: TapMindRewardedAdapterListener? = null
    private var isRewardedLoaded = false
    private var isRewardedLoading = false

    override fun loadAd(
        adData: AdData,
        context: Context,
        rewardedVideoAdListener: RewardedVideoAdListener
    ) {
        if (isRewardedLoading || isRewardedLoaded) {
            Log.w(TAG, "⚠️ Rewarded already loading/loaded. Ignoring duplicate loadAd call.")
            return
        }

        isRewardedLoading = true

        val adUnitData = adData.adUnitData

        for ((key, value) in adUnitData) {
            Log.d("AdUnitData", "$key : $value")
        }

        val config = adData.configuration
        val instanceName = config["instanceName"] as? String

        Log.e("AdUnitData", "instanceName = $instanceName")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = instanceName,
            appVersion = getAppVersion(context),
            adType = "Rewarded",
            country = Locale.getDefault().country,
            packageName = getPackageName(context),
            "ISTapMindCustomAdapterCustomAdapter"
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

        tapMindRewardedAdapterListener = object : TapMindRewardedAdapterListener {
            override fun onRewardedAdLoaded() {
                isRewardedLoaded = true
                isRewardedLoading = false
                Log.d(TAG, "$TAG1 : onAdLoaded")
                rewardedVideoAdListener.onAdLoadSuccess()
            }

            override fun onRewardedAdLoaded(bundle: Bundle?) {
                isRewardedLoaded = true
                isRewardedLoading = false
                Log.d(TAG, "$TAG1 : onAdLoaded Bundle")
                rewardedVideoAdListener.onAdLoadSuccess()
            }

            override fun onRewardedAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                isRewardedLoaded = false
                isRewardedLoading = false
                Log.d(
                    TAG,
                    "$TAG1 : onAdFailedToLoad " + tapMindAdapterError.getErrorCode() + " " + tapMindAdapterError.getErrorMessage()
                )
                rewardedVideoAdListener.onAdLoadFailed(
                    AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,
                    tapMindAdapterError.getErrorCode(),
                    tapMindAdapterError.getMessage()
                )
            }

            override fun onRewardedAdDisplayed() {
                Log.d(TAG, "$TAG1 : onRewardedAdDisplayed")
//                rewardedVideoAdListener.onAdOpened()
                rewardedVideoAdListener.onAdVisible()
                rewardedVideoAdListener.onAdStarted()
            }

            override fun onRewardedAdDisplayed(bundle: Bundle) {
                Log.d(TAG, "$TAG1 : onRewardedAdDisplayed Bundle")
                rewardedVideoAdListener.onAdStarted()
                rewardedVideoAdListener.onAdVisible()
            }

            override fun onRewardedAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?) {
                isRewardedLoading = false
                Log.d(
                    TAG,
                    "$TAG1 : onRewardedAdDisplayFailed " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                )
                rewardedVideoAdListener.onAdShowFailed(
                    tapMindAdapterError!!.getErrorCode(),
                    tapMindAdapterError.getMessage()
                )
            }

            override fun onRewardedAdDisplayFailed(
                tapMindAdapterError: TapMindAdapterError?,
                bundle: Bundle?
            ) {
                isRewardedLoading = false
                isRewardedLoaded = false
                Log.d(
                    TAG,
                    "$TAG1 : onRewardedAdDisplayFailed Bundle " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                )
                rewardedVideoAdListener.onAdShowFailed(
                    tapMindAdapterError!!.getErrorCode(),
                    tapMindAdapterError.getMessage()
                )
            }

            override fun onRewardedAdClicked() {
                Log.d(TAG, "$TAG1 : onInterstitialAdClicked")
                rewardedVideoAdListener.onAdClicked()
            }

            override fun onRewardedAdClicked(bundle: Bundle?) {
                Log.d(TAG, "$TAG1 : onInterstitialAdClicked Bundle")
                rewardedVideoAdListener.onAdClicked()
            }

            override fun onRewardedAdHidden() {
                isRewardedLoading = false
                isRewardedLoaded = false
                Log.d(TAG, "$TAG1 : onInterstitialAdHidden")
                rewardedVideoAdListener.onAdClosed()
            }

            override fun onRewardedAdHidden(bundle: Bundle?) {
                isRewardedLoading = false
                Log.d(TAG, "$TAG1 : onInterstitialAdHidden Bundle")
                rewardedVideoAdListener.onAdClosed()
            }

            override fun onUserRewarded(tapMindReward: TapMindReward) {
//                rewardedVideoAdListener.onAdRewarded( mapOf(tapMindReward.label to tapMindReward.amount.toString()))
            }

            override fun onUserRewarded(tapMindReward: TapMindReward?, bundle: Bundle?) {
//                rewardedVideoAdListener.onAdRewarded( mapOf(tapMindReward?.label to tapMindReward?.amount.toString()))
            }
        }

        TapMindsMediationAdapter.getInstance()
            .loadRewardedAd(request!!, context,  tapMindRewardedAdapterListener!!)
    }

    override fun showAd(
        adData: AdData,
        activity: Activity,
        rewardedVideoAdListener: RewardedVideoAdListener
    ) {
        Log.e(TAG, "showAd Ironsource")
        if (!isRewardedLoaded) {
            Log.e(TAG, "❌ showAd called but rewarded not loaded")
            rewardedVideoAdListener.onAdShowFailed(
                510,
                "Rewarded ad not ready"
            )
            return
        }

        if (activity.isFinishing || activity.isDestroyed) {
            Log.e(TAG, "❌ Activity not valid for showing rewarded ad")
            rewardedVideoAdListener.onAdShowFailed(
                511,
                "Invalid activity"
            )
            return
        }

        activity.runOnUiThread {
            Log.e(TAG, "✅ Showing rewarded ad now")
            TapMindsMediationAdapter.getInstance()
                .showRewardedAd(request!!, activity, tapMindRewardedAdapterListener!!)
        }
    }

    override fun isAdAvailable(adData: AdData): Boolean {
        return isRewardedLoaded
    }

    override fun destroyAd(adData: AdData) {
//        tapMindRewardedAdapterListener?.onRewardedAdHidden()
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

    private fun getPackageName(context: Context): String {
        return try {
            context.packageName
        } catch (_: Exception) {
            "Unknown"
        }
    }
}