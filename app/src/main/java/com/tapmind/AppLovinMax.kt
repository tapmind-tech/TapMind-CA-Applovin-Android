package com.tapmind

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder

class AppLovinMax {

    private val TAG = "APP@@@"
    private val AppLovinMax_INTERSTITIAL_AD_UNIT_ID = "test_interstitial"
    private val AppLovinMax_BANNER_AD_UNIT_ID = "test_banner"
    private val AppLovinMax_NATIVE_AD_UNIT_ID = "test_native"
    private val AppLovinMax_REWARDED_AD_UNIT_ID = "test_rewarded"

    fun showAppLovinMaxInterstitialAd(context: Context, progressBar: ProgressBar) {
        val interstitialAd = MaxInterstitialAd(AppLovinMax_INTERSTITIAL_AD_UNIT_ID, context)
        interstitialAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                progressBar.visibility = View.GONE
                if (interstitialAd.isReady) {
                    interstitialAd.showAd()
                }
            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(p0: MaxAd) {
            }

            override fun onAdClicked(p0: MaxAd) {
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
            }
        })
        progressBar.visibility = View.VISIBLE
        interstitialAd.loadAd()
    }

    fun showAppLovinMaxRewardedAd(context: Context, progressBar: ProgressBar) {
        val rewardedAd = MaxRewardedAd.getInstance(AppLovinMax_REWARDED_AD_UNIT_ID, context)
        rewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                progressBar.visibility = View.GONE
                Log.d(TAG, "AppLovin Rewarded : onAdLoaded")
                if (rewardedAd.isReady) {
                    rewardedAd.showAd()
                }
            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(p0: MaxAd) {
            }

            override fun onAdClicked(p0: MaxAd) {
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                Toast.makeText(context, p1.message, Toast.LENGTH_LONG).show()
                Log.d(TAG, "AppLovin Rewarded : onAdLoadFailed : " + p1.message)
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
            }

            override fun onUserRewarded(p0: MaxAd, p1: MaxReward) {
            }
        })
        progressBar.visibility = View.VISIBLE
        rewardedAd.loadAd()
    }

    fun showAppLovinMaxBannerAd(
        context: Context,
        progressBar: ProgressBar,
        adContainer: FrameLayout
    ) {
        progressBar.visibility = View.VISIBLE

        val adview = com.applovin.mediation.ads.MaxAdView(
            AppLovinMax_BANNER_AD_UNIT_ID,
            MaxAdFormat.BANNER,
            context
        )
//        adview.gravity = Gravity.BOTTOM
        adContainer.removeAllViews()
        adContainer.addView(adview)


        adview.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                progressBar.visibility = View.GONE
                Log.d(TAG, "Applovin Banner : onAdLoaded ")

            }

            override fun onAdDisplayed(p0: MaxAd) {

            }

            override fun onAdHidden(p0: MaxAd) {

            }

            override fun onAdClicked(p0: MaxAd) {

            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                Toast.makeText(context, p1.message, Toast.LENGTH_LONG).show()
                Log.d(TAG, "Applovin Banner : onAdLoadFailed " + p1.message)
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {

            }

            override fun onAdExpanded(p0: MaxAd) {

            }

            override fun onAdCollapsed(p0: MaxAd) {

            }

        })
        adview.loadAd()
    }


    fun showAppLovinMaxNativeAd(
        context: Context,
        progressBar: ProgressBar,
        nativeAdContainer: FrameLayout
    ) {

        progressBar.visibility = View.VISIBLE
        var nativeAdLoader = MaxNativeAdLoader(AppLovinMax_NATIVE_AD_UNIT_ID, context)

        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {

                val adView = createNativeAdView(context)

                nativeAdLoader.render(adView, ad)

                nativeAdContainer.removeAllViews()
                nativeAdContainer.addView(adView)

                progressBar.visibility = View.GONE
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                Toast.makeText(context, error?.message, Toast.LENGTH_LONG).show()
                Log.d(TAG,"Applovin Native : onAdLoadFailed "+error?.message)
                progressBar.visibility = View.GONE
            }

            override fun onNativeAdClicked(ad: MaxAd) {

            }


            override fun onNativeAdExpired(nativeAd: MaxAd) {
            }
        })
        nativeAdLoader.loadAd()
    }

    private fun createNativeAdView(context: Context): MaxNativeAdView {
        val binder: MaxNativeAdViewBinder = MaxNativeAdViewBinder.Builder(R.layout.native_custom_ad_view)
            .setTitleTextViewId(R.id.title_text_view)
            .setBodyTextViewId(R.id.body_text_view)
            .setAdvertiserTextViewId(R.id.advertiser_text_view)
            .setIconImageViewId(R.id.icon_image_view)
            .setMediaContentViewGroupId(R.id.media_view_container)
            .setOptionsContentViewGroupId(R.id.options_view)
            .setStarRatingContentViewGroupId(R.id.star_rating_view)
            .setCallToActionButtonId(R.id.cta_button)
            .build()
        return MaxNativeAdView(binder, context)
    }
}