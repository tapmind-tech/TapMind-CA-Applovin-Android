package com.tapminds.admob

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterParameters
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.adapter.listener.TapMindsAdapter
import com.tapminds.adapter.listener.TapMindsAdapterInitializationParameters
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.ads.native.TapMindNativeAdAdapterListener
import com.tapminds.ads.native.TapMindNativeAdView
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.network.AdData
import com.tapminds.network.ApiUtils
import com.tapminds.network.DataItem
import com.tapminds.tapmindsads.BundleUtils
import com.tapminds.tapmindsads.TapMindAdFormat
import com.tapminds.tapmindsads.TapmindsSdkUtils
import com.tapminds.tapmindsads.TapmindsSdkUtils.dpToPx
import com.tapminds.tapmindsads.TapmindsSdkUtils.runOnUiThread
import java.util.concurrent.atomic.AtomicBoolean

class AdMobManager {

    val TAG = "APP@@@"
    val TAG1 = "AdMobManager"
    private val initialized = AtomicBoolean()
    private var status: TapMindsAdapter.InitializationStatus? = null
    private val ADAPTIVE_BANNER_TYPE_INLINE = "inline"
    private val apiUtils = ApiUtils()

    fun isInitialized(): Boolean {
        return initialized.get()
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AdMobManager? = null

        fun getInstance(): AdMobManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AdMobManager().also {
                    INSTANCE = it
                }
            }
        }

        private const val TITLE_LABEL_TAG = 1
        private const val MEDIA_VIEW_CONTAINER_TAG = 2
        private const val ICON_VIEW_TAG = 3
        private const val BODY_VIEW_TAG = 4
        private const val CALL_TO_ACTION_VIEW_TAG = 5
        private const val ADVERTISER_VIEW_TAG = 8
    }

    fun initialize(
        parameters: TapMindsAdapterInitializationParameters?,
        activity: Activity,
        onCompletionListener: TapMindsAdapter.OnCompletionListener
    ) {
//        if (initialized.compareAndSet(false, true)) {
//            val context = activity
//
//            MobileAds.disableMediationAdapterInitialization(context)
//
//            if (parameters?.getServerParameters()!!.getBoolean("init_without_callback", false)) {
//                status = TapMindsAdapter.InitializationStatus.DOES_NOT_APPLY
//                MobileAds.initialize(context)
//                onCompletionListener.onCompletion(status, null)
//            } else {
//                status = TapMindsAdapter.InitializationStatus.INITIALIZING
//
//                MobileAds.initialize(context) { initializationStatus ->
//                    val googleAdsStatus = initializationStatus.adapterStatusMap["com.google.android.gms.ads.MobileAds"]
//                    val googleAdsState = googleAdsStatus?.initializationState
//
//                    // NOTE: We were able to load ads even when SDK is in "not ready" init state...
//                    status = if (googleAdsState == AdapterStatus.State.READY) {
//                        TapMindsAdapter.InitializationStatus.INITIALIZED_SUCCESS
//                    } else {
//                        TapMindsAdapter.InitializationStatus.INITIALIZED_UNKNOWN
//                    }
//
//                    Log.d(TAG, "AdMobAdapter onCompletion")
//
//                    onCompletionListener.onCompletion(status, null)
//                }
//            }
//        } else {
//            Log.d(TAG, "AdMobAdapter failed")
//            onCompletionListener.onCompletion(status, null)
//        }
    }


    //-----------------------------------START InterstitialAd-------------------------------------//

    private var interstitialAd: InterstitialAd? = null

    fun loadInterstitialAd(
        adData: AdData,
        parameters: TapMindAdapterResponseParameters,
        adapter: DataItem,
        context: Context,
        listener: TapMindInterstitialAdapterListener,
        adapterName: String
    ) {
        Log.d(TAG, "$TAG1 : loadInterstitialAd")

//        if (adapters.isEmpty()) {
//            listener.onInterstitialAdLoadFailed(
//                TapMindAdapterError(204, "No interstitial adapters available")
//            )
//            return
//        }

//        val placementId = parameters.getThirdPartyAdPlacementId()
        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())

        Log.d(
            TAG,
            "Loading ${if (isBiddingAd) "bidding " else ""}interstitial ad: ${adData.placementId}"
        )

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
//            val adUnitId = "/6499/example/interstitial"
        val isGAM = adapter.partner.equals("GAM", ignoreCase = true)

        Log.d(
            "TapMindAdapterAdmob",
            "Interstitial Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
        )

        val adRequest =
            if (isGAM) {
                com.google.android.gms.ads.admanager.AdManagerAdRequest.Builder()
                    .addCustomTargeting(
                        "placement",
                        adData.placementId.toString()
                    ) // optional GAM targeting
                    .build()
            } else {
                createAdRequestWithParameters(
                    isBiddingAd,
                    TapMindAdFormat.INTERSTITIAL,
                    parameters,
                    context
                )
            }

        InterstitialAd.load(
            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(
                        TAG,
                        "Interstitial loaded at priority=${adapter.priority}"
                    )

                    interstitialAd = ad
                    val responseId = ad.responseInfo.responseId
                    ad.setOnPaidEventListener { adValue ->
                        val valueMicros = adValue.valueMicros
                        Log.e("TapMindAdapterAdmob", "valueMicros: $valueMicros")

//                            if (valueMicros != 0L) {
                        apiUtils.callImpressionRequestAPI(
                            adData.appId.toString(),
                            adapter.adapterId.toString(),
                            adData.placementId.toString(),
                            "Interstitial",
                            "revenue",
                            "",
                            adapter.partner.toString(),
                            "SUCCESS",
                            adData.requestId.toString(),
                            adData.versionId.toString(),
                            ad.responseInfo.responseId.toString(),
                            adapterName,
                            valueMicros
                        )
//                            }
                    }
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
                        responseId.toString(),
                        adapterName
                    )
                    interstitialAd?.fullScreenContentCallback =
                        InterstitialAdListener(
                            adData,
                            listener,
                            adapter.adapterId.toString(),
                            adapter.partner.toString(),
                            adapterName,
                            responseId
                        )

                    if (TapmindsSdkUtils.isValidString(responseId)) {
                        val extraInfo = Bundle(1)
                        extraInfo.putString("creative_id", responseId)
                        listener.onInterstitialAdLoaded(extraInfo)
                    } else {
                        listener.onInterstitialAdLoaded()
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(
                        TAG,
                        "Interstitial failed at priority=${adapter.priority}, error=${loadAdError.code}"
                    )
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Interstitial",
                        "onAdFailedToLoad",
                        loadAdError.message,
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
                            loadAdError.message
                        )
                    )
                }
            }
        )
    }

    private inner class InterstitialAdListener(
        private val adData: AdData,
        private val listener: TapMindInterstitialAdapterListener,
        private val adapterId: String,
        private val partner: String,
        private val adapterName: String,
        private val responseId: String?
    ) : FullScreenContentCallback() {

        override fun onAdShowedFullScreenContent() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Interstitial",
                "onAdShowedFullScreenContent",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            val adapterError = TapMindAdapterError(
                TapMindAdapterError.AD_DISPLAY_FAILED,
                adError.code,
                adError.message
            )
            Log.d(
                TAG,
                "$TAG1 Interstitial ad (${adData.placementId}) failed to show with error: $adapterError"
            )
            listener.onInterstitialAdDisplayFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "$TAG1 Interstitial ad impression recorded: ${adData.placementId}")
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Interstitial",
                "onAdImpression",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            listener.onInterstitialAdDisplayed()
        }

        override fun onAdClicked() {
            Log.d(TAG, "$TAG1 Interstitial ad clicked: ${adData.placementId}")
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Interstitial",
                "onAdClicked",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            listener.onInterstitialAdClicked()
        }

        override fun onAdDismissedFullScreenContent() {
            Log.d(TAG, "$TAG1 Interstitial ad hidden: ${adData.placementId}")
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Interstitial",
                "onAdDismissedFullScreenContent",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            listener.onInterstitialAdHidden()
        }
    }

    fun showInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        listener: TapMindInterstitialAdapterListener
    ) {
        val placementId = parameters.getThirdPartyAdPlacementId()
        Log.d(TAG, "$TAG1 Showing interstitial ad: $placementId...")

        if (interstitialAd != null) {
            // Ad can still be shown even if activity is null
            interstitialAd?.show(activity)
        } else {
            Log.d(TAG, "$TAG1 Interstitial ad failed to show: $placementId")

            val error = TapMindAdapterError(
                TapMindAdapterError.AD_DISPLAY_FAILED,
                TapMindAdapterError.AD_NOT_READY.getErrorCode(),
                TapMindAdapterError.AD_NOT_READY.getErrorMessage()
            )

            listener.onInterstitialAdDisplayFailed(error)
        }
    }
    //------------------------------------END  InterstitialAd-------------------------------------//


    fun onDestroy() {
        Log.d(TAG, "Destroy called for adapter $this")

//        interstitialAd?.let {
//            it.fullScreenContentCallback = null
//            interstitialAd = null
//        }
//
//        appOpenAd?.let {
//            it.fullScreenContentCallback = null
//            appOpenAd = null
//            appOpenAdListener = null
//        }
//
//        rewardedAd?.let {
//            it.fullScreenContentCallback = null
//            rewardedAd = null
//            rewardedAdListener = null
//        }

        adView?.let {
            it.destroy()
            adView = null
        }

//        nativeAd?.let {
//            it.destroy()
//            nativeAd = null
//        }
//
//        nativeAdView?.let {
//            it.destroy()
//            nativeAdView = null
//        }
    }


    //----------------------------------------START AppOpenAd-------------------------------------//

//    private var appOpenAd: AppOpenAd? = null
//    private var appOpenAdListener: AppOpenAdListener? = null
//
//    fun loadAppOpenAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        listener: MaxAppOpenAdapterListener
//    ) {
//        val placementId = parameters.thirdPartyAdPlacementId
//        val isBiddingAd = AppLovinSdkUtils.isValidString(parameters.bidResponse)
//       Log.d(TAG,"Loading ${if (isBiddingAd) "bidding " else ""}app open ad: $placementId...")
//
//        updateMuteState(parameters.serverParameters)
//
//        val context = activity
//        val adRequest = createAdRequestWithParameters(isBiddingAd, MaxAdFormat.APP_OPEN, parameters, context)
//
//        AppOpenAd.load(context, placementId, adRequest, object : AppOpenAd.AppOpenAdLoadCallback() {
//            override fun onAdLoaded(ad: AppOpenAd) {
//               Log.d(TAG,"App open ad loaded: $placementId...")
//
//                appOpenAd = ad
//                appOpenAdListener = AppOpenAdListener(placementId, listener)
//                ad.fullScreenContentCallback = appOpenAdListener
//
//                val responseInfo = appOpenAd?.responseInfo
//                val responseId = responseInfo?.responseId
//
//                if (AppLovinSdkUtils.isValidString(responseId)) {
//                    val extraInfo = Bundle(1)
//                    extraInfo.putString("creative_id", responseId)
//                    listener.onAppOpenAdLoaded(extraInfo)
//                } else {
//                    listener.onAppOpenAdLoaded()
//                }
//            }
//
//            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                val adapterError = toMaxError(loadAdError)
//               Log.d(TAG,"App open ad ($placementId) failed to load with error: $adapterError")
//                listener.onAppOpenAdLoadFailed(adapterError)
//            }
//        })
//    }
//
//    private inner class AppOpenAdListener(
//        private val placementId: String,
//        private val listener: MaxAppOpenAdapterListener
//    ) : FullScreenContentCallback() {
//
//        override fun onAdShowedFullScreenContent() {
//           Log.d(TAG,"App open ad shown: $placementId")
//        }
//
//        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//            val adapterError = MaxAdapterError(
//                MaxAdapterError.AD_DISPLAY_FAILED,
//                adError.code,
//                adError.message
//            )
//           Log.d(TAG,"App open ad ($placementId) failed to show with error: $adapterError")
//            listener.onAppOpenAdDisplayFailed(adapterError)
//        }
//
//        override fun onAdImpression() {
//           Log.d(TAG,"App open ad impression recorded: $placementId")
//            listener.onAppOpenAdDisplayed()
//        }
//
//        override fun onAdClicked() {
//           Log.d(TAG,"App open ad clicked: $placementId")
//            listener.onAppOpenAdClicked()
//        }
//
//        override fun onAdDismissedFullScreenContent() {
//           Log.d(TAG,"App open ad hidden: $placementId")
//            listener.onAppOpenAdHidden()
//        }
//    }
//
//    fun showAppOpenAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        listener: MaxAppOpenAdapterListener
//    ) {
//        val placementId = parameters.thirdPartyAdPlacementId
//       Log.d(TAG,"Showing app open ad: $placementId...")
//
//        // Shows ad with null activity properly as tested in SDK version 21.1.0
//        if (appOpenAd != null) {
//            appOpenAd?.show(activity)
//        } else {
//           Log.d(TAG,"App open ad failed to show: $placementId")
//
//            val error = MaxAdapterError(
//                MaxAdapterError.AD_DISPLAY_FAILED,
//                MaxAdapterError.AD_NOT_READY.code,
//                MaxAdapterError.AD_NOT_READY.message
//            )
//            listener.onAppOpenAdDisplayFailed(error)
//        }
//    }


    //------------------------------------------END AppOpenAd-------------------------------------//

    //---------------------------------------START RewardedAd-------------------------------------//
    private var rewardedAd: RewardedAd? = null
    private var rewardedAdListener: RewardedAdListener? = null

    fun loadRewardedAd(
        adData: AdData,
        parameters: TapMindAdapterResponseParameters,
        adapter: DataItem,
        context: Context,
        listener: TapMindRewardedAdapterListener,
        adapterName: String
    ) {
//        val placementId = parameters.getThirdPartyAdPlacementId()
        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())

        Log.d(
            TAG,
            "Loading ${if (isBiddingAd) "bidding " else ""}rewarded ad: ${adData.placementId.toString()}"
        )

//        if (adapters.isEmpty()) {
//            listener.onRewardedAdLoadFailed(
//                TapMindAdapterError(204, "No rewarded adapters available")
//            )
//            return
//        }

        updateMuteState(parameters.getServerParameters())

//        val sortedAdapters = adapters.sortedBy { it.priority }

//        fun loadAt(index: Int) {
//            if (index >= sortedAdapters.size) {
//                listener.onRewardedAdLoadFailed(
//                    TapMindAdapterError(204, "No fill from all rewarded adapters")
//                )
//                return
//            }
//
//            val adapter = sortedAdapters[index]
        val adUnitId = adapter.adUnitId.toString()
//            val adUnitId = "/6499/example/rewarded"

        val isGAM = adapter.partner.equals("GAM", ignoreCase = true)

        Log.d(
            TAG,
            "Rewarded Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
        )

        val adRequest =
            if (isGAM) {
                com.google.android.gms.ads.admanager.AdManagerAdRequest.Builder()
                    .addCustomTargeting("placement", adData.placementId.toString())
                    .build()
            } else {
                createAdRequestWithParameters(
                    isBiddingAd,
                    TapMindAdFormat.REWARDED,
                    parameters,
                    context
                )
            }

        RewardedAd.load(
            context,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(
                        TAG,
                        "Rewarded loaded at priority=${adapter.priority}"
                    )

                    rewardedAd = ad
                    ad.setOnPaidEventListener { adValue ->
                        val valueMicros = adValue.valueMicros
                        Log.e("TapMindAdapterAdmob", "valueMicros: $valueMicros")

//                            if (valueMicros != 0L) {
                        apiUtils.callImpressionRequestAPI(
                            adData.appId.toString(),
                            adapter.adapterId.toString(),
                            adData.placementId.toString(),
                            "Rewarded",
                            "revenue",
                            "",
                            adapter.partner.toString(),
                            "SUCCESS",
                            adData.requestId.toString(),
                            adData.versionId.toString(),
                            ad.responseInfo.responseId.toString(),
                            adapterName,
                            valueMicros
                        )
//                            }
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
                        ad.responseInfo.responseId.toString(),
                        adapterName
                    )
                    rewardedAdListener = RewardedAdListener(
                        listener,
                        adData,
                        adapter.adapterId.toString(),
                        adapter.partner.toString(),
                        ad.responseInfo.responseId,
                        adapterName
                    )
                    ad.fullScreenContentCallback = rewardedAdListener

                    val responseId = ad.responseInfo.responseId
                    if (TapmindsSdkUtils.isValidString(responseId)) {
                        val extraInfo = Bundle(1).apply {
                            putString("creative_id", responseId)
                        }
                        listener.onRewardedAdLoaded()
                    } else {
                        listener.onRewardedAdLoaded()
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(
                        TAG,
                        "Rewarded failed at priority=${adapter.priority}, error=${loadAdError.code}"
                    )
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Rewarded",
                        "onAdFailedToLoad",
                        loadAdError.message,
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        "",
                        adapterName
                    )
                    listener.onRewardedAdLoadFailed(
                        TapMindAdapterError(
                            TapMindAdapterError.ERROR_CODE_NO_FILL,
                            loadAdError.message
                        )
                    )
//                        loadAt(index + 1)
                }
            }
        )
//        }

//        loadAt(0)
    }

    private inner class RewardedAdListener(
        private val listener: TapMindRewardedAdapterListener,
        private val adData: AdData,
        private val adapterId: String,
        private val partner: String,
        private val responseId: String?,
        private val adapterName: String
    ) : FullScreenContentCallback() {

        var hasGrantedReward = false

        override fun onAdShowedFullScreenContent() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Rewarded",
                "onAdShowedFullScreenContent",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            Log.d(TAG, "Rewarded ad shown: ${adData.placementId.toString()}")
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            val adapterError = TapMindAdapterError(
                TapMindAdapterError.AD_DISPLAY_FAILED,
                adError.code,
                adError.message
            )
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Rewarded",
                "onAdFailedToShowFullScreenContent",
                adError.message,
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            Log.d(
                TAG,
                "Rewarded ad (${adData.placementId.toString()}) failed to show with error: $adapterError"
            )
            listener.onRewardedAdDisplayFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "Rewarded ad impression recorded: ${adData.placementId.toString()}")
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Rewarded",
                "onAdImpression",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            listener.onRewardedAdDisplayed()
        }

        override fun onAdClicked() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Rewarded",
                "onAdClicked",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            Log.d(TAG, "Rewarded ad clicked: ${adData.placementId.toString()}")
            listener.onRewardedAdClicked()
        }

        override fun onAdDismissedFullScreenContent() {
            if (hasGrantedReward) {
                val reward = getReward()
                Log.d(TAG, "Rewarded user with reward: $reward")
                listener.onUserRewarded(reward)
            }

            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Rewarded",
                "onAdDismissedFullScreenContent",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )

            Log.d(TAG, "Rewarded ad hidden: ${adData.placementId.toString()}")
            listener.onRewardedAdHidden()
        }
    }

    fun showRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        listener: TapMindRewardedAdapterListener
    ) {
        val placementId = parameters.getThirdPartyAdPlacementId()
        Log.d(TAG, "Showing rewarded ad: $placementId...")

        if (rewardedAd != null) {
            rewardedAd?.show(activity) {
                rewardType = it.type
                rewardAmount = it.amount
                Log.d(TAG, "Rewarded ad user earned reward: $placementId")
                rewardedAdListener?.hasGrantedReward = true
            }
        } else {
            Log.d(TAG, "Rewarded ad failed to show: $placementId")

            val error = TapMindAdapterError(
                TapMindAdapterError.AD_DISPLAY_FAILED,
                TapMindAdapterError.AD_NOT_READY.getCode(),
                TapMindAdapterError.AD_NOT_READY.getMessage()
            )
            listener.onRewardedAdDisplayFailed(error)
        }
    }

    private var rewardType: String? = null
    private var rewardAmount: Int? = null

    fun getAdMobAdSize(width: Int, height: Int): AdSize {
        return when {
            width == 320 && height == 50 -> AdSize.BANNER
            width == 320 && height == 100 -> AdSize.LARGE_BANNER
            width == 300 && height == 250 -> AdSize.MEDIUM_RECTANGLE
            width == 468 && height == 60 -> AdSize.FULL_BANNER
            width == 728 && height == 90 -> AdSize.LEADERBOARD
            else -> {
                Log.e(TAG, "Unsupported AdMob size: ${width}x${height}, fallback to BANNER")
                AdSize.BANNER
            }
        }
    }

    private fun getReward(): TapMindReward {
        return object : TapMindReward {
            override val label: String get() = rewardType ?: TapMindReward.DEFAULT_LABEL
            override val amount: Int get() = rewardAmount ?: TapMindReward.DEFAULT_AMOUNT
        }
    }

    private var adView: AdView? = null
    private var nativeAd: NativeAd? = null
    private var adViewListener: AdViewListener? = null

    fun loadAdViewAd(
        adData: AdData,
        adapter: DataItem,
        parameters: TapMindAdapterResponseParameters,
        adFormat: TapMindAdFormat,
        activity: Activity,
        listener: TapMindAdViewAdapterListener,
        adapterName: String,
    ) {
//        if (adapters.isEmpty()) {
//            listener.onAdViewAdLoadFailed(
//                TapMindAdapterError(204, "No banner adapters available")
//            )
//            return
//        }

//        val sortedAdapters = adapters.sortedBy { it.priority }

//        fun loadAt(index: Int) {
//            if (index >= sortedAdapters.size) {
//                listener.onAdViewAdLoadFailed(
//                    TapMindAdapterError(204, "No fill from all banner adapters")
//                )
//                return
//            }

//            val adapter = sortedAdapters[index]
        val adUnitId = adapter.adUnitId.toString()

        Log.d(
            TAG,
            "Banner Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
        )

        val width = adapter.sizes?.firstOrNull()?.width ?: 0
        val height = adapter.sizes?.firstOrNull()?.height ?: 0

        val isGAM = adapter.partner.equals("GAM", ignoreCase = true)

        val adSize = if (isGAM) {
            bannerAdSize(activity, width, height, parameters)
        } else {
            AdSize.BANNER
        }

        if (isGAM) {
            val adView = com.google.android.gms.ads.admanager.AdManagerAdView(activity).apply {
                setAdSizes(adSize)
                this.adUnitId = adUnitId
//                    this.adUnitId =
                        "/6499/example/banner" // must be GAM path like "/6499/example/banner"
            }

            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    Log.d(TAG, "GAM Banner loaded at priority=${adapter.priority}")
                    adView.setOnPaidEventListener { adValue ->
                        val valueMicros = adValue.valueMicros
                        Log.e("TapMindAdapterAdmob", "valueMicros: $valueMicros")

//                            if (valueMicros != 0L) {
                        apiUtils.callImpressionRequestAPI(
                            adData.appId.toString(),
                            adapter.adapterId.toString(),
                            adData.placementId.toString(),
                            "Banner",
                            "revenue",
                            "",
                            adapter.partner.toString(),
                            "SUCCESS",
                            adData.requestId.toString(),
                            adData.versionId.toString(),
                            adView.responseInfo?.responseId.toString(),
                            adapterName,
                            valueMicros
                        )
//                            }
                    }
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
                        adView.responseInfo?.responseId.toString(),
                        adapterName
                    )
                    adViewListener = AdViewListener(
                        adFormat,
                        listener,
                        adapter.adapterId.toString(),
                        adapter.partner.toString(),
                        adData,
                        adView.responseInfo?.responseId,
                        adapterName
                    )
                    if (adViewListener != null) {
                        adView.adListener = adViewListener!!
                    }
                    listener.onAdViewAdLoaded(adView)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.e(
                        "Ads",
                        "GAM Banner Load failed: ${error.code} - ${error.message} - ${error.domain}"
                    )
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdFailedToLoad",
                        error.message,
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        adView.responseInfo?.responseId.toString(),
                        adapterName
                    )
                    listener.onAdViewAdLoadFailed(
                        TapMindAdapterError(
                            TapMindAdapterError.ERROR_CODE_NO_FILL,
                            error.message
                        )
                    )
//                        loadAt(index + 1)
                }

                override fun onAdOpened() {
                    listener.onAdViewAdDisplayed()
                }

                override fun onAdClicked() {
                    listener.onAdViewAdClicked()
                }

                override fun onAdClosed() {
                    listener.onAdViewAdHidden()
                }
            }

            val gamRequest = com.google.android.gms.ads.admanager.AdManagerAdRequest.Builder()
                // .addCustomTargeting("key", "value") // optional
                .build()

            adView.loadAd(gamRequest)

        } else {
            val adView = AdView(activity).apply {
                setAdSize(adSize)
                this.adUnitId = adUnitId
            }

            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    Log.d(TAG, "Banner loaded at priority=${adapter.priority}")
                    adView.setOnPaidEventListener { adValue ->
                        val valueMicros = adValue.valueMicros
                        Log.e("TapMindAdapterAdmob", "valueMicros: $valueMicros")

//                            if (valueMicros != 0L) {
                        apiUtils.callImpressionRequestAPI(
                            adData.appId.toString(),
                            adapter.adapterId.toString(),
                            adData.placementId.toString(),
                            "Banner",
                            "revenue",
                            "",
                            adapter.partner.toString(),
                            "SUCCESS",
                            adData.requestId.toString(),
                            adData.versionId.toString(),
                            adView.responseInfo?.responseId.toString(),
                            adapterName,
                            valueMicros
                        )
//                            }
                    }
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
                        adView.responseInfo?.responseId.toString(),
                        adapterName
                    )
                    adViewListener = AdViewListener(
                        adFormat,
                        listener,
                        adapter.adapterId.toString(),
                        adapter.partner.toString(),
                        adData,
                        adView.responseInfo?.responseId,
                        adapterName
                    )
                    if (adViewListener != null) {
                        adView.adListener = adViewListener!!
                    }
                    listener.onAdViewAdLoaded(adView)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w(
                        TAG,
                        "Banner failed at priority=${adapter.priority}, error=${error.code}"
                    )
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "onAdFailedToLoad",
                        error.message,
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        adView.responseInfo?.responseId.toString(),
                        adapterName
                    )
                    listener.onAdViewAdLoadFailed(
                        TapMindAdapterError(
                            TapMindAdapterError.ERROR_CODE_NO_FILL,
                            error.message
                        )
                    )
//                        loadAt(index + 1)
                }

                override fun onAdOpened() {
                    listener.onAdViewAdDisplayed()
                }

                override fun onAdClicked() {
                    listener.onAdViewAdClicked()
                }

                override fun onAdClosed() {
                    listener.onAdViewAdHidden()
                }
            }

            adView.loadAd(AdRequest.Builder().build())
        }
//        }

        // 🚀 Start waterfall from priority 0
//        loadAt(0)
//        val adview = com.tapminds.tampind.ads.banner.AdView(activity)
//        adview.createView()
//        listener.onAdViewAdLoaded(adview!!)

//        Log.d(TAG, "$TAG1 : loadBannerAd")
//        adView = AdView(activity).apply {
//            setAdSize(AdSize.BANNER)
////            setAdSize(toAdSize(adFormat, isAdaptiveBanner, parameters, context))
////            adUnitId = adId // TEST ID
//            adUnitId = "test" // TEST ID
//        }
////
////
//        adView?.adListener = object : AdListener() {
//
//            override fun onAdLoaded() {
//                listener.onAdViewAdLoaded(adView!!)
//                Log.d(TAG, "$TAG1 onAdLoaded")
//            }
//
//            override fun onAdFailedToLoad(error: LoadAdError) {
//                val adapterError = toMaxError(error)
//                Log.d(
//                    TAG,
//                    "${adFormat.getLabel()} ad  failed to load with error code: $adapterError"
//                )
//                listener.onAdViewAdLoadFailed(adapterError)
//            }
//
//            override fun onAdOpened() {
//                listener.onAdViewAdDisplayed()
//            }
//
//            override fun onAdClicked() {
//                listener.onAdViewAdClicked()
//            }
//
//            override fun onAdClosed() {
//                listener.onAdViewAdHidden()
//            }
//        }
//        adView?.loadAd(AdRequest.Builder().build())


//        val placementId = parameters.getThirdPartyAdPlacementId()
//        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())
//        val isNative = parameters.getServerParameters().getBoolean("is_native",false)
//
//       Log.d(TAG, "Loading ${if (isBiddingAd) "bidding " else ""}" + "${if (isNative) "native " else ""}${adFormat.getLabel()} ad for placement id: $placementId...")
//
//        val context = activity
//        val adRequest = createAdRequestWithParameters(isBiddingAd, adFormat, parameters, context)
//
//        if (isNative) {
//            val nativeAdOptionsBuilder = NativeAdOptions.Builder()
//                .setAdChoicesPlacement(getAdChoicesPlacement(parameters))
//                .setRequestMultipleImages(adFormat == TapMindAdFormat.MREC) // MRECs can handle multiple images
//
//            // NOTE: Activity context needed on older SDKs
//            val nativeAdViewListener = NativeAdViewListener(parameters, adFormat, activity, listener)
//
//            val adLoader = AdLoader.Builder(context, placementId)
//                .withNativeAdOptions(nativeAdOptionsBuilder.build())
//                .forNativeAd(nativeAdViewListener)
//                .withAdListener(nativeAdViewListener)
//                .build()
//
//            adLoader.loadAd(adRequest)
//        } else {
//
//            Log.d(TAG,"$TAG1 : placementId "+placementId)
//
//            adView = AdView(context).apply {
//                adUnitId = placementId
//                adListener = AdViewListener(placementId, adFormat, listener)
//
//                // Check if adaptive banner sizes should be used
//                val isAdaptiveBanner = parameters.getServerParameters().getBoolean("adaptive_banner", false)
//                setAdSize(toAdSize(adFormat, isAdaptiveBanner, parameters, context))
//
//                loadAd(adRequest)
//            }
//        }
    }

//    private inner class NativeAdViewListener(
//        parameters: TapMindAdapterResponseParameters,
//        private val adFormat: TapMindAdFormat,
//        activity: Activity,
//        private val listener: TapMindAdViewAdapterListener
//    ) : AdListener(), NativeAd.OnNativeAdLoadedListener {
//
//        private val placementId: String = parameters.getThirdPartyAdPlacementId()
//        private val serverParameters: Bundle = parameters.getServerParameters()
//        private val activityRef = WeakReference(activity)
//
//        override fun onNativeAdLoaded(nativeAd: NativeAd) {
//           Log.d(TAG,"Native ${adFormat.getLabel()} ad loaded: $placementId")
//
//            this@AdMobManager.nativeAd = nativeAd
//
//            val activity = activityRef.get()
//            val context = activity
//
//            var mediaView = MediaView(context!!)
//            val mediaContent = nativeAd.mediaContent
//            mediaContent?.let { mediaView.setMediaContent(it) }
//
//            val icon = nativeAd.icon
//            val maxNativeAdImage = when {
//                icon?.drawable != null -> TapMindNativeAd.TapMindNativeAdImage(icon.drawable)
//                icon?.uri != null -> TapMindNativeAd.TapMindNativeAdImage(icon.uri!!)
//                else -> null
//            }
//
//            val maxNativeAd = TapMindNativeAd.Builder()
//                .setAdFormat(adFormat)
//                .setTitle(nativeAd.headline)
//                .setBody(nativeAd.body)
//                .setCallToAction(nativeAd.callToAction)
//                .setIcon(maxNativeAdImage)
//                .setMediaView(mediaView)
//                .build()
//
//            runOnUiThread {
//                val templateName = BundleUtils.getString("template", "", serverParameters)
//                val maxNativeAdView = TapMindNativeAdView(maxNativeAd,  context)
//
//                nativeAdView = NativeAdView(context).apply {
//                    iconView = maxNativeAdView
//                    headlineView = maxNativeAdView.titleView
//                    bodyView = maxNativeAdView.bodyView
//                    mediaView = mediaView
//                    callToActionView = maxNativeAdView.callToActionView
//                    setNativeAd(nativeAd)
//                    addView(maxNativeAdView)
//                }
//
//                val responseInfo = nativeAd.responseInfo
//                val responseId = responseInfo?.responseId
//
//                if (TapmindsSdkUtils.isValidString(responseId)) {
//                    val extraInfo = Bundle(1).apply {
//                        putString("creative_id", responseId)
//                    }
//                    listener.onAdViewAdLoaded(nativeAdView, extraInfo)
//                } else {
//                    listener.onAdViewAdLoaded(nativeAdView)
//                }
//            }
//        }
//
//        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//            val adapterError = toMaxError(loadAdError)
//           Log.d(TAG,"Native ${adFormat.getLabel()} ad ($placementId) failed to load with error: $adapterError")
//            listener.onAdViewAdLoadFailed(adapterError)
//        }
//
//        override fun onAdImpression() {
//           Log.d(TAG,"Native ${adFormat.getLabel()} ad shown")
//            listener.onAdViewAdDisplayed()
//        }
//
//        override fun onAdClicked() {
//           Log.d(TAG,"Native ${adFormat.getLabel()} ad clicked")
//            listener.onAdViewAdClicked()
//        }
//
//        override fun onAdOpened() {
//           Log.d(TAG,"Native ${adFormat.getLabel()} ad opened")
//            listener.onAdViewAdExpanded()
//        }
//
//        override fun onAdClosed() {
//           Log.d(TAG,"Native ${adFormat.getLabel()} ad closed")
//            listener.onAdViewAdCollapsed()
//        }
//    }

    fun bannerAdSize(
        context: Context,
        apiWidth: Int,
        apiHeight: Int,
        parameters: TapMindAdapterParameters
    ): AdSize {

        return if (apiWidth > 0 && apiHeight > 0) {
            AdSize(apiWidth, apiHeight)
        } else {
            val bannerWidth = getAdaptiveBannerWidth(parameters, context)

            if (isInlineAdaptiveBanner(parameters)) {
                val inlineMaxHeight = getInlineAdaptiveBannerMaxHeight(parameters)
                if (inlineMaxHeight > 0) {
                    AdSize.getInlineAdaptiveBannerAdSize(bannerWidth, inlineMaxHeight)
                } else {
                    AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, bannerWidth)
                }
            } else {
                AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, bannerWidth)
            }
        }
    }

    private inner class AdViewListener(
        private val adFormat: TapMindAdFormat,
        private val listener: TapMindAdViewAdapterListener,
        private val adapterId: String,
        private val partner: String,
        private val adData: AdData,
        private val responseId: String?,
        private val adapterName: String
    ) : AdListener() {
        override fun onAdLoaded() {
            Log.d(TAG, "${adFormat.getLabel()} ad loaded: ${adData.placementId.toString()}")

            val extraInfo = Bundle(3)

            val responseInfo = adView?.responseInfo
            val responseId = responseInfo?.responseId
            if (TapmindsSdkUtils.isValidString(responseId)) {
                extraInfo.putString("creative_id", responseId)
            }

            val adSize = adView?.adSize
            adSize?.let {
                extraInfo.putInt("ad_width", it.width)
                extraInfo.putInt("ad_height", it.height)
            }

            adView?.let { listener.onAdViewAdLoaded(it, extraInfo) }
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            val adapterError = toMaxError(loadAdError)
            Log.d(
                TAG,
                "${adFormat.getLabel()} ad (${adData.placementId}) failed to load with error code: $adapterError"
            )
            listener.onAdViewAdLoadFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "${adFormat.getLabel()} ad shown: ${adData.placementId}")
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Banner",
                "onAdImpression",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            listener.onAdViewAdDisplayed()
        }

        override fun onAdOpened() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Banner",
                "onAdOpened",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            // Do not track ad view ad opened events (besides clicks) on Android, but do so on iOS
            Log.d(TAG, "${adFormat.getLabel()} ad opened")
            listener.onAdViewAdClicked()
        }

        override fun onAdClosed() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Banner",
                "onAdClosed",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            // NOTE: Do not track ad view ad closed events on Android, but do so on iOS
            Log.d(TAG, "${adFormat.getLabel()} ad closed")
        }

        override fun onAdClicked() {
            super.onAdClicked()
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Banner",
                "onAdClicked",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
        }
    }

    private var loadedNativeAd: NativeAd? = null

    fun loadNativeAd(
        adData: AdData,
        parameters: TapMindAdapterResponseParameters,
        adapter: DataItem,
        activity: Activity,
        listener: TapMindNativeAdAdapterListener,
        adapterName: String
    ) {
//        if (adapters.isEmpty()) {
//            listener.onNativeAdLoadFailed(
//                TapMindAdapterError(204, "No native adapters available")
//            )
//            return
//        }

//        val placementId = parameters.getThirdPartyAdPlacementId()
        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())

//        val sortedAdapters = adapters.sortedBy { it.priority }

//        fun loadAt(index: Int) {
//            if (index >= sortedAdapters.size) {
//                listener.onNativeAdLoadFailed(
//                    TapMindAdapterError(204, "No fill from all native adapters")
//                )
//                return
//            }
//
//            val adapter = sortedAdapters[index]
        val adUnitId = adapter.adUnitId.toString()
//            val adUnitId = "/6499/example/native"

        val isGAM = adapter.partner.equals("GAM", ignoreCase = true)

        Log.d(
            TAG,
            "Native Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
        )

        val adRequest = if (isGAM) {
            com.google.android.gms.ads.admanager.AdManagerAdRequest.Builder()
                .addCustomTargeting("placement", adData.placementId.toString())
                .build()
        } else {
            createAdRequestWithParameters(
                isBiddingAd,
                TapMindAdFormat.NATIVE,
                parameters,
                activity
            )
        }

        val nativeAdOptionsBuilder = NativeAdOptions.Builder()
            .setAdChoicesPlacement(getAdChoicesPlacement(parameters))

        val template = BundleUtils.getString(
            "template",
            "",
            parameters.getServerParameters()
        )

        nativeAdOptionsBuilder.setRequestMultipleImages(
            template?.contains("medium") == true
        )

        val nativeAdListener = object : NativeAdListener(
            parameters,
            activity,
            listener,
            adData,
            adapter.adapterId.toString(),
            adapter.partner.toString(),
            nativeAd?.responseInfo?.responseId,
            adapterName
        ) {

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                Log.w(
                    TAG,
                    "Native failed at priority=${adapter.priority}, error=${loadAdError.code}"
                )
                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Native",
                    "onAdFailedToLoad",
                    loadAdError.message,
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    nativeAd?.responseInfo?.responseId.toString(),
                    adapterName
                )
                listener.onNativeAdLoadFailed(
                    TapMindAdapterError(
                        TapMindAdapterError.ERROR_CODE_NO_FILL,
                        loadAdError.message
                    )
                )
//                    loadAt(index + 1)
            }

            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                Log.d(TAG, "Native loaded at priority=${adapter.priority}")
                super.onNativeAdLoaded(nativeAd)
                loadedNativeAd = nativeAd

                nativeAd.setOnPaidEventListener { adValue ->
                    val valueMicros = adValue.valueMicros
                    Log.e("TapMindAdapterAdmob", "valueMicros: $valueMicros")

//                        if (valueMicros != 0L) {
                    apiUtils.callImpressionRequestAPI(
                        adData.appId.toString(),
                        adapter.adapterId.toString(),
                        adData.placementId.toString(),
                        "Banner",
                        "revenue",
                        "",
                        adapter.partner.toString(),
                        "SUCCESS",
                        adData.requestId.toString(),
                        adData.versionId.toString(),
                        nativeAd.responseInfo?.responseId.toString(),
                        adapterName,
                        valueMicros
                    )
//                        }
                }

                apiUtils.callImpressionRequestAPI(
                    adData.appId.toString(),
                    adapter.adapterId.toString(),
                    adData.placementId.toString(),
                    "Native",
                    "onNativeAdLoaded",
                    "",
                    adapter.partner.toString(),
                    "SUCCESS",
                    adData.requestId.toString(),
                    adData.versionId.toString(),
                    nativeAd.responseInfo?.responseId.toString(),
                    adapterName
                )
            }
        }

        val adLoader = AdLoader.Builder(activity, adUnitId)
            .withNativeAdOptions(nativeAdOptionsBuilder.build())
            .forNativeAd(nativeAdListener)
            .withAdListener(nativeAdListener)
            .build()

        adLoader.loadAd(adRequest)
//        }
//
//        loadAt(0)
    }

    private open inner class NativeAdListener(
        parameters: TapMindAdapterResponseParameters,
        private val context: Context,
        private val listener: TapMindNativeAdAdapterListener,
        private val adData: AdData,
        private val adapterId: String,
        private val partner: String,
        private val responseId: String?,
        private val adapterName: String
    ) : AdListener(), NativeAd.OnNativeAdLoadedListener {

        private val serverParameters: Bundle = parameters.getServerParameters()

        override fun onNativeAdLoaded(nativeAd: NativeAd) {
            Log.d(TAG, "$TAG1 Native ad loaded: ${adData.placementId.toString()}")

            this@AdMobManager.nativeAd = nativeAd

            runOnUiThread {
                try {
                    var mediaView: View? = null
                    var mainImage: Drawable? = null
                    var mediaContentAspectRatio = 0.0f
                    var hasVideoContent = false
                    var adChoicesView: View? = null
                    nativeAd.adChoicesInfo?.let { _ ->
                        val adChoicesContainer = FrameLayout(context).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                gravity = Gravity.TOP or Gravity.END
                                topMargin = dpToPx(context, 4)
                            }

                            val adChoicesIcon = TextView(context).apply {
                                text = "Ad"
                                setTextColor(Color.WHITE)
                                textSize = 10f
                                setPadding(
                                    dpToPx(context, 4), dpToPx(context, 2),
                                    dpToPx(context, 4), dpToPx(context, 2)
                                )
                                background = GradientDrawable().apply {
                                    shape = GradientDrawable.RECTANGLE
                                    cornerRadius = dpToPx(context, 2).toFloat()
                                    setColor("#CC000000".toColorInt())
                                }
                                gravity = Gravity.CENTER
                            }

                            addView(adChoicesIcon)

                            // Set click listener to handle AdChoices
//                            setOnClickListener {
//                                info.clickListener?.onClick(context)
//                            }
                        }

                        adChoicesView = adChoicesContainer
                        Log.d(TAG, "$TAG1 AdChoices container created")
                    }

                    val mediaContent = nativeAd.mediaContent
                    if (mediaContent != null) {
                        Log.d(
                            TAG,
                            "$TAG1 MediaContent available - hasVideo: ${mediaContent.hasVideoContent()}"
                        )

                        // Create MediaView with proper layout params
                        val googleMediaView = MediaView(context).apply {
                            // Set layout params to ensure it's measurable
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }

                        // ✅ CRITICAL FIX: Use setMediaContent() method
                        googleMediaView.mediaContent = mediaContent

                        mediaView = googleMediaView
                        mainImage = mediaContent.mainImage
                        mediaContentAspectRatio = mediaContent.aspectRatio
                        hasVideoContent = mediaContent.hasVideoContent()

                        Log.d(TAG, "$TAG1 MediaView created successfully")
                        Log.d(TAG, "$TAG1 - Aspect ratio: $mediaContentAspectRatio")
                        Log.d(TAG, "$TAG1 - Has video: $hasVideoContent")

                    } else {
                        Log.d(TAG, "$TAG1 No mediaContent, checking images")

                        // Fallback to static images
                        val images = nativeAd.images
                        if (images.isNotEmpty()) {
                            val mediaImage = images[0]
                            val mediaDrawable = mediaImage.drawable

                            if (mediaDrawable != null) {
                                val mediaImageView = ImageView(context).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                    )
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageDrawable(mediaDrawable)
                                }

                                mediaView = mediaImageView
                                mainImage = mediaDrawable
                                mediaContentAspectRatio =
                                    mediaDrawable.intrinsicWidth.toFloat() / mediaDrawable.intrinsicHeight.toFloat()

                                Log.d(TAG, "$TAG1 Using image fallback")
                            }
                        }
                    }

                    // Handle icon
                    val icon = nativeAd.icon
                    val iconImage = when {
                        icon?.drawable != null -> {
                            Log.d(TAG, "$TAG1 Icon drawable available")
                            TapMindNativeAd.TapMindNativeAdImage(icon.drawable!!)
                        }

                        icon?.uri != null -> {
                            Log.d(TAG, "$TAG1 Icon URI available")
                            TapMindNativeAd.TapMindNativeAdImage(icon.uri!!)
                        }

                        else -> {
                            Log.d(TAG, "$TAG1 No icon available")
                            null
                        }
                    }

                    // ✅ Build TapMindNativeAd with all required fields
                    val builder = TapMindNativeAd.Builder()
                        .setAdFormat(TapMindAdFormat.NATIVE)
                        .setHasVideoContent(hasVideoContent)

                    // Set all available fields

                    if (adChoicesView != null) {
                        builder.setAdChoise(adChoicesView)
                        Log.d(TAG, "$TAG1 AdChoices view set in builder")
                    }

                    nativeAd.headline?.let {
                        builder.setTitle(it)
                        Log.d(TAG, "$TAG1 Headline: $it")
                    }
                    nativeAd.advertiser?.let {
                        builder.setAdvertiser(it)
                        Log.d(TAG, "$TAG1 Advertiser: $it")
                    }
                    nativeAd.body?.let {
                        builder.setBody(it)
                        Log.d(TAG, "$TAG1 Body: ${it.take(50)}...")
                    }
                    nativeAd.callToAction?.let {
                        builder.setCallToAction(it)
                        Log.d(TAG, "$TAG1 CTA: $it")
                    }
                    nativeAd.starRating?.let {
                        builder.setStarRating(it)
                        Log.d(TAG, "$TAG1 Rating: $it")
                    }
                    nativeAd.store?.let {
                        builder.setStore(it)
                        Log.d(TAG, "$TAG1 Store: $it")
                    }
                    nativeAd.price?.let {
                        builder.setPrice(it)
                        Log.d(TAG, "$TAG1 Price: $it")
                    }

                    // Set icon if available
                    iconImage?.let { builder.setIcon(it) }

                    // ✅ CRITICAL: Set media-related fields
//                    if (mediaView != null) {
//                        builder.setMediaView(mediaView)
//                        Log.d(TAG, "$TAG1 MediaView set in builder")
//                    } else {
//                        Log.w(TAG, "$TAG1 No MediaView to set")
//                    }

                    if (mainImage != null) {
                        builder.setMainImage(TapMindNativeAd.TapMindNativeAdImage(mainImage))
                        Log.d(TAG, "$TAG1 MainImage set in builder")
                    } else {
                        Log.w(TAG, "$TAG1 No MainImage to set")
                    }

                    if (mediaContentAspectRatio > 0) {
                        builder.setMediaContentAspectRatio(mediaContentAspectRatio)
                        Log.d(TAG, "$TAG1 Aspect ratio set: $mediaContentAspectRatio")
                    }

                    // Build the final ad
                    val tapMindNativeAd = TapMindGoogleNativeAd(builder)
                    Log.d(TAG, "$TAG1 TapMindGoogleNativeAd created")

                    val responseInfo = nativeAd.responseInfo
                    val responseId = responseInfo?.responseId
                    val extraInfo = Bundle().apply {
                        putString("creative_id", responseId)
                    }

                    Log.d(TAG, "$TAG1 Calling listener.onNativeAdLoaded")
                    listener.onNativeAdLoaded(tapMindNativeAd, extraInfo)

                } catch (e: Exception) {
                    Log.e(TAG, "$TAG1 Error in onNativeAdLoaded: ${e.message}", e)
                    listener.onNativeAdLoadFailed(
                        TapMindAdapterError.UNSPECIFIED.apply {
                            // Set error message if your TapMindAdapterError supports it
                        }
                    )
                }
            }
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            Log.d(TAG, "$TAG1 onAdFailedToLoad ${loadAdError.message}")
            val adapterError = toMaxError(loadAdError)
            listener.onNativeAdLoadFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "$TAG1 Native ad shown")
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Native",
                "onAdImpression",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                loadedNativeAd?.responseInfo?.responseId.toString(),
                adapterName
            )
            listener.onNativeAdDisplayed(null)
        }

        override fun onAdClicked() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Native",
                "onAdClicked",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            Log.d(TAG, "$TAG1 Native ad clicked")
            listener.onNativeAdClicked()
        }

        override fun onAdOpened() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Native",
                "onAdOpened",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            Log.d(TAG, "$TAG1 Native ad opened")
        }

        override fun onAdClosed() {
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Native",
                "onAdClosed",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
            Log.d(TAG, "$TAG1 Native ad closed")
        }

        override fun onAdSwipeGestureClicked() {
            super.onAdSwipeGestureClicked()
            apiUtils.callImpressionRequestAPI(
                adData.appId.toString(),
                adapterId,
                adData.placementId.toString(),
                "Native",
                "onAdSwipeGestureClicked",
                "",
                partner,
                "SUCCESS",
                adData.requestId.toString(),
                adData.versionId.toString(),
                responseId.toString(),
                adapterName
            )
        }
    }

    private inner class TapMindGoogleNativeAd(builder: Builder) : TapMindNativeAd(builder) {
        fun getMediaViewInternal(): View? = mediaView
        override fun prepareForInteraction(
            views: List<View>,
            container: ViewGroup
        ): Boolean {
            Log.d(TAG, "$TAG1 prepareForInteraction - container: ${container.javaClass.simpleName}")

            val nativeAd = this@AdMobManager.nativeAd
            if (nativeAd == null) {
                Log.e(TAG, "$TAG1 Failed to register: native ad is null")
                return false
            }

            try {
                // Create NativeAdView
                val nativeAdView = NativeAdView(container.context)
                Log.d(TAG, "$TAG1 NativeAdView created")

                if (container is TapMindNativeAdView) {
                    Log.d(TAG, "$TAG1 Container is TapMindNativeAdView")

                    val mainView = container.mainView
                    container.removeView(mainView)
                    nativeAdView.addView(mainView)
                    container.addView(nativeAdView)

                    // Register all asset views
                    nativeAdView.iconView = container.iconView
                    nativeAdView.headlineView = container.titleView
                    nativeAdView.advertiserView = container.advertiserView
                    nativeAdView.bodyView = container.bodyView
                    nativeAdView.callToActionView = container.callToActionView

                    // ✅ CRITICAL: Handle MediaView properly
                    val mediaView = getMediaViewInternal()
                    if (mediaView != null) {
                        Log.d(TAG, "$TAG1 Setting MediaView: ${mediaView.javaClass.simpleName}")

                        when (mediaView) {
                            is MediaView -> {
                                // Ensure media content is set
                                nativeAd.mediaContent?.let { content ->
                                    mediaView.mediaContent = content
                                    Log.d(TAG, "$TAG1 MediaContent set on MediaView")
                                }
                                nativeAdView.mediaView = mediaView
                            }

                            is ImageView -> {
                                nativeAdView.imageView = mediaView
                            }
                        }
                    } else {
                        Log.w(TAG, "$TAG1 No MediaView available")
                    }

                    // ✅ CRITICAL: Set native ad LAST
                    nativeAdView.setNativeAd(nativeAd)
                    Log.d(TAG, "$TAG1 setNativeAd called successfully")

                } else {
                    Log.d(TAG, "$TAG1 Container is standard ViewGroup")

                    var mediaView: View? = null
                    var mediaViewContainer: ViewGroup? = null

                    // Map all clickable views
                    for (view in views) {
                        val viewTag = view.tag ?: continue
                        val tag = viewTag as? Int ?: continue

                        when (tag) {
                            TITLE_LABEL_TAG -> {
                                nativeAdView.headlineView = view
                                Log.d(TAG, "$TAG1 Headline view registered")
                            }

                            ICON_VIEW_TAG -> {
                                nativeAdView.iconView = view
                                Log.d(TAG, "$TAG1 Icon view registered")
                            }

                            BODY_VIEW_TAG -> {
                                nativeAdView.bodyView = view
                                Log.d(TAG, "$TAG1 Body view registered")
                            }

                            CALL_TO_ACTION_VIEW_TAG -> {
                                nativeAdView.callToActionView = view
                                Log.d(TAG, "$TAG1 CTA view registered")
                            }

                            ADVERTISER_VIEW_TAG -> {
                                nativeAdView.advertiserView = view
                                Log.d(TAG, "$TAG1 Advertiser view registered")
                            }

                            MEDIA_VIEW_CONTAINER_TAG -> {
                                mediaView = getMediaViewInternal()
                                mediaViewContainer = view as? ViewGroup
                                Log.d(TAG, "$TAG1 Media container found")
                            }
                        }
                    }

                    val pluginContainer = mediaViewContainer ?: container
                    val hasPluginLayout =
                        pluginContainer is RelativeLayout || pluginContainer is FrameLayout

                    if (mediaView != null) {
                        Log.d(TAG, "$TAG1 Processing MediaView")

                        // Remove from parent if attached
                        (mediaView.parent as? ViewGroup)?.removeView(mediaView)

                        // Handle MediaView
                        if (mediaView is MediaView) {
                            // ✅ CRITICAL: Ensure media content is set
                            nativeAd.mediaContent?.let { content ->
                                mediaView.mediaContent = content
                                Log.d(TAG, "$TAG1 MediaContent set on MediaView")
                            }
                        }

                        // Add to NativeAdView
                        val mediaViewLayout = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        nativeAdView.addView(mediaView, mediaViewLayout)

                        // Register with NativeAdView
                        when (mediaView) {
                            is MediaView -> nativeAdView.mediaView = mediaView
                            is ImageView -> nativeAdView.imageView = mediaView
                        }
                    }

                    // ✅ CRITICAL: Set native ad LAST
                    nativeAdView.setNativeAd(nativeAd)
                    Log.d(TAG, "$TAG1 setNativeAd called successfully")

                    // Add NativeAdView to container
                    if (hasPluginLayout) {
                        val nativeAdViewLayout = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        pluginContainer.addView(nativeAdView, nativeAdViewLayout)
                    } else {
                        nativeAdView.measure(
                            View.MeasureSpec.makeMeasureSpec(
                                pluginContainer.width,
                                View.MeasureSpec.EXACTLY
                            ),
                            View.MeasureSpec.makeMeasureSpec(
                                pluginContainer.height,
                                View.MeasureSpec.EXACTLY
                            )
                        )
                        nativeAdView.layout(0, 0, pluginContainer.width, pluginContainer.height)
                        pluginContainer.addView(nativeAdView)
                    }
                }

                Log.d(TAG, "$TAG1 prepareForInteraction completed successfully")
                return true

            } catch (e: Exception) {
                Log.e(TAG, "$TAG1 Error in prepareForInteraction: ${e.message}", e)
                return false
            }
        }
    }


    //-----------------------------------------END AdView-----------------------------------------//


    //------------------------------------- extra methods-----------------------------------------//


    private fun createAdRequestWithParameters(
        isBiddingAd: Boolean,
        adFormat: TapMindAdFormat,
        parameters: TapMindAdapterParameters,
        context: Context
    ): AdRequest {
        val requestBuilder = AdRequest.Builder()
        val serverParameters = parameters.getServerParameters()
        val networkExtras = Bundle(6)
        var isDv360Bidding = false

        if (isBiddingAd) {
            val bidderType = BundleUtils.getString("bidder", "", serverParameters)
            if (bidderType.equals("dv360", ignoreCase = true)) {
                isDv360Bidding = true
            }

            // Requested by Google for signal collection
            networkExtras.putString(
                "query_info_type",
                if (isDv360Bidding) "requester_type_3" else "requester_type_2"
            )

            if (adFormat.isAdViewAd()) {
                val isAdaptiveBannerObj = parameters.getLocalExtraParameters()["adaptive_banner"]
                if (isAdaptiveBannerObj is String && isAdaptiveBannerObj.equals(
                        "true",
                        ignoreCase = true
                    )
                ) {
                    val adaptiveAdSize = toAdSize(adFormat, true, parameters, context)
                    if (isInlineAdaptiveBanner(parameters)) {
                        var adaptiveAdSizeHeight = getInlineAdaptiveBannerMaxHeight(parameters)
                        if (adaptiveAdSizeHeight <= 0) {
                            adaptiveAdSizeHeight = adaptiveAdSize.height
                        }

                        networkExtras.putInt("inlined_adaptive_banner_w", adaptiveAdSize.width)
                        networkExtras.putInt("inlined_adaptive_banner_h", adaptiveAdSizeHeight)
                    } else {
                        networkExtras.putInt("adaptive_banner_w", adaptiveAdSize.width)
                        networkExtras.putInt("adaptive_banner_h", adaptiveAdSize.height)
                    }
                }
            }

            if (parameters is TapMindAdapterResponseParameters) {
                val bidResponse = parameters.getBidResponse()
                if (TapmindsSdkUtils.isValidString(bidResponse)) {
                    requestBuilder.setAdString(bidResponse)
                }
            }
        }

        // Use "applovin" instead of mediationTag for Google's specs
        requestBuilder.setRequestAgent(if (isDv360Bidding) "applovin_dv360" else "applovin")

        // Use event id as AdMob's placement request id
        val eventId = BundleUtils.getString("event_id", bundle = serverParameters)
        if (TapmindsSdkUtils.isValidString(eventId)) {
            networkExtras.putString("placement_req_id", eventId)
        }

        parameters.hasUserConsent()?.let { hasConsent ->
            if (!hasConsent) {
                networkExtras.putString("npa", "1") // Non-personalized ads
            }
        }

        parameters.isDoNotSell()?.let { doNotSell ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
            if (doNotSell) {
                networkExtras.putInt("rdp", 1)
                prefs.putInt("gad_rdp", 1)
            } else {
                prefs.remove("gad_rdp")
            }
            prefs.apply()
        }

        val localExtraParameters = parameters.getLocalExtraParameters()

        (localExtraParameters["google_max_ad_content_rating"] as? String)?.let {
            networkExtras.putString("max_ad_content_rating", it)
        }

        (localExtraParameters["google_content_url"] as? String)?.let {
            requestBuilder.setContentUrl(it)
        }

        (localExtraParameters["google_neighbouring_content_url_strings"] as? List<*>)?.let {
            try {
                @Suppress("UNCHECKED_CAST")
                requestBuilder.setNeighboringContentUrls(it as List<String>)
            } catch (th: Throwable) {
                Log.d(
                    TAG,
                    "Neighbouring content URL strings extra param needs to be of type List<String>.",
                    th
                )
            }
        }

        requestBuilder.addNetworkExtrasBundle(AdMobAdapter::class.java, networkExtras)

        return requestBuilder.build()
    }


    private fun toAdSize(
        adFormat: TapMindAdFormat,
        isAdaptiveBanner: Boolean,
        parameters: TapMindAdapterParameters,
        context: Context
    ): AdSize {
        if (isAdaptiveBanner && isAdaptiveAdFormat(adFormat, parameters)) {
            return getAdaptiveAdSize(parameters, context)
        }

        return if (adFormat === TapMindAdFormat.BANNER) {
            AdSize.BANNER
        } else if (adFormat === TapMindAdFormat.LEADER) {
            AdSize.LEADERBOARD
        } else if (adFormat === TapMindAdFormat.MREC) {
            AdSize.MEDIUM_RECTANGLE
        } else {
            throw java.lang.IllegalArgumentException("Unsupported ad format: $adFormat")
        }
    }

    private fun isAdaptiveAdFormat(
        adFormat: TapMindAdFormat,
        parameters: TapMindAdapterParameters
    ): Boolean {
        // Adaptive banners must be inline for MRECs
        val isInlineAdaptiveMRec =
            (adFormat === TapMindAdFormat.MREC) && isInlineAdaptiveBanner(parameters)
        return isInlineAdaptiveMRec || adFormat === TapMindAdFormat.BANNER || adFormat === TapMindAdFormat.LEADER
    }

    private fun isInlineAdaptiveBanner(parameters: TapMindAdapterParameters): Boolean {
        val localExtraParameters = parameters.getLocalExtraParameters()
        val adaptiveBannerType = localExtraParameters["adaptive_banner_type"]
        return (adaptiveBannerType is String) && ADAPTIVE_BANNER_TYPE_INLINE.equals(
            adaptiveBannerType as String?,
            true
        )
    }

    private fun getAdaptiveAdSize(parameters: TapMindAdapterParameters, context: Context): AdSize {
        val bannerWidth: Int = getAdaptiveBannerWidth(parameters, context)

        if (isInlineAdaptiveBanner(parameters)) {
            val inlineMaxHeight: Int = getInlineAdaptiveBannerMaxHeight(parameters)
            if (inlineMaxHeight > 0) {
                return AdSize.getInlineAdaptiveBannerAdSize(bannerWidth, inlineMaxHeight)
            }

            return AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, bannerWidth)
        }

        // Return anchored size by default
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, bannerWidth)
    }

    private fun getInlineAdaptiveBannerMaxHeight(parameters: TapMindAdapterParameters): Int {
        val localExtraParameters = parameters.getLocalExtraParameters()
        val inlineMaxHeight = localExtraParameters["inline_adaptive_banner_max_height"]
        return inlineMaxHeight as? Int ?: 0
    }

    private fun getAdaptiveBannerWidth(
        parameters: TapMindAdapterParameters,
        context: Context
    ): Int {
        val localExtraParameters = parameters.getLocalExtraParameters()
        val widthObj = localExtraParameters["adaptive_banner_width"]
        if (widthObj is Int) {
            return widthObj
        } else if (widthObj != null) {
            Log.d(
                TAG,
                "Expected parameter \"adaptive_banner_width\" to be of type Integer, received: " + widthObj.javaClass
            )
        }

        val deviceWidthPx: Int = getApplicationWindowWidth(context)
        return TapmindsSdkUtils.pxToDp(context, deviceWidthPx)
    }

    fun getApplicationWindowWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    private fun toMaxError(googleAdsError: AdError): TapMindAdapterError {
        val googleErrorCode = googleAdsError.code
        val adapterError = when (googleErrorCode) {
            AdRequest.ERROR_CODE_NO_FILL,
            AdRequest.ERROR_CODE_MEDIATION_NO_FILL -> TapMindAdapterError.NO_FILL

            AdRequest.ERROR_CODE_NETWORK_ERROR -> TapMindAdapterError.NO_CONNECTION

            AdRequest.ERROR_CODE_INTERNAL_ERROR -> TapMindAdapterError.INTERNAL_ERROR

            AdRequest.ERROR_CODE_INVALID_REQUEST,
            AdRequest.ERROR_CODE_REQUEST_ID_MISMATCH -> TapMindAdapterError.BAD_REQUEST

            AdRequest.ERROR_CODE_APP_ID_MISSING,
            AdRequest.ERROR_CODE_INVALID_AD_STRING -> TapMindAdapterError.INVALID_CONFIGURATION

            else -> TapMindAdapterError.UNSPECIFIED
        }

        return TapMindAdapterError(adapterError, googleErrorCode, googleAdsError.message)
    }

    /**
     * Update the global mute state for AdMob - must be done _before_ ad load
     * to restrict inventory which requires playing with volume.
     */
    private fun updateMuteState(serverParameters: Bundle) {
        if (serverParameters.containsKey("is_muted")) {
            MobileAds.setAppMuted(serverParameters.getBoolean("is_muted"))
        }
    }

    private fun getAdChoicesPlacement(parameters: TapMindAdapterResponseParameters): Int {
        // Publishers can set via nativeAdLoader.setLocalExtraParameter("admob_ad_choices_placement", ADCHOICES_BOTTOM_LEFT)
//        val localExtraParams = parameters.getLocalExtraParameters()
//        val adChoicesPlacementObj = localExtraParams?.get("admob_ad_choices_placement")
//
//        return if (isValidAdChoicesPlacement(adChoicesPlacementObj)) {
//            adChoicesPlacementObj as Int
//        } else {
        return NativeAdOptions.ADCHOICES_TOP_RIGHT
//        }
    }

    private fun isValidAdChoicesPlacement(placementObj: Any?): Boolean {
        return placementObj is Int && (
                placementObj == NativeAdOptions.ADCHOICES_TOP_LEFT ||
                        placementObj == NativeAdOptions.ADCHOICES_TOP_RIGHT ||
                        placementObj == NativeAdOptions.ADCHOICES_BOTTOM_LEFT ||
                        placementObj == NativeAdOptions.ADCHOICES_BOTTOM_RIGHT
                )
    }

    private class AutoMeasuringMediaView(context: Context) : MediaView(context) {

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            requestLayout()
        }

        override fun requestLayout() {
            super.requestLayout()
            post {
                measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                )
                layout(left, top, right, bottom)
            }
        }
    }
}