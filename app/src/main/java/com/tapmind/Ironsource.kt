package com.tapmind

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
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

    //    private val IRONSOURCE_INTERSTITIAL_AD_UNIT_ID = "aeyqi3vqlv6o8sh9"
    private val IRONSOURCE_INTERSTITIAL_AD_UNIT_ID = "uhqpizf13ghg479b"

    //    private  val IRONSOURCE_BANNER_AD_UNIT_ID = "thnfvcsog13bhn08"
//    private val IRONSOURCE_BANNER_AD_UNIT_ID = "42nw51p9eh0jaioh"
//    private val IRONSOURCE_REWARDED_AD_UNIT_ID = "76yy3nay3ceui2a3"

    private val IRONSOURCE_REWARDED_AD_UNIT_ID = "p8em4563fv6hu68t"
    private val IRONSOURCE_BANNER_AD_UNIT_ID = "z99pgob38ju6y3wh"
    private val IRONSOURCE_NATIVE_AD_UNIT_ID = "gmhi28kyr3gqnn7d"

    fun showIronsourceInterstitialAd(context: Context, progressBar: ProgressBar) {
        val mInterstitialAd = LevelPlayInterstitialAd(IRONSOURCE_INTERSTITIAL_AD_UNIT_ID)
        mInterstitialAd.setListener(object : LevelPlayInterstitialAdListener {

            override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {

                Log.d(TAG, "LevelPlay Interstitial : onAdLoaded")
                progressBar.visibility = View.GONE
                Log.e(TAG, "isAdReady: ${mInterstitialAd.isAdReady}")
                Log.e(TAG, "isAdReady: $levelPlayAdInfo")

                // Add a small delay before showing
                Handler(Looper.getMainLooper()).postDelayed({
                    if (mInterstitialAd.isAdReady) {
                        Log.d(TAG, "Showing ad with activity: ${context::class.java.simpleName}")
                        if (context is Activity && !context.isFinishing && !context.isDestroyed) {
                            mInterstitialAd.showAd(context)
                        } else {
                            Log.e(TAG, "Activity is not valid")
                        }
                    } else {
                        Log.e(TAG, "Ad is not ready to show")
                    }
                }, 100)
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
                progressBar.visibility = View.GONE
            }

            override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {

            }

            override fun onAdDisplayFailed(
                error: LevelPlayAdError,
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
//
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
        val adSize = LevelPlayAdSize.createAdaptiveAdSize(context)
        val adConfig = LevelPlayBannerAdView.Config.Builder()
            .setAdSize(adSize!!)
            .setPlacementName("DefaultBanner")
            .build()

        val levelPlayBanner = LevelPlayBannerAdView(context, IRONSOURCE_BANNER_AD_UNIT_ID, adConfig)

        adContainer.removeAllViews()
        adContainer.addView(levelPlayBanner)
        adContainer.visibility = View.VISIBLE

        adContainer.post {
            adContainer.removeAllViews()

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                50.dpToPx(context)
            )
            levelPlayBanner.layoutParams = layoutParams

            adContainer.addView(levelPlayBanner)
        }
        levelPlayBanner.bannerListener = object : LevelPlayBannerAdViewListener {
            override fun onAdLoaded(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "onAdLoaded: adContainer")
                progressBar.visibility = View.GONE
            }

            override fun onAdLoadFailed(error: LevelPlayAdError) {
                Log.d(TAG, "onAdLoadFailed: adContainer ${error.errorMessage}")
                Log.d(TAG, "onAdLoadFailed: adContainer ${error.errorCode}")
            }

            override fun onAdDisplayed(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "onAdDisplayed: adContainer")
            }

            override fun onAdDisplayFailed(adInfo: LevelPlayAdInfo, error: LevelPlayAdError) {
                Log.d(TAG, "onAdDisplayFailed: adContainer")
            }

            override fun onAdClicked(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "onAdClicked: adContainer")
            }

            override fun onAdExpanded(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "onAdExpanded: adContainer")
            }

            override fun onAdCollapsed(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "onAdCollapsed: adContainer")
            }

            override fun onAdLeftApplication(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "onAdLeftApplication: adContainer")
            }
        }
        levelPlayBanner.loadAd()
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    fun showIronsourceNativeAd(
        context: Context,
        progressBar: ProgressBar,
        adContainer: FrameLayout
    ) {
        progressBar.visibility = View.VISIBLE
        val nativeAd = LevelPlayNativeAd.Builder()
            .withListener(object : LevelPlayNativeAdListener {
                override fun onAdLoaded(nativeAd: LevelPlayNativeAd?, adInfo: AdInfo?) {
                    InflateIronSourceNative(nativeAd, context, adContainer)
                }

                override fun onAdLoadFailed(nativeAd: LevelPlayNativeAd?, error: IronSourceError?) {
                    Toast.makeText(context, error?.errorMessage, Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Ironsource Native : onAdLoadFailed " + error?.errorMessage)
                    progressBar.visibility = View.GONE
                }

                override fun onAdClicked(nativeAd: LevelPlayNativeAd?, adInfo: AdInfo?) {
                }

                override fun onAdImpression(nativeAd: LevelPlayNativeAd?, adInfo: AdInfo?) {
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