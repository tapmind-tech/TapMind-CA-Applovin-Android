package com.ironsource.adapters.custom.istapmindcustomadapter

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

class ISTapMindCustomAdapterCustomInterstitial(networkSettings: NetworkSettings) :
    BaseInterstitial<ISTapMindCustomAdapterCustomAdapter>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobInterstitialCustomEventLoader"

    private var request: TapMindAdapterResponseParameters? = null
    private var tapMindInterstitialAdapterListener: TapMindInterstitialAdapterListener? = null
    private var isAdReady = false

    init {
        Log.e(TAG, "╔═══════════════════════════════════════════════════════╗")
        Log.e(TAG, "║  ISTapMindCustomAdapterCustomInterstitial CREATED     ║")
        Log.e(TAG, "╚═══════════════════════════════════════════════════════╝")
    }

    override fun loadAd(
        adData: AdData,
        context: Context,
        interstitialAdListener: InterstitialAdListener
    ) {
        val adUnitData = adData.adUnitData

        for ((key, value) in adUnitData) {
            Log.d(TAG, "$key : $value")
        }

        val networkId = adUnitData["networkId"] as? String ?: ""
        val config = adData.configuration
        val instanceName = config["instanceName"] as? String

        Log.e("AdUnitData", "instanceName = $instanceName")

        val playLoad = AdRequestPayload(
            appName = getAppName(context),
//            placementId = "interstitial_map",
            placementId = instanceName,
            appVersion = getAppVersion(context),
            adType = "Interstitial",
            country = Locale.getDefault().country,
            packageName = getPackageName(context),
            "ISTapMindCustomAdapterCustomAdapter"
        )

        Log.e(TAG, "loadAd playLoad: $playLoad")

        AdRequestPayloadHolder.playLoad = playLoad

        request = object : TapMindAdapterResponseParameters {

            override fun getThirdPartyAdPlacementId(): String {
                return networkId
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
                return true
            }
        }

        tapMindInterstitialAdapterListener = object : TapMindInterstitialAdapterListener {
            override fun onInterstitialAdLoaded() {
                Log.d(TAG, "$TAG1 : onAdLoaded")
                isAdReady = true
                interstitialAdListener.onAdLoadSuccess()
            }

            override fun onInterstitialAdLoaded(bundle: Bundle?) {
                Log.d(TAG, "$TAG1 : onAdLoaded Bundle")
                isAdReady = true
                interstitialAdListener.onAdLoadSuccess()
            }

            override fun onInterstitialAdLoadFailed(tapMindAdapterError: TapMindAdapterError?) {
                Log.d(
                    TAG,
                    "$TAG1 : onAdFailedToLoad " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                )
                isAdReady = false
                interstitialAdListener.onAdLoadFailed(
                    AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,
                    tapMindAdapterError!!.getErrorCode(),
                    tapMindAdapterError.getMessage()
                )
            }

            override fun onInterstitialAdDisplayed() {
                Log.d(TAG, "$TAG1 : onInterstitialAdDisplayed")
                interstitialAdListener.onAdOpened()
            }

            override fun onInterstitialAdDisplayed(bundle: Bundle?) {
                Log.d(TAG, "$TAG1 : onInterstitialAdDisplayed Bundle")
                interstitialAdListener.onAdOpened()
            }

            override fun onInterstitialAdClicked() {
                Log.d(TAG, "$TAG1 : onInterstitialAdClicked")
                interstitialAdListener.onAdClicked()
            }

            override fun onInterstitialAdClicked(bundle: Bundle?) {
                Log.d(TAG, "$TAG1 : onInterstitialAdClicked Bundle")
                interstitialAdListener.onAdClicked()
            }

            override fun onInterstitialAdHidden() {
                isAdReady = false
                Log.d(TAG, "$TAG1 : onInterstitialAdHidden")
                interstitialAdListener.onAdClosed()
            }

            override fun onInterstitialAdHidden(bundle: Bundle?) {
                isAdReady = false
                interstitialAdListener.onAdClosed()
                Log.d(TAG, "$TAG1 : onInterstitialAdHidden Bundle")
            }

            override fun onInterstitialAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?) {
                Log.d(
                    TAG,
                    "$TAG1 : onAdFailedToLoad " + tapMindAdapterError?.getErrorCode() + " " + tapMindAdapterError?.getErrorMessage()
                )
                isAdReady = false
                interstitialAdListener.onAdShowFailed(
                    tapMindAdapterError!!.getErrorCode(),
                    tapMindAdapterError.getMessage()
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
                isAdReady = false
                interstitialAdListener.onAdShowFailed(
                    tapMindAdapterError!!.getErrorCode(),
                    tapMindAdapterError.getMessage()
                )
            }
        }

        TapMindsMediationAdapter.getInstance()
            .loadInterstitialAd(request!!, context, tapMindInterstitialAdapterListener!!)
    }

    override fun showAd(
        adData: AdData,
        activity: Activity,
        interstitialAdListener: InterstitialAdListener
    ) {
        activity.runOnUiThread {
            Log.d(TAG, "Showing ad with activity: ${activity.javaClass.simpleName}")

            TapMindsMediationAdapter.getInstance()
                .showInterstitialAd(request!!, activity, tapMindInterstitialAdapterListener!!)
        }
    }

    override fun isAdAvailable(adData: AdData): Boolean {
        Log.d(TAG, "$TAG1: isAdAvailable() called, returning: $isAdReady")
        return isAdReady
    }

    override fun destroyAd(adData: AdData) {
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