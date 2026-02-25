package com.tapminds.ironSource

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.network.AdData
import com.tapminds.network.ApiUtils
import com.tapminds.network.DataItem
import com.tapminds.tapmindsads.TapMindAdFormat
import com.unity3d.mediation.LevelPlayAdError
import com.unity3d.mediation.LevelPlayAdInfo
import com.unity3d.mediation.LevelPlayAdSize
import com.unity3d.mediation.banner.LevelPlayBannerAdView
import com.unity3d.mediation.banner.LevelPlayBannerAdViewListener
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAd
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAdListener
import com.unity3d.mediation.rewarded.LevelPlayReward
import com.unity3d.mediation.rewarded.LevelPlayRewardedAd
import com.unity3d.mediation.rewarded.LevelPlayRewardedAdListener
import java.util.concurrent.atomic.AtomicBoolean

class IronSourceManager {

    val TAG = "APP@@@"
    val TAG1 = "IronSourceManager"
    private val initialized = AtomicBoolean()
    private val ADAPTIVE_BANNER_TYPE_INLINE = "inline"
    private val apiUtils = ApiUtils()

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: IronSourceManager? = null

        fun getInstance(): IronSourceManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: IronSourceManager().also {
                    INSTANCE = it
                }
            }
        }
    }

    fun loadInterstitialAd(
        adData: AdData,
        parameters: TapMindAdapterResponseParameters,
        adapter: DataItem,
        context: Context,
        listener: TapMindInterstitialAdapterListener,
        adapterName: String
    ) {
//        if (adapters.isEmpty()) {
//            listener.onInterstitialAdLoadFailed(
//                TapMindAdapterError(204, "No interstitial adapters available")
//            )
//            return
//        }

        updateMuteState(parameters.getServerParameters())

//        val sortedAdapters = adapters.sortedBy { it.priority }

//        fun loadAt(index: Int) {
//            if (index >= sortedAdapters.size) {
//                listener.onInterstitialAdLoadFailed(
//                    TapMindAdapterError(204, "No fill from all interstitial adapters")
//                )
//                return
//            }

//            val adapter = sortedAdapters[index]
        val adUnitId = adapter.adUnitId.toString()

        Log.d(
            "TapMindAdapterAdmob",
            "Interstitial Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
        )
        val mInterstitialAd = LevelPlayInterstitialAd(adUnitId)
        mInterstitialAd.setListener(object : LevelPlayInterstitialAdListener {

            override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {

                Log.d(TAG, "LevelPlay Interstitial : onAdLoaded")
                Log.e(TAG, "isAdReady: ${mInterstitialAd.isAdReady}")
                Log.e(TAG, "isAdReady: $levelPlayAdInfo")

                Handler(Looper.getMainLooper()).postDelayed({
                    if (mInterstitialAd.isAdReady) {
                        Log.d(
                            TAG,
                            "Showing ad with activity: ${context::class.java.simpleName}"
                        )
                        if (context is Activity && !context.isFinishing && !context.isDestroyed) {
                            mInterstitialAd.showAd(context)
                        } else {
                            Log.e(TAG, "Activity is not valid")
                        }
                    } else {
                        Log.e(TAG, "Ad is not ready to show")
                    }
                }, 100)
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdLoaded",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdLoadFailed(levelPlayAdError: LevelPlayAdError) {
                Log.d(
                    TAG,
                    "LevelPlay Interstitial : onAdLoadFailed " + levelPlayAdError.errorMessage
                )
                Log.d(
                    TAG,
                    "LevelPlay Interstitial : onAdLoadFailed " + levelPlayAdError.errorCode
                )
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdLoadFailed",
                    levelPlayAdError.errorMessage,
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
                listener.onInterstitialAdLoadFailed(
                    TapMindAdapterError(
                        TapMindAdapterError.ERROR_CODE_NO_FILL,
                        levelPlayAdError.errorMessage
                    )
                )
            }

            override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdDisplayed",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdDisplayFailed(
                error: LevelPlayAdError,
                levelPlayAdInfo: LevelPlayAdInfo
            ) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdDisplayFailed",
                    error.errorMessage,
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdClicked(levelPlayAdInfo: LevelPlayAdInfo) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdClicked",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdClosed(levelPlayAdInfo: LevelPlayAdInfo) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdClosed",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdInfoChanged(levelPlayAdInfo: LevelPlayAdInfo) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Interstitial",
                    "onAdDisplayFailed",
                    "onAdInfoChanged",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }
        })

        mInterstitialAd.loadAd()
//        }
//        // 🚀 Start waterfall
//        loadAt(0)
    }

    fun loadRewardedAd(
        adData: AdData,
        parameters: TapMindAdapterResponseParameters,
        adapter: DataItem,
        context: Context,
        listener: TapMindRewardedAdapterListener,
        adapterName: String
    ) {
//        if (adapters.isEmpty()) {
//            listener.onRewardedAdLoadFailed(
//                TapMindAdapterError(204, "No rewarded adapters available")
//            )
//            return
//        }
//        updateMuteState(parameters.getServerParameters())
//        val sortedAdapters = adapters.sortedBy { it.priority }

//        fun loadAt(index: Int) {
//            if (index >= sortedAdapters.size) {
//                listener.onRewardedAdLoadFailed(
//                    TapMindAdapterError(204, "No fill from all rewarded adapters")
//                )
//                return
//            }

//            val adapter = sortedAdapters[index]
        val adUnitId = adapter.adUnitId.toString()

        Log.d(
            TAG,
            "Rewarded Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
        )
        val mRewardedAd = LevelPlayRewardedAd(adUnitId)
        mRewardedAd.setListener(object : LevelPlayRewardedAdListener {

            override fun onAdLoaded(p0: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay Rewarded : onAdLoaded")
                if (mRewardedAd.isAdReady) {
                    mRewardedAd.showAd(context as Activity)
                }
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Rewarded",
                    "onAdLoaded",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdLoadFailed(error: LevelPlayAdError) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Rewarded",
                    "onAdLoadFailed",
                    error.errorMessage,
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
                Log.d(TAG, "LevelPlay Rewarded : onAdLoadFailed " + error.errorMessage)
                listener.onRewardedAdLoadFailed(
                    TapMindAdapterError(
                        TapMindAdapterError.ERROR_CODE_NO_FILL,
                        error.errorMessage
                    )
                )
//                    loadAt(index + 1)
            }

            override fun onAdDisplayed(p0: LevelPlayAdInfo) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Rewarded",
                    "onAdDisplayed",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }

            override fun onAdRewarded(p0: LevelPlayReward, p1: LevelPlayAdInfo) {
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Rewarded",
                    "onAdRewarded",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    "",
                    adapterName
                )
            }
        })
//        }
//
//        loadAt(0)
    }

    fun loadAdViewAd(
        adData: AdData,
        adapter: DataItem,
        parameters: TapMindAdapterResponseParameters,
        adFormat: TapMindAdFormat,
        activity: Activity,
        listener: TapMindAdViewAdapterListener,
        adapterName: String
    ) {
//        if (adapters.isEmpty()) {
//            listener.onAdViewAdLoadFailed(
//                TapMindAdapterError(204, "No banner adapters available")
//            )
//            return
//        }
//
//        val sortedAdapters = adapters.sortedBy { it.priority }
//
//
//        fun loadAt(index: Int) {
//            if (index >= sortedAdapters.size) {
//                listener.onAdViewAdLoadFailed(
//                    TapMindAdapterError(204, "No fill from all banner adapters")
//                )
//                return
//            }
//
//            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()


            val width = adapter.sizes?.firstOrNull()?.width ?: 0
            val height = adapter.sizes?.firstOrNull()?.height ?: 0

            val adSize = getLevelPlayBannerSizeFromApi(width, height)

//            val adSize = LevelPlayAdSize.createAdaptiveAdSize(activity)

            val adConfig = LevelPlayBannerAdView.Config.Builder()
                .setAdSize(adSize)
                .setPlacementName("DefaultBanner")
                .build()

            val levelPlayBanner = LevelPlayBannerAdView(activity, adUnitId, adConfig)

            levelPlayBanner.bannerListener = object : LevelPlayBannerAdViewListener {
                override fun onAdLoaded(adInfo: LevelPlayAdInfo) {
                    Log.d(TAG, "onAdLoaded: adContainer")
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdLoaded",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                }

                override fun onAdLoadFailed(error: LevelPlayAdError) {
                    Log.d(TAG, "onAdLoadFailed: adContainer ${error.errorMessage}")
                    Log.d(TAG, "onAdLoadFailed: adContainer ${error.errorCode}")
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdLoadFailed",
                        error.errorMessage,
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    listener.onAdViewAdLoadFailed(
                        TapMindAdapterError(
                            TapMindAdapterError.ERROR_CODE_NO_FILL,
                            error.errorMessage
                        )
                    )
//                    loadAt(index + 1)
                }

                override fun onAdDisplayed(adInfo: LevelPlayAdInfo) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdDisplayed",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    Log.d(TAG, "onAdDisplayed: adContainer")
                }

                override fun onAdDisplayFailed(adInfo: LevelPlayAdInfo, error: LevelPlayAdError) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdDisplayFailed",
                        error.errorMessage,
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    Log.d(TAG, "onAdDisplayFailed: adContainer")
                }

                override fun onAdClicked(adInfo: LevelPlayAdInfo) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdClicked",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    Log.d(TAG, "onAdClicked: adContainer")
                }

                override fun onAdExpanded(adInfo: LevelPlayAdInfo) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdExpanded",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    Log.d(TAG, "onAdExpanded: adContainer")
                }

                override fun onAdCollapsed(adInfo: LevelPlayAdInfo) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdCollapsed",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    Log.d(TAG, "onAdCollapsed: adContainer")
                }

                override fun onAdLeftApplication(adInfo: LevelPlayAdInfo) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdLeftApplication",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    Log.d(TAG, "onAdLeftApplication: adContainer")
                }
            }
            levelPlayBanner.loadAd()
//        }
//        loadAt(0)
    }

    fun getLevelPlayBannerSizeFromApi(width: Int, height: Int): LevelPlayAdSize {
        return when {
            width == 320 && height == 50 -> LevelPlayAdSize.BANNER
            width == 320 && height == 100 -> LevelPlayAdSize.LARGE
            width == 300 && height == 250 -> LevelPlayAdSize.MEDIUM_RECTANGLE
            width == 728 && height == 90 -> LevelPlayAdSize.LEADERBOARD
            else -> {
                Log.w(
                    TAG,
                    "Unsupported banner size from API: ${width}x${height}, fallback to BANNER"
                )
                LevelPlayAdSize.BANNER
            }
        }
    }

    private fun updateMuteState(serverParameters: Bundle) {
        if (serverParameters.containsKey("is_muted")) {
            MobileAds.setAppMuted(serverParameters.getBoolean("is_muted"))
        }
    }
}