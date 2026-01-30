package com.tapminds.facebook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdOptionsView
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdExtendedListener
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdBase
import com.facebook.ads.NativeAdListener
import com.facebook.ads.NativeBannerAd
import com.facebook.ads.RewardedVideoAd
import com.facebook.ads.RewardedVideoAdExtendedListener
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import android.provider.Settings
import android.util.Log
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterParameters
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.ads.native.TapMindNativeAdAdapterListener
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.adapter.listener.TapMindsAdapter
import com.tapminds.adapter.listener.TapMindsAdapterInitializationParameters
import com.tapminds.tapmindsads.BundleUtils
import com.tapminds.tapmindsads.StringUtils.isValidString
import com.tapminds.tapmindsads.TapMindAdFormat
import com.tapminds.tapmindsads.TapmindsSdkUtils
import com.tapminds.tapmindsads.TapmindsSdkUtils.runOnUiThread
import java.security.MessageDigest
import java.util.*

class FbManager {

    val TAG = "APP@@@"
    val TAG1 = "FbManager"
    companion object{

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: FbManager? = null

        fun getInstance(): FbManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FbManager().also {
                    INSTANCE = it
                }
            }
        }

        private val INITIALIZED: AtomicBoolean = AtomicBoolean()
        private var sStatus: TapMindsAdapter.InitializationStatus  ?=null
    }

    fun isInitialized(): Boolean {
        return INITIALIZED.get()
    }


    fun initialize(
        parameters: TapMindsAdapterInitializationParameters,
        activity: Activity?,
        onCompletionListener: TapMindsAdapter.OnCompletionListener
    ) {
        // Update ad settings
        updateAdSettings(parameters)

        if (INITIALIZED.compareAndSet(false, true)) {
            sStatus = TapMindsAdapter.InitializationStatus.INITIALIZING

            val placementIds = parameters.getServerParameters().getStringArrayList("placement_ids") ?: ""

            val initListener = AudienceNetworkAds.InitListener { initResult ->
                if (initResult.isSuccess) {
                    Log.d(TAG,"$TAG1 Facebook SDK successfully finished initialization: ${initResult.message}")
                    sStatus = TapMindsAdapter.InitializationStatus.INITIALIZED_SUCCESS
                    onCompletionListener.onCompletion(sStatus, null)
                } else {
                    Log.d(TAG,"$TAG1 Facebook SDK failed to finish initialization: ${initResult.message}")
                    sStatus = TapMindsAdapter.InitializationStatus.INITIALIZED_FAILURE
                    onCompletionListener.onCompletion(sStatus, initResult.message)
                }
            }

//            if (parameters.isTesting) {
                AdSettings.setDebugBuild(true)
//            }
            AdSettings.setTestMode(true)
            AdSettings.addTestDevice(getHashedDeviceId(activity!!).toString())

//            Log.d(TAG,"Initializing Facebook SDK with placements: $placementIds")

            AudienceNetworkAds.buildInitSettings(activity)
//                .withMediationService(getMediationIdentifier())
//                .withPlacementIds(placementIds)
                .withInitListener(initListener)
                .initialize()
        } else {
            onCompletionListener.onCompletion(sStatus, null)
        }
    }


    fun getHashedDeviceId(context: Context) {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val hashedId = md5(androidId.uppercase(Locale.US))
        Log.d("MetaAds", "Your FAN test device hash: $hashedId")
    }

    private fun md5(s: String): String {
        val digest = MessageDigest.getInstance("MD5")
        digest.update(s.toByteArray())
        val messageDigest = digest.digest()
        val hexString = StringBuilder()
        for (b in messageDigest) {
            val hex = Integer.toHexString(0xFF and b.toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    //-----------------------------------START InterstitialAd-------------------------------------//

    private var mInterstitialAd : InterstitialAd? = null
    private val onInterstitialAdHiddenCalled = AtomicBoolean()

    fun loadInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity?,
        listener: TapMindInterstitialAdapterListener
    ) {
        val placementId = parameters.getThirdPartyAdPlacementId()

        Log.d(TAG,"Loading interstitial ad: $placementId...")

        updateAdSettings(parameters)

        mInterstitialAd = InterstitialAd(activity, placementId)
        val adLoadConfigBuilder = mInterstitialAd!!
            .buildLoadAdConfig()
            .withAdListener(InterstitialAdListener(listener))

        if (mInterstitialAd?.isAdLoaded == true && mInterstitialAd?.isAdInvalidated == false) {
            Log.d(TAG,"An interstitial ad has been loaded already")
            listener.onInterstitialAdLoaded()
        } else {
            Log.d(TAG,"Loading bidding interstitial ad...")
            mInterstitialAd?.loadAd(adLoadConfigBuilder.withBid(parameters.getBidResponse()).build())
        }
    }

    private inner class InterstitialAdListener(
        private val listener: TapMindInterstitialAdapterListener
    ) : InterstitialAdExtendedListener {

        override fun onAdLoaded(ad: Ad) {
            Log.d(TAG,"Interstitial ad loaded: ${ad.placementId}")
            listener.onInterstitialAdLoaded()
        }

        override fun onError(ad: Ad?, adError: AdError) {
            val adapterError = toMaxError(adError)
            Log.d(TAG,"Interstitial ad (${ad?.placementId}) failed to load with error: $adapterError")
            listener.onInterstitialAdLoadFailed(adapterError)
        }

        override fun onAdClicked(ad: Ad) {
            Log.d(TAG,"Interstitial ad clicked: ${ad.placementId}")
            listener.onInterstitialAdClicked()
        }

        override fun onLoggingImpression(ad: Ad) {
            // Max does its own impression tracking
            Log.d(TAG,"Interstitial ad logging impression: ${ad.placementId}")
            listener.onInterstitialAdDisplayed()
        }

        override fun onInterstitialDisplayed(ad: Ad) {
            Log.d(TAG,"Interstitial ad displayed: ${ad.placementId}")
        }

        override fun onInterstitialDismissed(ad: Ad) {
            Log.d(TAG,"Interstitial ad hidden: ${ad.placementId}")
            if (onInterstitialAdHiddenCalled.compareAndSet(false, true)) {
                listener.onInterstitialAdHidden()
            }
        }

        override fun onInterstitialActivityDestroyed() {
            Log.d(TAG,"Interstitial ad Activity destroyed")

            // This may occur if launched from the app icon with `android:launchMode="singleTask"`
            if (onInterstitialAdHiddenCalled.compareAndSet(false, true)) {
                listener.onInterstitialAdHidden()
            }
        }

        override fun onRewardedAdCompleted() {
            // No-op
        }

        override fun onRewardedAdServerSucceeded() {
            // No-op
        }

        override fun onRewardedAdServerFailed() {
            // No-op
        }
    }

    fun showInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity?,
        listener: TapMindInterstitialAdapterListener
    ) {
        Log.d(TAG,"Showing interstitial ad: ${parameters.getThirdPartyAdPlacementId()}...")

        if (mInterstitialAd != null && mInterstitialAd?.isAdLoaded == true) {
            // Check if ad is already expired or invalidated
            if (mInterstitialAd?.isAdInvalidated == false) {
                mInterstitialAd?.show()
            } else {
                Log.d(TAG,"Unable to show interstitial - ad expired...")
                listener.onInterstitialAdDisplayFailed(TapMindAdapterError.AD_EXPIRED)
            }
        } else {
            Log.d(TAG,"Unable to show interstitial - no ad loaded...")
            listener.onInterstitialAdDisplayFailed(
                TapMindAdapterError(TapMindAdapterError.AD_DISPLAY_FAILED, 0, "Interstitial ad not ready")
            )
        }
    }


    //------------------------------------END  InterstitialAd-------------------------------------//

    //----------------------------------------START AppOpenAd-------------------------------------//
    //------------------------------------------END AppOpenAd-------------------------------------//

    //---------------------------------------START RewardedAd-------------------------------------//

    private var mRewardedVideoAd: RewardedVideoAd? = null
    private val onRewardedAdHiddenCalled = AtomicBoolean()

    fun loadRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity?,
        listener: TapMindRewardedAdapterListener
    ) {
        val placementId = parameters.getThirdPartyAdPlacementId()

        Log.d(TAG,"Loading rewarded: $placementId...")

        updateAdSettings(parameters)

        mRewardedVideoAd = RewardedVideoAd(activity, placementId)
        val adLoadConfigBuilder = mRewardedVideoAd
            ?.buildLoadAdConfig()
            ?.withAdListener(RewardedAdListener(listener))

        if (mRewardedVideoAd?.isAdLoaded == true && mRewardedVideoAd?.isAdInvalidated == false) {
            Log.d(TAG,"A rewarded ad has been loaded already")
            listener.onRewardedAdLoaded()
        } else {
            Log.d(TAG,"Loading bidding rewarded ad...")
            mRewardedVideoAd?.loadAd(adLoadConfigBuilder?.withBid(parameters.getBidResponse())?.build())
        }
    }

    private inner class RewardedAdListener(
        private val listener: TapMindRewardedAdapterListener
    ) : RewardedVideoAdExtendedListener {

        private var hasGrantedReward = false

        override fun onAdLoaded(ad: Ad) {
            Log.d(TAG,"Rewarded ad loaded: ${ad.placementId}")
            listener.onRewardedAdLoaded()
        }

        override fun onError(ad: Ad?, adError: AdError) {
            Log.d(TAG,"Rewarded ad (${ad?.placementId}) failed to load with error (${adError.errorCode}-${adError.errorMessage})")
            val adapterError = toMaxError(adError)
            Log.d(TAG,"Rewarded ad (${ad?.placementId}) failed to load with error ($adapterError)")
            listener.onRewardedAdLoadFailed(adapterError)
        }

        override fun onAdClicked(ad: Ad) {
            Log.d(TAG,"Rewarded ad clicked: ${ad.placementId}")
            listener.onRewardedAdClicked()
        }

        override fun onRewardedVideoClosed() {
            Log.d(TAG,"Rewarded ad hidden")

            if (onRewardedAdHiddenCalled.compareAndSet(false, true)) {
                if (hasGrantedReward) {
                    val reward = getReward()
                    Log.d(TAG,"Rewarded user with reward: $reward")
                    listener.onUserRewarded(reward)
                }

                listener.onRewardedAdHidden()
            }
        }

        override fun onRewardedVideoCompleted() {
            Log.d(TAG,"Rewarded ad video completed")
            hasGrantedReward = true
        }

        override fun onLoggingImpression(ad: Ad) {
            Log.d(TAG,"Rewarded ad logging impression: ${ad.placementId}")
            listener.onRewardedAdDisplayed()
        }



        override fun onRewardedVideoActivityDestroyed() {
            Log.d(TAG,"Rewarded ad Activity destroyed")

            // We will not reward the user if Activity is destroyed — this may be due to launching
            // from app icon and having the `android:launchMode="singleTask"` flag
            if (onRewardedAdHiddenCalled.compareAndSet(false, true)) {
                listener.onRewardedAdHidden()
            }
        }
    }

    fun showRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity?,
        listener: TapMindRewardedAdapterListener
    ) {
        Log.d(TAG,"Showing rewarded ad: ${parameters.getThirdPartyAdPlacementId()}...")

        if (mRewardedVideoAd != null && mRewardedVideoAd?.isAdLoaded == true) {
            // Check if ad is already expired or invalidated — you won’t get paid for invalidated ads
            if (mRewardedVideoAd?.isAdInvalidated == false) {
                // Configure user reward from server
//                configureReward(parameters)

                mRewardedVideoAd?.show()
            } else {
                Log.d(TAG,"Unable to show rewarded ad - ad expired...")
                listener.onRewardedAdDisplayFailed(TapMindAdapterError.AD_EXPIRED)
            }
        } else {
            Log.d(TAG,"Unable to show rewarded ad - no ad loaded...")
            listener.onRewardedAdDisplayFailed(
                TapMindAdapterError(TapMindAdapterError.AD_DISPLAY_FAILED, 0, "Rewarded ad not ready")
            )
        }
    }


    private var rewardType : String ?= null
    private var rewardAmount : Int ?= null

    private fun getReward(): TapMindReward {
        return object : TapMindReward {
            override val label: String
                get() = rewardType ?: TapMindReward.DEFAULT_LABEL
            override val amount: Int
                get() = rewardAmount ?: TapMindReward.DEFAULT_AMOUNT

        }
    }



    //-----------------------------------------END RewardedAd-------------------------------------//

    //-----------------------------------------START AdView---------------------------------------//


    private var mAdView: AdView? = null
    private var mNativeAd: NativeAd? = null
    private var mNativeBannerAd: NativeBannerAd? = null

    fun loadAdViewAd(
        parameters: TapMindAdapterResponseParameters,
        adFormat: TapMindAdFormat,
        activity: Activity?,
        listener: TapMindAdViewAdapterListener
    ) {

        mAdView =  AdView(activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
        val adListener = object : AdListener{
            override fun onError(p0: Ad?, p1: AdError?) {
                Log.d(TAG,"$TAG1 onError : "+p1?.errorCode + " "+p1?.errorMessage)
                p1?.let{ listener.onAdViewAdLoadFailed(toMaxError(it)) }
            }

            override fun onAdLoaded(p0: Ad?) {
                listener.onAdViewAdLoaded(mAdView!!.rootView)
                Log.d(TAG,"$TAG1 onAdLoaded")
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d(TAG,"$TAG1 onAdClicked")
                listener.onAdViewAdClicked()
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d(TAG,"$TAG1 onLoggingImpression")
                listener.onAdViewAdDisplayed()
            }

        }
        mAdView?.loadAd(mAdView?.buildLoadAdConfig()?.withAdListener(adListener)?.build())




//
//        val placementId = parameters.getThirdPartyAdPlacementId()
//        val isNative = parameters.getServerParameters().getBoolean("is_native")
//
//        Log.d(TAG,"Loading${if (isNative) " native " else " "} ${adFormat.getLabel()} ad: $placementId...")
//
//        updateAdSettings(parameters)
//
////         NOTE: FB native is no longer supported in banners but kept for backward compatibility
//        if (isNative) {
//            mNativeAd = NativeAd(activity, placementId)
//            mNativeAd?.loadAd(
//                mNativeAd?.buildLoadAdConfig()
//                    ?.withAdListener(NativeAdViewListener(parameters.getServerParameters(), adFormat, activity, listener))
//                    ?.withBid(parameters.getBidResponse())
//                    ?.build()
//            )
//        } else {
//            mAdView = AdView(activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", toAdSize(adFormat))
//            mAdView?.loadAd(
//                mAdView?.buildLoadAdConfig()
//                    ?.withAdListener(AdViewListener(adFormat, listener))
//                    ?.withBid(parameters.getBidResponse())
//                    ?.build()
//            )
//        }
    }

    /**
     * Implementation of Facebook's [NativeAdListener] for MAX adview ads.
     */
//    private inner class NativeAdViewListener(
//        private val serverParameters: Bundle,
//        private val adFormat: TapMindAdFormat,
//        activity: Activity?,
//        private val listener: MaxAdViewAdapterListener
//    ) : NativeAdListener {
//
//        private val activityRef = WeakReference(activity)
//
//        override fun onAdLoaded(ad: Ad) {
//            Log.d(TAG,"Native ${adFormat.label} ad loaded: ${ad.placementId}")
//
//            // Check validity
//            if (mNativeAd == null || mNativeAd != ad) {
//                Log.d(TAG,"Native ${adFormat.label} ad failed to load: no fill")
//                listener.onAdViewAdLoadFailed(TapMindAdapterError.NO_FILL)
//                return
//            }
//
//            if (mNativeAd?.isAdInvalidated == true) {
//                Log.d(TAG,"Native ${adFormat.label} ad failed to load: ad is no longer valid")
//                listener.onAdViewAdLoadFailed(TapMindAdapterError.AD_EXPIRED)
//                return
//            }
//
//            if (adFormat == TapMindAdFormat.MREC) {
//                val mrecView = NativeAdView.render(activityRef.get(), mNativeAd)
//                listener.onAdViewAdLoaded(mrecView)
//            } else {
//                renderNativeAdView()
//            }
//        }
//
//        override fun onMediaDownloaded(ad: Ad) {
//            Log.d(TAG,"Native ${adFormat.label} successfully downloaded media: ${ad.placementId}")
//        }
//
//        override fun onError(ad: Ad?, adError: AdError) {
//            val adapterError = toMaxError(adError)
//            Log.d(TAG,"Native ${adFormat.label} ad (${ad?.placementId}) failed to load with error: $adapterError")
//            listener.onAdViewAdLoadFailed(adapterError)
//        }
//
//        override fun onLoggingImpression(ad: Ad) {
//            Log.d(TAG,"Native ${adFormat.getLabel()} shown: ${ad.placementId}")
//            listener.onAdViewAdDisplayed()
//        }
//
//        override fun onAdClicked(ad: Ad) {
//            Log.d(TAG,"Native ${adFormat.getLabel()} clicked: ${ad.placementId}")
//            listener.onAdViewAdClicked()
//        }
//
//        private fun renderNativeAdView() {
//            runOnUiThread {
//                val activity = activityRef.get()
//                val context = activity
//
//                val iconView = MediaView(context)
//                val mediaView = MediaView(context)
//
//                val maxNativeAd = MaxNativeAd.Builder()
//                    .setAdFormat(adFormat)
//                    .setTitle(mNativeAd?.adHeadline)
//                    .setAdvertiser(mNativeAd?.advertiserName)
//                    .setBody(mNativeAd?.adBodyText)
//                    .setCallToAction(mNativeAd?.adCallToAction)
//                    .setIconView(iconView)
//                    .setOptionsView(AdOptionsView(context, mNativeAd, null))
//                    .setMediaView(mediaView)
//                    .build()
//
//                // Handle template selection
//                val templateName = BundleUtils.getString("template", "", serverParameters)
//                val maxNativeAdView = when {
//                    templateName.contains("vertical") -> {
//                        if (templateName == "vertical") {
//                            val verticalTemplateName = if (adFormat == MaxAdFormat.LEADER)
//                                "vertical_leader_template" else "vertical_media_banner_template"
//                            createMaxNativeAdView(maxNativeAd, verticalTemplateName, activity)
//                        } else {
//                            createMaxNativeAdView(maxNativeAd, templateName, activity)
//                        }
//                    }
//                    else -> {
//                        createMaxNativeAdView(
//                            maxNativeAd,
//                            if (AppLovinSdkUtils.isValidString(templateName))
//                                templateName else "media_banner_template",
//                            activity
//                        )
//                    }
//                }
//
//                // Setup clickable views
//                val clickableViews = mutableListOf<View>()
//                maxNativeAdView.titleTextView?.takeIf { AppLovinSdkUtils.isValidString(maxNativeAd.title) }?.let {
//                    clickableViews.add(it)
//                }
//                maxNativeAdView.advertiserTextView?.takeIf { AppLovinSdkUtils.isValidString(maxNativeAd.advertiser) }?.let {
//                    clickableViews.add(it)
//                }
//                maxNativeAdView.bodyTextView?.takeIf { AppLovinSdkUtils.isValidString(maxNativeAd.body) }?.let {
//                    clickableViews.add(it)
//                }
//                maxNativeAdView.callToActionButton?.takeIf { AppLovinSdkUtils.isValidString(maxNativeAd.callToAction) }?.let {
//                    clickableViews.add(it)
//                }
//                maxNativeAd.iconView?.let { clickableViews.add(it) }
//
//                maxNativeAd.mediaView?.let { media ->
//                    maxNativeAdView.mediaContentViewGroup?.let { group ->
//                        clickableViews.add(group)
//                    }
//                }
//
//                mNativeAd?.registerViewForInteraction(maxNativeAdView, mediaView, iconView, clickableViews)
//                listener.onAdViewAdLoaded(maxNativeAdView)
//            }
//        }
//    }

//    private fun createMaxNativeAdView(
//        maxNativeAd: MaxNativeAd,
//        templateName: String,
//        activity: Activity?
//    ): MaxNativeAdView {
//        return MaxNativeAdView(maxNativeAd, templateName, activity)
//    }


//    private inner class AdViewListener(
//        private val adFormat: TapMindAdFormat,
//        private val listener: TapMindAdViewAdapterListener
//    ) : AdListener {
//
//        override fun onAdLoaded(ad: Ad) {
//            Log.d(TAG,"${adFormat.getLabel()} ad loaded: ${ad.placementId}")
//            mAdView?.let { listener.onAdViewAdLoaded(it.rootView) }
//        }
//
//        override fun onError(ad: Ad, adError: AdError) {
//            val adapterError = toMaxError(adError)
//            Log.d(TAG,"${adFormat.getLabel()} ad (${ad.placementId}) failed to load with error ($adapterError)")
//            listener.onAdViewAdLoadFailed(adapterError)
//        }
//
//        override fun onAdClicked(ad: Ad) {
//            Log.d(TAG,"${adFormat.getLabel()} ad clicked: ${ad.placementId}")
//            listener.onAdViewAdClicked()
//        }
//
//        override fun onLoggingImpression(ad: Ad) {
//            Log.d(TAG,"${adFormat.getLabel()} ad displayed: ${ad.placementId}")
//            listener.onAdViewAdDisplayed()
//        }
//    }


    fun loadNativeAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        listener: TapMindNativeAdAdapterListener
    ) {
        val serverParameters = parameters.getServerParameters()
        val isNativeBanner = BundleUtils.getBoolean("is_native_banner", serverParameters)
        val placementId = parameters.getThirdPartyAdPlacementId()

        Log.d(TAG,"$TAG1 Loading native ${if (isNativeBanner) "banner " else ""}ad: $placementId...")

        updateAdSettings(parameters)

        val context = activity

        if (isNativeBanner) {
            mNativeBannerAd = NativeBannerAd(context, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
            val adConfig = mNativeBannerAd!!.buildLoadAdConfig()
                .withAdListener(TapMindNativeAdListener(serverParameters, context, listener))
                .withBid(parameters.getBidResponse())
                .build()
            mNativeBannerAd!!.loadAd(adConfig)
        } else {
            mNativeAd = NativeAd(context, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
            val adConfig = mNativeAd!!.buildLoadAdConfig()
                .withAdListener(TapMindNativeAdListener(serverParameters, context, listener))
                .withBid(parameters.getBidResponse())
                .build()
            mNativeAd!!.loadAd(adConfig)
        }
    }

    /**
     * Implementation of Facebook's NativeAdListener for MAX native ads.
     */
    private inner class TapMindNativeAdListener(
        private val serverParameters: Bundle,
        private val context: Context,
        private val listener: TapMindNativeAdAdapterListener
    ) : NativeAdListener {

        override fun onAdLoaded(ad: Ad) {
            Log.d(TAG,"$TAG1 Native ad loaded: ${ad.placementId}")

            // This listener is used for both native ads and native banner ads
            val nativeAd = mNativeAd ?: mNativeBannerAd

            // `nativeAd` may be null if the adapter is destroyed before the ad loaded (timed out)
            if (nativeAd == null || nativeAd != ad) {
                Log.d(TAG,"$TAG1 Native ad failed to load: no fill")
                listener.onNativeAdLoadFailed(TapMindAdapterError.NO_FILL)
                return
            }

            if (nativeAd.isAdInvalidated) {
                Log.d(TAG,"$TAG1 Native ad failed to load: ad is no longer valid")
                listener.onNativeAdLoadFailed(TapMindAdapterError.AD_EXPIRED)
                return
            }

            val templateName = BundleUtils.getString("template", "", serverParameters)
            val isTemplateAd = TapmindsSdkUtils.isValidString(templateName)
            if (isTemplateAd && nativeAd.adHeadline.isNullOrEmpty()) {
                Log.d(TAG,"$TAG1 Native ad ($nativeAd) does not have required assets.")
                listener.onNativeAdLoadFailed(TapMindAdapterError.MISSING_REQUIRED_NATIVE_AD_ASSETS)
                return
            }

            // Ensure UI rendering is done on the UI thread
            runOnUiThread {
                val mediaView = MediaView(context)
//                mediaView.setBackgroundResource(android.R.color.black)

                val iconDrawable = nativeAd.preloadedIconViewDrawable
                val icon = nativeAd.adIcon

                when {
                    iconDrawable != null -> {
                        handleNativeAdLoaded(nativeAd, iconDrawable, mediaView, context)
                    }
                    icon != null -> {
                        getCachingExecutorService().execute {
                            var iconDrawableAsync: Drawable? = null
                            if (isValidString(icon.url)) {
                                Log.d(TAG,"$TAG1 Adding native ad icon (${icon.url}) to queue to be fetched")

                                val iconDrawableFuture = createDrawableFuture(icon.url, context.resources)
                                val imageTaskTimeoutSeconds = BundleUtils.getInt("image_task_timeout_seconds", 10, serverParameters)

                                try {
                                    iconDrawableAsync = iconDrawableFuture?.get(imageTaskTimeoutSeconds.toLong(), TimeUnit.SECONDS)

                                    Log.d(TAG,"$TAG1 Image fetching tasks sucess ")

                                } catch (th: Throwable) {
                                    Log.d(TAG,"$TAG1 Image fetching tasks failed"+ th)
                                }
                            }
                            handleNativeAdLoaded(nativeAd, iconDrawableAsync, mediaView, context)
                        }
                    }
                    else -> {
                        // Optional icon missing
                        Log.d(TAG,"$TAG1 No native ad icon (optional) available for the current creative.")
                        handleNativeAdLoaded(nativeAd, null, mediaView, context)
                    }
                }
            }
        }

        override fun onMediaDownloaded(ad: Ad) {
            Log.d(TAG,"$TAG1 Native ad successfully downloaded media: ${ad.placementId}")
        }

        override fun onError(ad: Ad, adError: AdError) {
            val adapterError = toMaxError(adError)
            Log.d(TAG,"$TAG1 Native ad (${ad.placementId}) failed to load with error ($adapterError)")
            listener.onNativeAdLoadFailed(adapterError)
        }

        override fun onLoggingImpression(ad: Ad) {
            Log.d(TAG,"$TAG1 Native shown: ${ad.placementId}")
            listener.onNativeAdDisplayed(null)
        }

        override fun onAdClicked(ad: Ad) {
            Log.d(TAG,"$TAG1 Native clicked: ${ad.placementId}")
            listener.onNativeAdClicked()
        }

        private fun handleNativeAdLoaded(
            nativeAd: NativeAdBase,
            iconDrawable: Drawable?,
            mediaView: MediaView,
            context: Context
        ) {

                var mainImage: TapMindNativeAd.TapMindNativeAdImage? = null

                // Only get ad cover image for NativeAd (not banner)
                if (nativeAd is NativeAd && nativeAd.adCoverImage != null) {
                    val uri = Uri.parse(nativeAd.adCoverImage!!.url)
                    mainImage = TapMindNativeAd.TapMindNativeAdImage(uri)

                }


                val builder = TapMindNativeAd.Builder()
                    .setAdFormat(TapMindAdFormat.NATIVE)
                    .setTitle(nativeAd.adHeadline)
                    .setAdvertiser(nativeAd.advertiserName)
                    .setBody(nativeAd.adBodyText)
                    .setCallToAction(nativeAd.adCallToAction)
                    .setMediaContentAspectRatio(nativeAd.aspectRatio)
                    .setIcon(TapMindNativeAd.TapMindNativeAdImage(iconDrawable))
                    .setAdChoise(AdOptionsView(context, nativeAd, null))

                if (nativeAd is NativeAd) {
                    builder.setMainImage(mainImage)
                }

                if (nativeAd is NativeBannerAd) {
                    // Native banners use icon as media
                    val mediaViewImageView = ImageView(context)
                    mediaViewImageView.setImageDrawable(iconDrawable)
                    builder.setMediaView(mediaViewImageView)


                } else {
                        builder.setMediaView(mediaView)
                }

                val maxNativeAd = TapMindFacebookNativeAd(builder)

                listener.onNativeAdLoaded(maxNativeAd, null)

        }
    }

    private inner class TapMindFacebookNativeAd(builder: Builder) : TapMindNativeAd(builder) {

        override fun prepareForInteraction(clickableViews: List<View>, container: ViewGroup): Boolean {

            Log.d(TAG,"$TAG1 TapMindFacebookNativeAd prepareForInteraction")

            val nativeAd: NativeAdBase? = mNativeAd ?: mNativeBannerAd
            if (nativeAd == null) {
                Log.d(TAG,"$TAG1 Failed to register native ad views: native ad is null.")
                return false
            }

            // To avoid IllegalArgumentException: Invalid set of clickable views (size=0)
            if (clickableViews.isEmpty()) {
                Log.d(TAG,"$TAG1 No clickable views to prepare")
                return false
            }

            var iconImageView: ImageView? = null
            for (clickableView in clickableViews) {
                if (clickableView is ImageView) {
                    iconImageView = clickableView
                    break
                }
            }

            Log.d(TAG,"$TAG1 updatedClickableViews")

            val updatedClickableViews = clickableViews.toMutableList()
            getMediaVieww()?.let { updatedClickableViews.add(it) }

            when (nativeAd) {
                is NativeBannerAd -> {
                    when {
                        iconImageView != null -> {
                            Log.d(TAG,"$TAG1 register iconImageView")
                            nativeAd.registerViewForInteraction(container, iconImageView, updatedClickableViews)
                        }
                        getMediaVieww() is ImageView -> {
                            Log.d(TAG,"$TAG1 register ImageView")
                            nativeAd.registerViewForInteraction(container, getMediaVieww() as ImageView, updatedClickableViews)
                        }
                        else -> {
                            Log.d(TAG,"$TAG1 Failed to register native ad view for interaction: icon image view and media view are null")
                            return false
                        }
                    }
                }

                is NativeAd -> {
                    Log.d(TAG,"$TAG1 register view")
                    nativeAd.registerViewForInteraction(container, getMediaVieww() as? MediaView, iconImageView, updatedClickableViews)
                }

                else -> {
                    Log.d(TAG,"$TAG1 Unknown native ad type: ${nativeAd.javaClass.simpleName}")
                    return false
                }
            }

            return true
        }
    }



    private var cachingExecutorService: ExecutorService? = null

    private fun getCachingExecutorService(): ExecutorService {
        // Use a thread-safe singleton executor service for background caching tasks
        if (cachingExecutorService == null || cachingExecutorService!!.isShutdown) {
            cachingExecutorService = Executors.newCachedThreadPool { runnable ->
                Thread(runnable, "applovin-caching").apply {
                    isDaemon = true
                    priority = Thread.NORM_PRIORITY
                }
            }
        }
        return cachingExecutorService!!
    }

    private fun createDrawableFuture(
        url: String,
        resources: Resources
    ): Future<Drawable>? {
        return getCachingExecutorService().submit<Drawable> {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.instanceFollowRedirects = true
                connection.doInput = true
                connection.connect()

                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                connection.disconnect()

                BitmapDrawable(resources, bitmap)
            } catch (th: Throwable) {
                Log.d(TAG,"${TAG1} Failed to fetch image drawable from URL: $url" + th.toString())
                null
            }
        }
    }

    //-----------------------------------------END AdView-----------------------------------------//


    fun onDestroy() {
//        mInterstitialAd?.let {
//            it.destroy()
//            mInterstitialAd = null
//        }
//
//        mRewardedVideoAd?.let {
//            it.destroy()
//            mRewardedVideoAd = null
//        }

        mAdView?.let {
            it.destroy()
            mAdView = null
        }

        mNativeAd?.let {
            it.unregisterView()
            it.destroy()
            mNativeAd = null
        }
//
//        mNativeBannerAd?.let {
//            it.unregisterView()
//            it.destroy()
//            mNativeBannerAd = null
//        }
    }



    //------------------------------------- extra methods-----------------------------------------//

    private fun updateAdSettings(parameters: TapMindAdapterParameters) {
        val serverParameters = parameters.getServerParameters()

        if (serverParameters!!.containsKey("video_autoplay")) {
            val videoAutoplay = serverParameters.getBoolean("video_autoplay")
            AdSettings.setVideoAutoplay(videoAutoplay)
        }

        val testDevicesString = serverParameters.getString("test_device_ids", null)
        if (!testDevicesString.isNullOrEmpty()) {
            val testDeviceList = testDevicesString.split(",").map { it.trim() }
            AdSettings.addTestDevices(testDeviceList)
        }

        // Update mediation service
//        AdSettings.setMediationService(getMediationIdentifier())
    }

//    private fun getMediationIdentifier(): String {
//        return "APPLOVIN_${AppLovinSdk.VERSION}:${getAdapterVersion()}"
//    }

//    fun getAdapterVersion(): String {
//        return com.applovin.mediation.adapters.facebook.BuildConfig.VERSION_NAME
//    }

    private fun toMaxError(facebookError: AdError): TapMindAdapterError {
        val facebookErrorCode = facebookError.errorCode
        var adapterError = TapMindAdapterError.UNSPECIFIED

        when (facebookErrorCode) {
            AdError.NETWORK_ERROR_CODE -> adapterError = TapMindAdapterError.NO_CONNECTION
            AdError.NO_FILL_ERROR_CODE -> adapterError = TapMindAdapterError.NO_FILL
            AdError.SERVER_ERROR_CODE, AdError.REMOTE_ADS_SERVICE_ERROR -> adapterError =
                TapMindAdapterError.SERVER_ERROR

            AdError.INTERNAL_ERROR_CODE, AdError.INTERSTITIAL_AD_TIMEOUT -> adapterError =
                TapMindAdapterError.TIMEOUT

            AdError.CACHE_ERROR_CODE, AdError.BROKEN_MEDIA_ERROR_CODE, AdError.SHOW_CALLED_BEFORE_LOAD_ERROR_CODE, AdError.LOAD_CALLED_WHILE_SHOWING_AD, AdError.LOAD_TOO_FREQUENTLY_ERROR_CODE, AdError.NATIVE_AD_IS_NOT_LOADED, AdError.INCORRECT_STATE_ERROR -> adapterError =
                TapMindAdapterError.INVALID_LOAD_STATE

            AdError.INTERNAL_ERROR_2003, AdError.INTERNAL_ERROR_2004, AdError.INTERNAL_ERROR_2006 -> adapterError =
                TapMindAdapterError.INTERNAL_ERROR

            AdError.MEDIAVIEW_MISSING_ERROR_CODE, AdError.ICONVIEW_MISSING_ERROR_CODE, AdError.AD_ASSETS_UNSUPPORTED_TYPE_ERROR_CODE -> adapterError =
                TapMindAdapterError.MISSING_REQUIRED_NATIVE_AD_ASSETS

            AdError.CLEAR_TEXT_SUPPORT_NOT_ALLOWED -> adapterError =
                TapMindAdapterError.INVALID_CONFIGURATION

            AdError.MISSING_DEPENDENCIES_ERROR, AdError.API_NOT_SUPPORTED, AdError.AD_PRESENTATION_ERROR_CODE -> adapterError =
                TapMindAdapterError.INTERNAL_ERROR
        }
        return TapMindAdapterError(
            adapterError,
            facebookErrorCode,
            facebookError.errorMessage
        )
    }


    private fun toAdSize(adFormat: TapMindAdFormat): AdSize {
        return if (adFormat === TapMindAdFormat.BANNER) {
            AdSize.BANNER_HEIGHT_50
        } else if (adFormat === TapMindAdFormat.LEADER) {
            AdSize.BANNER_HEIGHT_90
        } else if (adFormat === TapMindAdFormat.MREC) {
            AdSize.RECTANGLE_HEIGHT_250
        } else {
            throw IllegalArgumentException("Invalid ad format: $adFormat")
        }
    }

}