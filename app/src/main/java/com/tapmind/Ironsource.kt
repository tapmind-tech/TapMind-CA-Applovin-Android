package com.tapmind

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.ironsource.mediationsdk.ads.nativead.LevelPlayMediaView
import com.ironsource.mediationsdk.ads.nativead.LevelPlayNativeAd
import com.ironsource.mediationsdk.ads.nativead.LevelPlayNativeAdListener
import com.ironsource.mediationsdk.ads.nativead.NativeAdLayout
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.logger.IronSourceError
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


class Ironsource {

    private val TAG = "App@@@"
    private val APP_KEY = "85460dcd"
    private val IRONSOURCE_INTERSTITIAL_AD_UNIT_ID = "aeyqi3vqlv6o8sh9"

    //    private  val IRONSOURCE_BANNER_AD_UNIT_ID = "thnfvcsog13bhn08"
    private val IRONSOURCE_BANNER_AD_UNIT_ID = "42nw51p9eh0jaioh"
    private val IRONSOURCE_REWARDED_AD_UNIT_ID = "76yy3nay3ceui2a3"

    fun showIronsourceInterstitialAd(context: Context, progressBar: ProgressBar) {
        val mInterstitialAd = LevelPlayInterstitialAd(IRONSOURCE_INTERSTITIAL_AD_UNIT_ID)
        mInterstitialAd.setListener(object : LevelPlayInterstitialAdListener {
            override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay Interstitial : onAdLoaded")
                progressBar.visibility = View.GONE
                if (mInterstitialAd.isAdReady) {
                    mInterstitialAd.showAd(context as Activity)
                }
            }

            override fun onAdLoadFailed(levelPlayAdError: LevelPlayAdError) {
                Log.d(
                    TAG,
                    "LevelPlay Interstitial : onAdLoadFailed " + levelPlayAdError.errorMessage
                )
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {

            }

            override fun onAdDisplayFailed(
                levelPlayAdError: LevelPlayAdError,
                levelPlayAdInfo: LevelPlayAdInfo
            ) {

            }

            override fun onAdClicked(levelPlayAdInfo: LevelPlayAdInfo) {

            }

            override fun onAdClosed(levelPlayAdInfo: LevelPlayAdInfo) {

            }

            override fun onAdInfoChanged(levelPlayAdInfo: LevelPlayAdInfo) {

            }
        })
        progressBar.visibility = View.VISIBLE
        mInterstitialAd.loadAd()
    }

    fun showIronsourceRewardedAd(context: Context, progressBar: ProgressBar) {
        val mRewardedAd = LevelPlayRewardedAd(IRONSOURCE_REWARDED_AD_UNIT_ID)
        mRewardedAd.setListener(object : LevelPlayRewardedAdListener {
            override fun onAdLoaded(p0: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay Rewarded : onAdLoaded")
                progressBar.visibility = View.GONE
                if (mRewardedAd.isAdReady) {
                    mRewardedAd.showAd(context as Activity)
                }
            }

            override fun onAdLoadFailed(p0: LevelPlayAdError) {
                Log.d(TAG, "LevelPlay Rewarded : onAdLoadFailed " + p0.errorMessage)
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayed(p0: LevelPlayAdInfo) {
            }

            override fun onAdRewarded(p0: LevelPlayReward, p1: LevelPlayAdInfo) {
            }
        })
        progressBar.visibility = View.VISIBLE
        mRewardedAd.loadAd()
    }

    fun showIronsourceBannerAd(
        context: Context,
        progressBar: ProgressBar,
        adContainer: FrameLayout
    ) {
        progressBar.visibility = View.VISIBLE
        val adSize = LevelPlayAdSize.BANNER
        val adConfig = LevelPlayBannerAdView.Config.Builder()
            .setAdSize(adSize)
            .setPlacementName("placementName")
            .build()
        // Create the banner view and set the ad unit id
        val levelPlayBanner = LevelPlayBannerAdView(context, IRONSOURCE_BANNER_AD_UNIT_ID, adConfig)

        adContainer.removeAllViews()
        adContainer.addView(levelPlayBanner)
        levelPlayBanner.bannerListener = object : LevelPlayBannerAdViewListener {
            override fun onAdLoaded(adInfo: LevelPlayAdInfo) {
                progressBar.visibility = View.GONE
            }

            override fun onAdLoadFailed(error: LevelPlayAdError) {
                Toast.makeText(context, error.errorMessage, Toast.LENGTH_LONG).show()
                Log.d(TAG, "LevelPlay Banner : onAdLoadFailed " + error.errorMessage)
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayed(adInfo: LevelPlayAdInfo) {
            }

            override fun onAdDisplayFailed(adInfo: LevelPlayAdInfo, error: LevelPlayAdError) {
            }

            override fun onAdClicked(adInfo: LevelPlayAdInfo) {
            }

            override fun onAdExpanded(adInfo: LevelPlayAdInfo) {
            }

            override fun onAdCollapsed(adInfo: LevelPlayAdInfo) {
            }

            override fun onAdLeftApplication(adInfo: LevelPlayAdInfo) {
            }
        }
        levelPlayBanner.pauseAutoRefresh()
        levelPlayBanner.loadAd()
    }

    fun showIronsourceNativeAd(
        context: Context,
        progressBar: ProgressBar,
        adContainer: FrameLayout
    ) {
        progressBar.visibility = View.VISIBLE
        val nativeAd = LevelPlayNativeAd.Builder()
            .withListener(object : LevelPlayNativeAdListener {
                override fun onAdLoaded(nativeAd: LevelPlayNativeAd?, p1: AdInfo?) {
                    InflateIronSourceNative(nativeAd, context, adContainer)
                }

                override fun onAdLoadFailed(p0: LevelPlayNativeAd?, p1: IronSourceError?) {
                    Toast.makeText(context, p1?.errorMessage, Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Ironsource Native : onAdLoadFailed " + p1?.errorMessage)
                    progressBar.visibility = View.GONE
                }

                override fun onAdClicked(p0: LevelPlayNativeAd?, p1: AdInfo?) {
                }

                override fun onAdImpression(p0: LevelPlayNativeAd?, p1: AdInfo?) {
                    progressBar.visibility = View.GONE
                }
            }).build()
        nativeAd.loadAd()
    }

    fun InflateIronSourceNative(
        nativeAd: LevelPlayNativeAd?,
        activity: Context,
        adContainer: FrameLayout
    ) {

        val adLayout =
            (activity as Activity).layoutInflater.inflate(R.layout.ironsource_native_ad, null)
        val nativeAdLayout = adLayout.findViewById<NativeAdLayout>(R.id.native_ad_layout)

        // Bind your views
        val badge = adLayout.findViewById<TextView>(R.id.badge)
        val titleView = adLayout.findViewById<TextView>(R.id.ad_title)
        val bodyView = adLayout.findViewById<TextView>(R.id.ad_body)
        val iconView = adLayout.findViewById<ImageView>(R.id.ad_app_icon)
        val ctaButton = adLayout.findViewById<Button>(R.id.ad_call_to_action)
        val mediaContainer = adLayout.findViewById<LevelPlayMediaView>(R.id.ad_media)

        badge.text = nativeAd?.advertiser ?: "Sponsored"
        titleView.text = nativeAd?.title
        bodyView.text = nativeAd?.body
        ctaButton.text = nativeAd?.callToAction
        iconView.setImageDrawable(nativeAd?.icon?.drawable)

        nativeAdLayout.setTitleView(titleView)
        nativeAdLayout.setBodyView(bodyView)
        nativeAdLayout.setIconView(iconView)
        nativeAdLayout.setMediaView(mediaContainer)
        nativeAdLayout.setCallToActionView(ctaButton)
        nativeAdLayout.setAdvertiserView(badge)

        nativeAd?.let { nativeAdLayout.registerNativeAdViews(it) }

        adContainer.removeAllViews()
        adContainer.addView(adLayout)

    }
}