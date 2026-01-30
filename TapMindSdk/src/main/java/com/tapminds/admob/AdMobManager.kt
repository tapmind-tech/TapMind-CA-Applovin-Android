package com.tapminds.admob

//import com.applovin.sdk.AppLovinSdkUtils.runOnUiThread
//import com.applovin.impl.sdk.utils.BundleUtils
//import com.applovin.mediation.MaxAdFormat
//import com.applovin.mediation.MaxReward
//import com.applovin.mediation.adapter.MaxAdapter
//import com.applovin.mediation.adapter.MaxAdapterError
//import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxAppOpenAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxRewardedAdapterListener
//import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
//import com.applovin.mediation.adapter.parameters.MaxAdapterParameters
//import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
//import com.applovin.mediation.nativeAds.MaxNativeAd
//import com.applovin.mediation.nativeAds.MaxNativeAdView
//import com.applovin.sdk.AppLovinSdkUtils
//import com.applovin.sdk.AppLovinSdkUtils.runOnUiThread
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
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
import com.tapminds.network.APInterface
import com.tapminds.network.ApiClient
import com.tapminds.network.DataItem
import com.tapminds.network.ImpressionRequest
import com.tapminds.network.ImpressionResponse
import com.tapminds.tapmindsads.BundleUtils
import com.tapminds.tapmindsads.TapMindAdFormat
import com.tapminds.tapmindsads.TapmindsSdkUtils
import com.tapminds.tapmindsads.TapmindsSdkUtils.runOnUiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

class AdMobManager {

    val TAG = "APP@@@"
    val TAG1 = "AdMobManager"
    private val initialized = AtomicBoolean()
    private var status: TapMindsAdapter.InitializationStatus? = null
    private val ADAPTIVE_BANNER_TYPE_INLINE = "inline"


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

        private val TITLE_LABEL_TAG = 1
        private val MEDIA_VIEW_CONTAINER_TAG = 2
        private val ICON_VIEW_TAG = 3
        private val BODY_VIEW_TAG = 4
        private val CALL_TO_ACTION_VIEW_TAG = 5
        private val ADVERTISER_VIEW_TAG = 8
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
        appId: String,
        placementID: String,
        parameters: TapMindAdapterResponseParameters,
        adapters: List<DataItem>,
        context: Context,
        listener: TapMindInterstitialAdapterListener
    ) {
        Log.d(TAG, "$TAG1 : loadInterstitialAd")

        if (adapters.isEmpty()) {
            listener.onInterstitialAdLoadFailed(
                TapMindAdapterError(204, "No interstitial adapters available")
            )
            return
        }

//        val placementId = parameters.getThirdPartyAdPlacementId()
        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())

        Log.d(
            TAG,
            "Loading ${if (isBiddingAd) "bidding " else ""}interstitial ad: $placementID"
        )

        updateMuteState(parameters.getServerParameters())

        val sortedAdapters = adapters.sortedBy { it.priority }

        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onInterstitialAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all interstitial adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()

            Log.d(
                "TapMindAdapterAdmob",
                "Interstitial Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
            )

            val adRequest = createAdRequestWithParameters(
                isBiddingAd,
                TapMindAdFormat.INTERSTITIAL,
                parameters,
                context
            )

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
                        interstitialAd?.fullScreenContentCallback =
                            InterstitialAdListener(
                                placementID,
                                listener,
                                appId,
                                adapter.adapterId.toString(),
                                adapter.partner.toString()
                            )

                        val responseId = ad.responseInfo.responseId
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
                        loadAt(index + 1)
                    }
                }
            )
        }

        // 🚀 Start waterfall
        loadAt(0)
    }

    private inner class InterstitialAdListener(
        private val placementId: String,
        private val listener: TapMindInterstitialAdapterListener,
        private val appId: String,
        private val adapterId: String,
        private val partner: String
    ) : FullScreenContentCallback() {

        override fun onAdShowedFullScreenContent() {
            Log.d(TAG, "$TAG1 Interstitial ad shown: $placementId")
            callImpressionRequestAPI(
                appId,
                adapterId,
                placementId,
                "Interstitial",
                partner,
                "SUCCESS"
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
                "$TAG1 Interstitial ad ($placementId) failed to show with error: $adapterError"
            )
            listener.onInterstitialAdDisplayFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "$TAG1 Interstitial ad impression recorded: $placementId")
            listener.onInterstitialAdDisplayed()
        }

        override fun onAdClicked() {
            Log.d(TAG, "$TAG1 Interstitial ad clicked: $placementId")
            listener.onInterstitialAdClicked()
        }

        override fun onAdDismissedFullScreenContent() {
            Log.d(TAG, "$TAG1 Interstitial ad hidden: $placementId")
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
        appId: String,
        placementId: String,
        parameters: TapMindAdapterResponseParameters,
        adapters: List<DataItem>,
        context: Context,
        listener: TapMindRewardedAdapterListener
    ) {
//        val placementId = parameters.getThirdPartyAdPlacementId()
        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())

        Log.d(
            TAG,
            "Loading ${if (isBiddingAd) "bidding " else ""}rewarded ad: $placementId"
        )

        if (adapters.isEmpty()) {
            listener.onRewardedAdLoadFailed(
                TapMindAdapterError(204, "No rewarded adapters available")
            )
            return
        }

        updateMuteState(parameters.getServerParameters())

        val sortedAdapters = adapters.sortedBy { it.priority }

        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onRewardedAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all rewarded adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()

            Log.d(
                TAG,
                "Rewarded Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
            )

            val adRequest = createAdRequestWithParameters(
                isBiddingAd,
                TapMindAdFormat.REWARDED,
                parameters,
                context
            )

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
                        rewardedAdListener = RewardedAdListener(
                            placementId,
                            listener,
                            appId,
                            adapter.adapterId.toString(),
                            adapter.partner.toString()
                        )
                        ad.fullScreenContentCallback = rewardedAdListener

                        val responseId = ad.responseInfo.responseId
                        if (TapmindsSdkUtils.isValidString(responseId)) {
                            val extraInfo = Bundle(1).apply {
                                putString("creative_id", responseId)
                            }
                            listener.onRewardedAdLoaded(extraInfo)
                        } else {
                            listener.onRewardedAdLoaded()
                        }
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(
                            TAG,
                            "Rewarded failed at priority=${adapter.priority}, error=${loadAdError.code}"
                        )
                        loadAt(index + 1)
                    }
                }
            )
        }

        loadAt(0)
    }

    private inner class RewardedAdListener(
        private val placementId: String,
        private val listener: TapMindRewardedAdapterListener,
        private val appId: String,
        private val adapterId: String,
        private val partner: String
    ) : FullScreenContentCallback() {

        var hasGrantedReward = false

        override fun onAdShowedFullScreenContent() {
            Log.d(TAG, "Rewarded ad shown: $placementId")
            callImpressionRequestAPI(appId, adapterId, placementId, "Rewarded", partner, "SUCCESS")
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            val adapterError = TapMindAdapterError(
                TapMindAdapterError.AD_DISPLAY_FAILED,
                adError.code,
                adError.message
            )
            Log.d(TAG, "Rewarded ad ($placementId) failed to show with error: $adapterError")
            listener.onRewardedAdDisplayFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "Rewarded ad impression recorded: $placementId")

            listener.onRewardedAdDisplayed()
        }

        override fun onAdClicked() {
            Log.d(TAG, "Rewarded ad clicked: $placementId")
            listener.onRewardedAdClicked()
        }

        override fun onAdDismissedFullScreenContent() {
            if (hasGrantedReward) {
                val reward = getReward()
                Log.d(TAG, "Rewarded user with reward: $reward")
                listener.onUserRewarded(reward)
            }

            Log.d(TAG, "Rewarded ad hidden: $placementId")
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
//            configureReward(parameters)

            // Tested that ad still successfully shows with a `null` Activity
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

    private fun getReward(): TapMindReward {
        return object : TapMindReward {
            override val label: String get() = rewardType ?: TapMindReward.DEFAULT_LABEL
            override val amount: Int get() = rewardAmount ?: TapMindReward.DEFAULT_AMOUNT
        }
    }


    //-----------------------------------------END RewardedAd-------------------------------------//

    //-----------------------------------------START AdView---------------------------------------//

    private var adView: AdView? = null
    private var nativeAd: NativeAd? = null
    private var nativeAdView: NativeAdView? = null


    fun loadAdViewAd(
        appId: String,
        placementId: String,
        adapters: List<DataItem>,
        parameters: TapMindAdapterResponseParameters,
        adFormat: TapMindAdFormat,
        activity: Activity,
        listener: TapMindAdViewAdapterListener
    ) {
        if (adapters.isEmpty()) {
            listener.onAdViewAdLoadFailed(
                TapMindAdapterError(204, "No banner adapters available")
            )
            return
        }

        val sortedAdapters = adapters.sortedBy { it.priority }

        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onAdViewAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all banner adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()

            Log.d(
                TAG,
                "Banner Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
            )

            val adView = AdView(activity).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = adUnitId
            }

            adView.adListener = object : AdListener() {

                override fun onAdLoaded() {
                    Log.d(TAG, "Banner loaded at priority=${adapter.priority}")
                    listener.onAdViewAdLoaded(adView)
                    callImpressionRequestAPI(
                        appId,
                        adapter.adapterId.toString(),
                        placementId,
                        "Banner",
                        adapter.partner.toString(),
                        "SUCCESS"
                    )
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w(
                        TAG,
                        "Banner failed at priority=${adapter.priority}, error=${error.code}"
                    )
                    loadAt(index + 1)
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

        // 🚀 Start waterfall from priority 0
        loadAt(0)
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

    private inner class AdViewListener(
        private val placementId: String,
        private val adFormat: TapMindAdFormat,
        private val listener: TapMindAdViewAdapterListener
    ) : AdListener() {

        override fun onAdLoaded() {
            Log.d(TAG, "${adFormat.getLabel()} ad loaded: $placementId")

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
                "${adFormat.getLabel()} ad ($placementId) failed to load with error code: $adapterError"
            )
            listener.onAdViewAdLoadFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "${adFormat.getLabel()} ad shown: $placementId")
            listener.onAdViewAdDisplayed()
        }

        override fun onAdOpened() {
            // Do not track ad view ad opened events (besides clicks) on Android, but do so on iOS
            Log.d(TAG, "${adFormat.getLabel()} ad opened")
            listener.onAdViewAdClicked()
        }

        override fun onAdClosed() {
            // NOTE: Do not track ad view ad closed events on Android, but do so on iOS
            Log.d(TAG, "${adFormat.getLabel()} ad closed")
        }
    }

    fun loadNativeAd(
        appId: String,
        placementID: String,
        parameters: TapMindAdapterResponseParameters,
        adapters: List<DataItem>,
        activity: Activity,
        listener: TapMindNativeAdAdapterListener
    ) {
        if (adapters.isEmpty()) {
            listener.onNativeAdLoadFailed(
                TapMindAdapterError(204, "No native adapters available")
            )
            return
        }

//        val placementId = parameters.getThirdPartyAdPlacementId()
        val isBiddingAd = TapmindsSdkUtils.isValidString(parameters.getBidResponse())

        val context = activity
        val sortedAdapters = adapters.sortedBy { it.priority }

        fun loadAt(index: Int) {
            if (index >= sortedAdapters.size) {
                listener.onNativeAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all native adapters")
                )
                return
            }

            val adapter = sortedAdapters[index]
            val adUnitId = adapter.adUnitId.toString()

            Log.d(
                TAG,
                "Native Waterfall → priority=${adapter.priority}, partner=${adapter.partner}, adUnitId=$adUnitId"
            )

            val adRequest = createAdRequestWithParameters(
                isBiddingAd,
                TapMindAdFormat.NATIVE,
                parameters,
                context
            )

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
                context,
                listener,
                appId,
                placementID,
                adapter.adapterId.toString(),
                adapter.partner.toString()
            ) {

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(
                        TAG,
                        "Native failed at priority=${adapter.priority}, error=${loadAdError.code}"
                    )
                    loadAt(index + 1)
                }

                override fun onNativeAdLoaded(nativeAd: NativeAd) {
                    Log.d(TAG, "Native loaded at priority=${adapter.priority}")
                    super.onNativeAdLoaded(nativeAd)
                }
            }

            val adLoader = AdLoader.Builder(context, adUnitId)
                .withNativeAdOptions(nativeAdOptionsBuilder.build())
                .forNativeAd(nativeAdListener)
                .withAdListener(nativeAdListener)
                .build()

            adLoader.loadAd(adRequest)
        }

        loadAt(0)
    }

    private open inner class NativeAdListener(
        parameters: TapMindAdapterResponseParameters,
        private val context: Context,
        private val listener: TapMindNativeAdAdapterListener,
        private val appId: String,
        private val placementId: String,
        private val adapterId: String,
        private val partner: String
    ) : AdListener(), NativeAd.OnNativeAdLoadedListener {

        //        private val placementId: String = parameters.getThirdPartyAdPlacementId()
        private val serverParameters: Bundle = parameters.getServerParameters()

        override fun onNativeAdLoaded(nativeAd: NativeAd) {
            Log.d(TAG, "$TAG1 Native ad loaded: $placementId")

            this@AdMobManager.nativeAd = nativeAd

//            val templateName = BundleUtils.getString("template", "", serverParameters)
//            val isTemplateAd = TapmindsSdkUtils.isValidString(templateName)
//            if (isTemplateAd && nativeAd.headline.isNullOrEmpty()) {
//                Log.d(TAG,"Native ad ($nativeAd) does not have required assets.")
//                listener.onNativeAdLoadFailed(TapMindAdapterError.MISSING_REQUIRED_NATIVE_AD_ASSETS)
//                return
//            }

            runOnUiThread {
                var mediaView: View? = null
                val mediaContent = nativeAd.mediaContent
                val images = nativeAd.images
                var mainImage: Drawable? = null
                var mediaContentAspectRatio = 0.0f


                if (mediaContent != null) {
                    val googleMediaView = MediaView(context)
                    googleMediaView.mediaContent = mediaContent
                    mediaView = googleMediaView

                    mainImage = mediaContent.mainImage
                    mediaContentAspectRatio = mediaContent.aspectRatio
                } else if (!images.isNullOrEmpty()) {
                    val mediaImage = images[0]
                    val mediaImageView = ImageView(context)
                    val mediaDrawable = mediaImage.drawable
                    if (mediaDrawable != null) {
                        mediaImageView.setImageDrawable(mediaDrawable)
                        mediaView = mediaImageView
                        mediaContentAspectRatio =
                            mediaDrawable.intrinsicWidth.toFloat() / mediaDrawable.intrinsicHeight.toFloat()
                    }
                }

                val icon = nativeAd.icon
                val iconImage = when {
                    icon?.drawable != null -> TapMindNativeAd.TapMindNativeAdImage(icon.drawable!!)
                    icon?.uri != null -> TapMindNativeAd.TapMindNativeAdImage(icon.uri!!)
                    else -> null
                }

                val builder = TapMindNativeAd.Builder()
                    .setAdFormat(TapMindAdFormat.NATIVE)
                    .setTitle(nativeAd.headline)
                    .setAdvertiser(nativeAd.advertiser)
                    .setBody(nativeAd.body)
                    .setCallToAction(nativeAd.callToAction)
                    .setIcon(iconImage)
                    .setHasVideoContent(nativeAd.mediaContent!!.hasVideoContent())
                    .setMediaView(mediaView)
                    .setMainImage(TapMindNativeAd.TapMindNativeAdImage(mainImage!!))
                    .setMediaContentAspectRatio(mediaContentAspectRatio)
                    .setStarRating(nativeAd.starRating)


                val tapMindNativeAd = TapMindGoogleNativeAd(builder)

                val responseInfo = nativeAd.responseInfo
                val responseId = responseInfo?.responseId
                val extraInfo = Bundle(1).apply {
                    putString("creative_id", responseId)
                }

                listener.onNativeAdLoaded(tapMindNativeAd, extraInfo)
                callImpressionRequestAPI(appId, adapterId, placementId, "Native", partner, "SUCCESS")
            }
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            Log.d(TAG, "$TAG1 onAdFailedToLoad " + loadAdError.responseInfo)
            val adapterError = toMaxError(loadAdError)
//            Log.d(TAG,"Native ad ($placementId) failed to load with error: $adapterError")
            listener.onNativeAdLoadFailed(adapterError)
        }

        override fun onAdImpression() {
            Log.d(TAG, "$TAG1 Native ad shown")
            listener.onNativeAdDisplayed(null)

        }

        override fun onAdClicked() {
            Log.d(TAG, "$TAG1 Native ad clicked")
            listener.onNativeAdClicked()
        }

        override fun onAdOpened() {
            Log.d(TAG, "$TAG1 Native ad opened")
        }

        override fun onAdClosed() {
            Log.d(TAG, "$TAG1 Native ad closed")
        }
    }

    private inner class TapMindGoogleNativeAd(builder: Builder) : TapMindNativeAd(builder) {

        override fun prepareForInteraction(
            clickableViews: List<View>,
            container: ViewGroup
        ): Boolean {

            Log.d(TAG, "$TAG1 prepareForInteraction")

            val nativeAd = this@AdMobManager.nativeAd
            if (nativeAd == null) {
                Log.d(TAG, "Failed to register native ad views: native ad is null.")
                return false
            }

            nativeAdView = NativeAdView(container.context)

            // Native integrations
            if (container is TapMindNativeAdView) {
                val mainView = container.mainView
                container.removeView(mainView)
                nativeAdView?.addView(mainView)
                container.addView(nativeAdView)

                nativeAdView?.iconView = container.iconView
                nativeAdView?.headlineView = container.titleView
                nativeAdView?.advertiserView = container.advertiserView
                nativeAdView?.bodyView = container.bodyView
                nativeAdView?.callToActionView = container.callToActionView

                when (val mediaView = getMediaVieww()) {
                    is MediaView -> nativeAdView?.mediaView = mediaView
                    is ImageView -> nativeAdView?.imageView = mediaView
                }

                nativeAdView?.setNativeAd(nativeAd)
            } else {
                var mediaView: View? = null

                for (view in clickableViews) {
                    val viewTag = view.tag ?: continue
                    val tag = viewTag as Int

                    when (tag) {
                        TITLE_LABEL_TAG -> nativeAdView?.headlineView = view
                        ICON_VIEW_TAG -> nativeAdView?.iconView = view
                        BODY_VIEW_TAG -> nativeAdView?.bodyView = view
                        CALL_TO_ACTION_VIEW_TAG -> nativeAdView?.callToActionView = view
                        ADVERTISER_VIEW_TAG -> nativeAdView?.advertiserView = view
                        MEDIA_VIEW_CONTAINER_TAG -> mediaView = getMediaVieww()
                    }
                }

                val pluginContainer = (mediaView?.parent as? ViewGroup) ?: container

                val hasPluginLayout =
                    pluginContainer is RelativeLayout || pluginContainer is FrameLayout

                if (mediaView != null) {
                    pluginContainer.removeView(mediaView)

                    if (!hasPluginLayout && mediaView is MediaView) {
                        val googleMediaView = mediaView
                        val googleMediaContent = googleMediaView.mediaContent
                        if (googleMediaContent != null && googleMediaContent.hasVideoContent()) {
                            mediaView = AutoMeasuringMediaView(pluginContainer.context)
                            googleMediaView.mediaContent = nativeAd.mediaContent
                        }
                    }

                    val mediaViewLayout = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    nativeAdView?.addView(mediaView, mediaViewLayout)

                    when (mediaView) {
                        is MediaView -> nativeAdView?.mediaView = mediaView
                        is ImageView -> nativeAdView?.imageView = mediaView
                    }
                } else {
                    val layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    val placeholderView = View(pluginContainer.context)
                    nativeAdView?.addView(placeholderView, layoutParams)
                    nativeAdView?.storeView = placeholderView
                }

                nativeAdView?.setNativeAd(nativeAd)

                if (hasPluginLayout) {
                    val nativeAdViewLayout = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    pluginContainer.addView(nativeAdView, nativeAdViewLayout)
                } else {
                    nativeAdView?.measure(
                        View.MeasureSpec.makeMeasureSpec(
                            pluginContainer.width,
                            View.MeasureSpec.EXACTLY
                        ),
                        View.MeasureSpec.makeMeasureSpec(
                            pluginContainer.height,
                            View.MeasureSpec.EXACTLY
                        )
                    )
                    nativeAdView?.layout(0, 0, pluginContainer.width, pluginContainer.height)
                    pluginContainer.addView(nativeAdView)
                }
            }

            return true
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

    private fun callImpressionRequestAPI(
        appId: String,
        adapterId: String,
        placementId: String,
        adType: String,
        partner: String,
        result: String
    ) {
        val apiInter = ApiClient.retrofit.create(APInterface::class.java)

        val impressionRequest =
            ImpressionRequest(appId, adapterId, placementId, adType, partner, result)

        Log.e("TapMindAdapterAdmob", "callImpressionRequestAPI: $impressionRequest")
        val callImpression = apiInter.impressionRequest(impressionRequest)

        callImpression.enqueue(object : Callback<ImpressionResponse> {
            override fun onResponse(
                call: Call<ImpressionResponse?>,
                response: Response<ImpressionResponse?>
            ) {
                Log.e("TapMindAdapterAdmob", "onResponseImpression: ${response.raw()}")
                Log.e("TapMindAdapterAdmob", "onResponseImpression: ${response.body()?.message}")
            }

            override fun onFailure(
                call: Call<ImpressionResponse?>,
                t: Throwable
            ) {
                Log.e("TapMindAdapterAdmob", "onFailure: ${t.message}")
            }
        })
    }
}