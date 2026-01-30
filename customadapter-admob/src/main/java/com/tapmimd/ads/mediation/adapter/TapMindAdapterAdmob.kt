package com.tapmimd.ads.mediation.adapter

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.VersionInfo
import com.google.android.gms.ads.mediation.Adapter
import com.google.android.gms.ads.mediation.InitializationCompleteCallback
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationAppOpenAd
import com.google.android.gms.ads.mediation.MediationAppOpenAdCallback
import com.google.android.gms.ads.mediation.MediationAppOpenAdConfiguration
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration
import com.google.android.gms.ads.mediation.MediationConfiguration
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration
import com.google.android.gms.ads.mediation.MediationNativeAdCallback
import com.google.android.gms.ads.mediation.MediationNativeAdConfiguration
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.mediation.NativeAdMapper
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class TapMindAdapterAdmob() : Adapter() {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindAdapterAdmob"

    override fun initialize(
        context: Context,
        initializationCallback: InitializationCompleteCallback,
        mediationConfigurations: List<MediationConfiguration>
    ) {
        initializationCallback.onInitializationSucceeded()
//        Log.d(TAG, "$TAG1 initialize")

//        val request = object : TapMindsAdapterInitializationParameters{
//            override fun getAdUnitId(): String {
//                return ""
//
//            }
//        }
//
//        TapMindFacebookAdapter.initialize(request,context,object : TapMindsAdapter.OnCompletionListener{
//            override fun onCompletion(var1: TapMindsAdapter.InitializationStatus?, var2: String?) {
//                Log.d(TAG, "$TAG1 initializee")
//
//                initializationCallback.onInitializationSucceeded()
//            }
//        })


    }

    override fun getVersionInfo(): VersionInfo {
        // Your adapter version (you define this)
        val major = 1
        val minor = 0
        val micro = 0
        return VersionInfo(major, minor, micro)
    }

    override fun getSDKVersionInfo(): VersionInfo {
        // The SDK version of your internal TapMind SDK
        val version = "1.2.3" // example: replace with your SDK version

        val parts = version.split(".")
        val major = parts.getOrNull(0)?.toIntOrNull() ?: 1
        val minor = parts.getOrNull(1)?.toIntOrNull() ?: 0
        val micro = parts.getOrNull(2)?.toIntOrNull() ?: 0

        return VersionInfo(major, minor, micro)
    }

    override fun loadBannerAd(
        adConfig: MediationBannerAdConfiguration,
        callback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
    ) {
        Log.d(TAG, "$TAG1 loadBannerAd")
        val context = adConfig.context
        val placementId = adConfig.serverParameters.getString("label")
        Log.d("loadBannerAd", "$TAG1 placementId: ${adConfig.serverParameters}")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = placementId,
            appVersion = getAppVersion(context),
            adType = "Banner",
            country = Locale.getDefault().country
        )
        val data = AdmobBannerCustomEventLoader.getInstance(adConfig, callback)
        data.loadAdd()
    }

    override fun loadNativeAdMapper(
        adConfig: MediationNativeAdConfiguration,
        callback: MediationAdLoadCallback<NativeAdMapper?, MediationNativeAdCallback?>
    ) {
        Log.d(TAG, "$TAG1 loadNativeAdMapper")
        val context = adConfig.context
        val placementId = adConfig.serverParameters.getString("label")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = placementId,
            appVersion = getAppVersion(context),
            adType = "Native",
            country = Locale.getDefault().country
        )
        AdmobNativeCustomEventLoader.getInstance(adConfig, callback).loadAd()
    }


    override fun loadInterstitialAd(
        adConfig: MediationInterstitialAdConfiguration,
        callback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
    ) {
        Log.d(TAG, "$TAG1 loadInterstitialAd")
        val context = adConfig.context
        val placementId = adConfig.serverParameters.getString("label")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = placementId,
            appVersion = getAppVersion(context),
            adType = "Interstitial",
            country = Locale.getDefault().country
        )
        val loader = AdmobInterstitialCustomEventLoader(adConfig, callback)
        loader.loadAd()
    }


    override fun loadAppOpenAd(
        adConfig: MediationAppOpenAdConfiguration,
        callback: MediationAdLoadCallback<MediationAppOpenAd?, MediationAppOpenAdCallback?>
    ) {
        val context = adConfig.context
        val placementId = adConfig.serverParameters.getString("label")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = placementId,
            appVersion = getAppVersion(context),
            adType = "AppOpen",
            country = Locale.getDefault().country
        )
        Log.d(TAG, "$TAG1 loadAppOpenAd")
    }

    override fun loadRewardedAd(
        adConfig: MediationRewardedAdConfiguration,
        callback: MediationAdLoadCallback<MediationRewardedAd?, MediationRewardedAdCallback?>
    ) {
        Log.d(TAG, "$TAG1 loadRewardedAd")
        val context = adConfig.context
        val placementId = adConfig.serverParameters.getString("label")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = placementId,
            appVersion = getAppVersion(context),
            adType = "Rewarded",
            country = Locale.getDefault().country
        )
        val data = AdmobRewardedCustomEventLoader.getInstance(adConfig, callback)
        data.loadAd()
    }

    override fun loadRewardedInterstitialAd(
        adConfig: MediationRewardedAdConfiguration,
        callback: MediationAdLoadCallback<MediationRewardedAd?, MediationRewardedAdCallback?>
    ) {
        val context = adConfig.context
        val placementId = adConfig.serverParameters.getString("label")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(context),
            placementId = placementId,
            appVersion = getAppVersion(context),
            adType = "RewardedInterstitial",
            country = Locale.getDefault().country
        )
        Log.d(TAG, "$TAG1 loadRewardedInterstitialAd")
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