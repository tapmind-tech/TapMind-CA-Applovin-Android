package com.tapmimd.ads.mediation.adapter

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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
import com.tapmimd.ads.mediation.adapter.GeoProviderAdMob.getAppInfo
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import org.json.JSONObject
import java.util.Locale

class TapMindAdapterAdmob : Adapter() {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindAdapterAdmob"

    override fun initialize(
        context: Context,
        initializationCallback: InitializationCompleteCallback,
        mediationConfigurations: List<MediationConfiguration>
    ) {
        Log.e(TAG, "initialize: TapMindAdapterAdmob")
        initializationCallback.onInitializationSucceeded()
        val testDeviceIds = listOf("50F31F822A7EFC9B3784DB2124C73C03")

        val config = RequestConfiguration.Builder()
            .setTestDeviceIds(testDeviceIds)
            .build()

        MobileAds.setRequestConfiguration(config)
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
        Log.d("loadBannerAd", "$TAG1 placementId: ${adConfig.serverParameters}")
        val paramString = adConfig.serverParameters.getString("parameter")
        val placementName = if (paramString != null) {
            val jsonObject = JSONObject(paramString)
            jsonObject.optString("placementName")
        } else {
            "tapminds_banner"
        }

        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderAdMob.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = placementName,
            appVersion = appData.versionName,
            adType = "Banner",
            country = country,
            packageName = appData.packageName,
            adapterName = "TapMindAdapterAdmob",
        )
        val data = AdmobBannerCustomEventLoader(adConfig, callback)
        data.loadAdd()
    }

    override fun loadNativeAdMapper(
        adConfig: MediationNativeAdConfiguration,
        callback: MediationAdLoadCallback<NativeAdMapper?, MediationNativeAdCallback?>
    ) {
        Log.d(TAG, "$TAG1 loadNativeAdMapper")
        val context = adConfig.context
        val paramString = adConfig.serverParameters.getString("parameter")
        val placementName = if (paramString != null) {
            val jsonObject = JSONObject(paramString)
            jsonObject.optString("placementName")
        } else {
            "tapminds_native"
        }

        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderAdMob.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = placementName,
            appVersion = appData.versionName,
            adType = "Native",
            country = country,
            packageName = appData.packageName,
            "TapMindAdapterAdmob",
        )
        AdmobNativeCustomEventLoader(adConfig, callback).loadAd()
    }


    override fun loadInterstitialAd(
        adConfig: MediationInterstitialAdConfiguration,
        callback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
    ) {
        Log.d(TAG, "$TAG1 loadInterstitialAd")
        Log.d(TAG, "$TAG1 adConfig ${adConfig.context}")
        val context = adConfig.context
        val paramString = adConfig.serverParameters.getString("parameter")
        val placementName = if (paramString != null) {
            val jsonObject = JSONObject(paramString)
            jsonObject.optString("placementName")
        } else {
            "tapminds_interstitial"
        }

        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderAdMob.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = placementName,
            appVersion = appData.versionName,
            adType = "Interstitial",
            country = country,
            packageName = appData.packageName,
            "TapMindAdapterAdmob",
        )

        AdmobInterstitialCustomEventLoader(adConfig, callback).loadAd()
    }

    override fun loadAppOpenAd(
        adConfig: MediationAppOpenAdConfiguration,
        callback: MediationAdLoadCallback<MediationAppOpenAd?, MediationAppOpenAdCallback?>
    ) {
        val context = adConfig.context
        val paramString = adConfig.serverParameters.getString("parameter")
        val placementName = if (paramString != null) {
            val jsonObject = JSONObject(paramString)
            jsonObject.optString("placementName")
        } else {
            "tapminds_AppOpen"
        }

        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderAdMob.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = placementName,
            appVersion = appData.versionName,
            adType = "AppOpen",
            country = country,
            packageName = appData.packageName,
            "TapMindAdapterAdmob",
        )
        Log.d(TAG, "$TAG1 loadAppOpenAd")
    }

    override fun loadRewardedAd(
        adConfig: MediationRewardedAdConfiguration,
        callback: MediationAdLoadCallback<MediationRewardedAd?, MediationRewardedAdCallback?>
    ) {
        Log.d(TAG, "$TAG1 loadRewardedAd")
        val context = adConfig.context
        val paramString = adConfig.serverParameters.getString("parameter")
        val placementName = if (paramString != null) {
            val jsonObject = JSONObject(paramString)
            jsonObject.optString("placementName")
        } else {
            "tapminds_reward"
        }

        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderAdMob.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = placementName,
            appVersion = appData.versionName,
            adType = "Rewarded",
            country = country,
            packageName = appData.packageName,
            "TapMindAdapterAdmob",
        )
        AdmobRewardedCustomEventLoader(adConfig, callback).loadAd()
    }

    override fun loadRewardedInterstitialAd(
        adConfig: MediationRewardedAdConfiguration,
        callback: MediationAdLoadCallback<MediationRewardedAd?, MediationRewardedAdCallback?>
    ) {
        val context = adConfig.context
        val paramString = adConfig.serverParameters.getString("parameter")
        val placementName = if (paramString != null) {
            val jsonObject = JSONObject(paramString)
            jsonObject.optString("placementName")
        } else {
            "tapminds_RewardedInterstitial"
        }

        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderAdMob.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = placementName,
            appVersion = appData.versionName,
            adType = "RewardedInterstitial",
            country = country,
            packageName = appData.packageName,
            "TapMindAdapterAdmob",
        )
        Log.d(TAG, "$TAG1 loadRewardedInterstitialAd")
    }
}