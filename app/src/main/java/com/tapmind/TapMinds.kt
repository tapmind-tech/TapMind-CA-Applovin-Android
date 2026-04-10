package com.tapmind

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxReward
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
import com.applovin.mediation.adapter.listeners.MaxAppOpenAdapterListener
import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
import com.applovin.mediation.adapter.listeners.MaxRewardedAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.mediation.nativeAds.MaxNativeAd
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.google.android.gms.ads.identifier.AdvertisingIdClient
//import com.adapter.TapMindsMediationAdapter
import java.util.Collections
import java.util.concurrent.Executors

class TapMinds {

    val TAG = "APP@@@"
    val TAG1 = "TapMinds"
    private val admob = "admob"
    private val facebook = "facebook"
    private val adsnetwork = "ads_network"
//    private lateinit var tapMindsMediationAdapter : TapMindsMediationAdapter

    fun init(context: Context){
        val YOUR_SDK_KEY = "05TMDQ5tZabpXQ45_UTbmEGNUtVAzSTzT6KmWQc5_CuWdzccS4DCITZoL3yIWUG3bbq60QC_d4WF28tUC4gVTF"

        val executor = Executors.newSingleThreadExecutor();
        executor.execute {
            val initConfigBuilder = AppLovinSdkInitializationConfiguration.builder(YOUR_SDK_KEY, context)
            initConfigBuilder.mediationProvider = AppLovinMediationProvider.MAX

            // Enable test mode by default for the current device. Cannot be run on the main thread.
            val currentGaid = AdvertisingIdClient.getAdvertisingIdInfo(context).id
            if (currentGaid != null) {
                initConfigBuilder.testDeviceAdvertisingIds = Collections.singletonList(currentGaid)
            }


            // Initialize the AppLovin SDK
            val sdk = AppLovinSdk.getInstance(context)
            sdk.settings.setVerboseLogging(true)

            sdk.initialize(initConfigBuilder.build()) {

                Log.d(TAG,"$TAG1 : sdk initialize")

//                tapMindsMediationAdapter = TapMindsMediationAdapter(sdk)

                val initParams = object : MaxAdapterInitializationParameters {
                    override fun getAdUnitId(): String = "test_ad_unit_id"

                    override fun getLocalExtraParameters(): MutableMap<String, Any> = mutableMapOf()

                    override fun getServerParameters(): Bundle = Bundle().apply {
                        putString("app_id", "test_app_id") // same key you use in your adapter
                    }

                    override fun getCustomParameters(): Bundle = Bundle()

                    override fun hasUserConsent(): Boolean? = true

                    override fun isAgeRestrictedUser(): Boolean? = false

                    override fun isDoNotSell(): Boolean? = false

                    override fun getConsentString(): String? = "test_consent"

                    override fun isTesting(): Boolean = true
                }

//                tapMindsMediationAdapter.initialize(initParams, context as Activity) { status, _ ->
//                    Log.d(TAG,"$TAG1 : Adapter initialize $status")
//                }

            }

            executor.shutdown()
        }
    }

    fun showAdmobInterstitialAd(context: Context){
        val requestParameters = getRequestParameters("ca-app-pub-3940256099942544/1033173712",Bundle().apply {  putString(adsnetwork, admob) })

//        tapMindsMediationAdapter.loadInterstitialAd(requestParameters,context as Activity,object : MaxInterstitialAdapterListener {
//            override fun onInterstitialAdLoaded() {
//                Log.d(TAG,"$TAG1 : showAdmobInterstitialAd onInterstitialAdLoaded")
//
//            }
//
//            override fun onInterstitialAdLoaded(p0: Bundle?) {
//                Log.d(TAG,"$TAG1 : showAdmobInterstitialAd onInterstitialAdLoaded : Bundle")
//
//                tapMindsMediationAdapter.showInterstitialAd(requestParameters,context as Activity,object : MaxInterstitialAdapterListener{
//                    override fun onInterstitialAdLoaded() {
//
//                    }
//
//                    override fun onInterstitialAdLoaded(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdLoadFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onInterstitialAdDisplayed() {
//
//                    }
//
//                    override fun onInterstitialAdDisplayed(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdClicked() {
//
//                    }
//
//                    override fun onInterstitialAdClicked(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdHidden() {
//
//                    }
//
//                    override fun onInterstitialAdHidden(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//                    }
//
//                })
//
//            }
//
//            override fun onInterstitialAdLoadFailed(p0: MaxAdapterError?) {
//                Log.d(TAG,"$TAG1 : showAdmobInterstitialAd onInterstitialAdLoadFailed")
//            }
//
//            override fun onInterstitialAdDisplayed() {
//
//            }
//
//            override fun onInterstitialAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onInterstitialAdClicked() {
//
//            }
//
//            override fun onInterstitialAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onInterstitialAdHidden() {
//
//            }
//
//            override fun onInterstitialAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//        })
    }

    fun showAdmobRewardedAd(context: Context){
        val requestParameters = getRequestParameters("ca-app-pub-3940256099942544/5224354917",Bundle().apply {  putString(adsnetwork, admob) })
//        tapMindsMediationAdapter.loadRewardedAd(requestParameters,context as Activity,object : MaxRewardedAdapterListener {
//            override fun onRewardedAdLoaded() {
//                Log.d(TAG,"$TAG1 : showAdmobRewardedAd onRewardedAdLoaded")
//            }
//
//            override fun onRewardedAdLoaded(p0: Bundle?) {
//                Log.d(TAG,"$TAG1 : showAdmobRewardedAd onRewardedAdLoaded : Bundle")
//                tapMindsMediationAdapter.showRewardedAd(requestParameters,context as Activity,object : MaxRewardedAdapterListener{
//                    override fun onRewardedAdLoaded() {
//
//                    }
//
//                    override fun onRewardedAdLoaded(p0: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdLoadFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onRewardedAdDisplayed() {
//
//                    }
//
//                    override fun onRewardedAdDisplayed(p0: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdClicked() {
//
//                    }
//
//                    override fun onRewardedAdClicked(p0: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdHidden() {
//
//                    }
//
//                    override fun onRewardedAdHidden(p0: Bundle?) {
//
//                    }
//
//                    override fun onUserRewarded(p0: MaxReward?) {
//
//                    }
//
//                    override fun onUserRewarded(p0: MaxReward?, p1: Bundle?) {
//
//                    }
//
//                })
//            }
//
//            override fun onRewardedAdLoadFailed(p0: MaxAdapterError?) {
//                Log.d(TAG,"$TAG1 : showAdmobRewardedAd onRewardedAdLoadFailed")
//            }
//
//            override fun onRewardedAdDisplayed() {
//
//            }
//
//            override fun onRewardedAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//
//            override fun onRewardedAdClicked() {
//
//            }
//
//            override fun onRewardedAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onRewardedAdHidden() {
//
//            }
//
//            override fun onRewardedAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onUserRewarded(p0: MaxReward?) {
//
//            }
//
//            override fun onUserRewarded(p0: MaxReward?, p1: Bundle?) {
//
//            }
//
//        })
    }

    fun showAdmobAppOpenAd(context: Context){
        val requestParameters = getRequestParameters("ca-app-pub-3940256099942544/9257395921",Bundle().apply {  putString(adsnetwork, admob) })
//        tapMindsMediationAdapter.loadAppOpenAd(requestParameters,context as Activity,object : MaxAppOpenAdapterListener {
//            override fun onAppOpenAdLoaded() {
//                Log.d(TAG,"$TAG1 : showAdmobAppOpenAd onAppOpenAdLoaded")
//            }
//
//            override fun onAppOpenAdLoaded(p0: Bundle?) {
//                Log.d(TAG,"$TAG1 : showAdmobAppOpenAd onAppOpenAdLoaded : Bundle")
//                tapMindsMediationAdapter.showAppOpenAd(requestParameters,context as Activity,object: MaxAppOpenAdapterListener{
//                    override fun onAppOpenAdLoaded() {
//
//                    }
//
//                    override fun onAppOpenAdLoaded(p0: Bundle?) {
//
//                    }
//
//                    override fun onAppOpenAdLoadFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onAppOpenAdDisplayed() {
//
//                    }
//
//                    override fun onAppOpenAdDisplayed(p0: Bundle?) {
//
//                    }
//
//                    override fun onAppOpenAdClicked() {
//
//                    }
//
//                    override fun onAppOpenAdClicked(p0: Bundle?) {
//
//                    }
//
//                    override fun onAppOpenAdHidden() {
//
//                    }
//
//                    override fun onAppOpenAdHidden(p0: Bundle?) {
//
//                    }
//
//                    override fun onAppOpenAdDisplayFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onAppOpenAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//                    }
//
//                })
//
//            }
//
//            override fun onAppOpenAdLoadFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onAppOpenAdDisplayed() {
//
//            }
//
//            override fun onAppOpenAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onAppOpenAdClicked() {
//
//            }
//
//            override fun onAppOpenAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onAppOpenAdHidden() {
//
//            }
//
//            override fun onAppOpenAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onAppOpenAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onAppOpenAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//
//        })
    }

    fun showFbInterstitialAd(context: Context){
        val requestParameters = getRequestParameters("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",Bundle().apply {  putString(adsnetwork, facebook) })
//        tapMindsMediationAdapter.loadInterstitialAd(requestParameters,context as Activity,object : MaxInterstitialAdapterListener {
//            override fun onInterstitialAdLoaded() {
//                Log.d(TAG,"$TAG1 : showFbInterstitialAd onInterstitialAdLoaded")
//                tapMindsMediationAdapter.showInterstitialAd(requestParameters,context as Activity,object : MaxInterstitialAdapterListener{
//                    override fun onInterstitialAdLoaded() {
//
//                    }
//
//                    override fun onInterstitialAdLoaded(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdLoadFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onInterstitialAdDisplayed() {
//
//                    }
//
//                    override fun onInterstitialAdDisplayed(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdClicked() {
//
//                    }
//
//                    override fun onInterstitialAdClicked(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdHidden() {
//
//                    }
//
//                    override fun onInterstitialAdHidden(p0: Bundle?) {
//
//                    }
//
//                    override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//                    }
//
//                })
//            }
//
//            override fun onInterstitialAdLoaded(p0: Bundle?) {
//                Log.d(TAG,"$TAG1 : showFbInterstitialAd onInterstitialAdLoaded : Bundle")
//
//
//
//            }
//
//            override fun onInterstitialAdLoadFailed(p0: MaxAdapterError?) {
//                Log.d(TAG,"$TAG1 : showFbInterstitialAd onInterstitialAdLoadFailed")
//            }
//
//            override fun onInterstitialAdDisplayed() {
//
//            }
//
//            override fun onInterstitialAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onInterstitialAdClicked() {
//
//            }
//
//            override fun onInterstitialAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onInterstitialAdHidden() {
//
//            }
//
//            override fun onInterstitialAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onInterstitialAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//        })
    }

    fun showFbRewardedAd(context: Context){
        val requestParameters = getRequestParameters("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",Bundle().apply {  putString(adsnetwork, facebook) })
//        tapMindsMediationAdapter.loadRewardedAd(requestParameters,context as Activity,object : MaxRewardedAdapterListener {
//            override fun onRewardedAdLoaded() {
//                Log.d(TAG,"$TAG1 : showFbRewardedAd onRewardedAdLoaded")
//                tapMindsMediationAdapter.showRewardedAd(requestParameters,context as Activity,object : MaxRewardedAdapterListener{
//                    override fun onRewardedAdLoaded() {
//
//                    }
//
//                    override fun onRewardedAdLoaded(p0: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdLoadFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onRewardedAdDisplayed() {
//
//                    }
//
//                    override fun onRewardedAdDisplayed(p0: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?) {
//
//                    }
//
//                    override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdClicked() {
//
//                    }
//
//                    override fun onRewardedAdClicked(p0: Bundle?) {
//
//                    }
//
//                    override fun onRewardedAdHidden() {
//
//                    }
//
//                    override fun onRewardedAdHidden(p0: Bundle?) {
//
//                    }
//
//                    override fun onUserRewarded(p0: MaxReward?) {
//
//                    }
//
//                    override fun onUserRewarded(p0: MaxReward?, p1: Bundle?) {
//
//                    }
//
//                })
//            }
//
//            override fun onRewardedAdLoaded(p0: Bundle?) {
//                Log.d(TAG,"$TAG1 : showFbRewardedAd onRewardedAdLoaded : Bundle")
//
//            }
//
//            override fun onRewardedAdLoadFailed(p0: MaxAdapterError?) {
//                Log.d(TAG,"$TAG1 : showFbRewardedAd onRewardedAdLoadFailed")
//            }
//
//            override fun onRewardedAdDisplayed() {
//
//            }
//
//            override fun onRewardedAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onRewardedAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//
//            override fun onRewardedAdClicked() {
//
//            }
//
//            override fun onRewardedAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onRewardedAdHidden() {
//
//            }
//
//            override fun onRewardedAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onUserRewarded(p0: MaxReward?) {
//
//            }
//
//            override fun onUserRewarded(p0: MaxReward?, p1: Bundle?) {
//
//            }
//
//        })
    }

    fun showAdmobBannerAd(activity: Activity, loader: ProgressBar, adContainer: FrameLayout) {
        loader.visibility = View.VISIBLE
        val requestParameters = getRequestParameters("ca-app-pub-3940256099942544/9214589741",Bundle().apply {
            putString(adsnetwork, admob)
        })
//        tapMindsMediationAdapter.loadAdViewAd(requestParameters, MaxAdFormat.BANNER,activity,object : MaxAdViewAdapterListener{
//            override fun onAdViewAdLoaded(p0: View?) {
//                Log.d(TAG,"$TAG1 : showAdmobBannerAd onAdViewAdLoaded")
//            }
//
//            override fun onAdViewAdLoaded(p0: View?, p1: Bundle?) {
//                Log.d(TAG,"$TAG1 : showAdmobBannerAd onAdViewAdLoaded : Bundle")
//                adContainer.removeAllViews()
//                adContainer.addView(p0)
//                loader.visibility = View.GONE
//            }
//
//            override fun onAdViewAdLoadFailed(p0: MaxAdapterError?) {
//                Log.d(TAG,"$TAG1 : showAdmobBannerAd onAdViewAdLoadFailed")
//                loader.visibility = View.GONE
//            }
//
//            override fun onAdViewAdDisplayed() {
//
//            }
//
//            override fun onAdViewAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onAdViewAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//
//            override fun onAdViewAdClicked() {
//
//            }
//
//            override fun onAdViewAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdHidden() {
//
//            }
//
//            override fun onAdViewAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdExpanded() {
//
//            }
//
//            override fun onAdViewAdExpanded(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdCollapsed() {
//
//            }
//
//            override fun onAdViewAdCollapsed(p0: Bundle?) {
//
//            }
//
//        })
    }

    fun showAdmobNativeAd(activity: Activity, loader: ProgressBar, adContainer: FrameLayout) {

        loader.visibility = View.VISIBLE
        val requestParameters = getRequestParameters("ca-app-pub-3940256099942544/2247696110",Bundle().apply {
            putString(adsnetwork, admob)
        })

//        tapMindsMediationAdapter.loadNativeAd(requestParameters,activity,object : MaxNativeAdAdapterListener {
//            override fun onNativeAdLoaded(ad: MaxNativeAd?, p1: Bundle?) {
//                Log.d(TAG,"$TAG1 : showAdmobNativeAd onNativeAdLoaded : Bundle")
//
//
//
//                adContainer.removeAllViews()
//                val adView = activity.layoutInflater.inflate(R.layout.layout_native_ad, adContainer, false)
//                val title = adView.findViewById<TextView>(R.id.ad_headline)
//                val body = adView.findViewById<TextView>(R.id.ad_body)
//                val icon = adView.findViewById<ImageView>(R.id.ad_icon)
//                val cta = adView.findViewById<Button>(R.id.ad_cta)
//                val mediaContainer = adView.findViewById<FrameLayout>(R.id.ad_media)
//                // Bind your ad data
//                title.text = ad?.title
//                body.text = ad?.body
//                cta.text = ad?.callToAction
//                icon.setImageDrawable(ad?.icon?.drawable)
//                ad?.mediaView?.let {
//                    mediaContainer.addView(it)
//                }
//                adContainer.addView(adView)
//
//                loader.visibility = View.GONE
//            }
//
//            override fun onNativeAdLoadFailed(p0: MaxAdapterError?) {
//                loader.visibility = View.GONE
//                Log.d(TAG,"$TAG1 : showAdmobNativeAd onNativeAdLoadFailed")
//            }
//
//            override fun onNativeAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onNativeAdClicked() {
//
//            }
//
//            override fun onNativeAdClicked(p0: Bundle?) {
//
//            }
//
//        })

    }

    fun showFbBannerAd(activity: Activity, loader: ProgressBar, adContainer: FrameLayout) {
        loader.visibility = View.VISIBLE
        val requestParameters = getRequestParameters("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",Bundle().apply {
            putString(adsnetwork, facebook)
        })

//        tapMindsMediationAdapter.loadAdViewAd(requestParameters, MaxAdFormat.BANNER,activity,object : MaxAdViewAdapterListener{
//            override fun onAdViewAdLoaded(p0: View?) {
//                Log.d(TAG,"$TAG1 : showFbBannerAd onAdViewAdLoaded")
//                adContainer.removeAllViews()
//                adContainer.addView(p0)
//                loader.visibility = View.GONE
//            }
//
//            override fun onAdViewAdLoaded(p0: View?, p1: Bundle?) {
//                Log.d(TAG,"$TAG1 : showFbBannerAd onAdViewAdLoaded : Bundle")
//            }
//
//            override fun onAdViewAdLoadFailed(p0: MaxAdapterError?) {
//                loader.visibility = View.GONE
//                Log.d(TAG,"$TAG1 : showFbBannerAd onAdViewAdLoadFailed")
//            }
//
//            override fun onAdViewAdDisplayed() {
//
//            }
//
//            override fun onAdViewAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdDisplayFailed(p0: MaxAdapterError?) {
//
//            }
//
//            override fun onAdViewAdDisplayFailed(p0: MaxAdapterError?, p1: Bundle?) {
//
//            }
//
//            override fun onAdViewAdClicked() {
//
//            }
//
//            override fun onAdViewAdClicked(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdHidden() {
//
//            }
//
//            override fun onAdViewAdHidden(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdExpanded() {
//
//            }
//
//            override fun onAdViewAdExpanded(p0: Bundle?) {
//
//            }
//
//            override fun onAdViewAdCollapsed() {
//
//            }
//
//            override fun onAdViewAdCollapsed(p0: Bundle?) {
//
//            }
//
//        })
    }

    fun showFbNativeAd(activity: Activity, loader: ProgressBar, adContainer: FrameLayout) {
        loader.visibility = View.VISIBLE
        val requestParameters = getRequestParameters("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",Bundle().apply {
            putString(adsnetwork, facebook)
        })
//        tapMindsMediationAdapter.loadNativeAd(requestParameters,activity,object : MaxNativeAdAdapterListener {
//            override fun onNativeAdLoaded(ad: MaxNativeAd?, p1: Bundle?) {
//
//                activity.runOnUiThread {
//                    Log.d(TAG, "$TAG1 : showFbNativeAd onNativeAdLoaded : Bundle")
//                    loader.visibility = View.GONE
//                    adContainer.removeAllViews()
//                    val adView = activity.layoutInflater.inflate(R.layout.layout_native_ad, adContainer, false)
//                    val title = adView.findViewById<TextView>(R.id.ad_headline)
//                    val body = adView.findViewById<TextView>(R.id.ad_body)
//                    val icon = adView.findViewById<ImageView>(R.id.ad_icon)
//                    val cta = adView.findViewById<Button>(R.id.ad_cta)
//                    val mediaContainer = adView.findViewById<FrameLayout>(R.id.ad_media)
//
//                    title.text = ad?.title
//                    body.text = ad?.body
//                    cta.text = ad?.callToAction
//                    icon.setImageDrawable(ad?.icon?.drawable)
//
//                    ad?.mediaView?.let {
//                        mediaContainer.addView(it)
//                    }
//
//                    adContainer.addView(adView)
//                }
//            }
//
//            override fun onNativeAdLoadFailed(p0: MaxAdapterError?) {
//                loader.visibility = View.GONE
//                Log.d(TAG,"$TAG1 : showFbNativeAd onNativeAdLoadFailed")
//            }
//
//            override fun onNativeAdDisplayed(p0: Bundle?) {
//
//            }
//
//            override fun onNativeAdClicked() {
//
//            }
//
//            override fun onNativeAdClicked(p0: Bundle?) {
//
//            }
//
//        })
    }

    fun getRequestParameters(thirdPartyAdPlacementId : String,serverParameters : Bundle): MaxAdapterResponseParameters {
        return object : MaxAdapterResponseParameters{
            override fun getAdUnitId(): String  = "Demo_AdUnit"

            override fun getLocalExtraParameters():  MutableMap<String, Any> = mutableMapOf()

            override fun getServerParameters():  Bundle = serverParameters

            override fun getCustomParameters(): Bundle = Bundle()

            override fun hasUserConsent(): Boolean? = true

            override fun isAgeRestrictedUser(): Boolean? = false

            override fun isDoNotSell():Boolean? = false

            override fun getConsentString():String? = null

            override fun isTesting(): Boolean  = true

            override fun getThirdPartyAdPlacementId(): String  = thirdPartyAdPlacementId

            override fun getBidResponse(): String = ""

            override fun getBidExpirationMillis(): Long  = 0L

        }
    }

}