package com.tapmind

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tapmind.databinding.AdmobNativeAdBinding

class Admob {

    val TAG = "APP@@@"
//    val TAG = "TapMindAdapterAdmob"
    val TAG1 = "Admob"
    var interstitialAd: InterstitialAd? = null

    fun init(context: Context) {
        MobileAds.initialize(context) {
            val statusMap = it.adapterStatusMap
            for ((adapterClass, status) in statusMap) {
                Log.d(
                    TAG,
                    "Adapter name: $adapterClass, Description: ${status.description}, Latency: ${status.latency}"
                )
            }
//            Log.d(TAG,"$TAG1 : "+it.adapterStatusMap.toString())
        }
    }

    fun showAdmobBannerAd(context: Context, progressBar: ProgressBar, adContainer: FrameLayout) {

        progressBar.visibility = View.VISIBLE

        val adView = AdView(context)
        adView.adUnitId = "ca-app-pub-7450680965442270/1794874535"
//        adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
        adView.setAdSize(AdSize.BANNER)
        adContainer.removeAllViews()
        adContainer.addView(adView)

        adView.adListener =
            object : AdListener() {
                override fun onAdClicked() {
                    Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdClicked")
                }

                override fun onAdClosed() {
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    progressBar.visibility = View.GONE
                    Log.d(
                        TAG,
                        "$TAG1 : showAdmobBannerAd onAdViewAdLoadFailed : " + adError.responseInfo
                    )
                }

                override fun onAdImpression() {
                    Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdImpression")
                }

                override fun onAdLoaded() {
                    Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdLoaded")
                    progressBar.visibility = View.GONE
                }

                override fun onAdOpened() {
                    Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdOpened")
                }
            }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun showAdmobInterstitialAd(context: Activity) {

        val adRequest = AdRequest.Builder().build()

        val adID = "ca-app-pub-7450680965442270/1478768049"
//        val adID = "ca-app-pub-3940256099942544/1033173712"

        InterstitialAd.load(
            context, adID, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "$TAG1 : showAdmobInterstitialAd onAdLoaded")

                    if (context.isFinishing || context.isDestroyed) {
                        Log.d(TAG, "Activity not valid")
                        return
                    }

                    interstitialAd = ad

                    interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            Log.d(TAG, "$TAG1 : showAdmobInterstitialAd onAdClicked : ")
                            super.onAdClicked()
                        }

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            interstitialAd = null
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobInterstitialAd onAdDismissedFullScreenContent : "
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            interstitialAd = null
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobInterstitialAd onAdFailedToShowFullScreenContent : " + p0.code + " " + p0.message
                            )
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Log.d(TAG, "$TAG1 : showAdmobInterstitialAd onAdImpression : ")
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobInterstitialAd onAdShowedFullScreenContent : "
                            )
                        }
                    }
                    Log.d(TAG, "Attempting to show ad, interstitialAd is null: ${interstitialAd == null}")
                    Log.d(TAG, "About to show ad...")
                    try {
                        interstitialAd?.show(context)
                        Log.d(TAG, "Ad show() called successfully")
                    } catch (e: Exception) {
                        Log.e(TAG, "Exception showing ad: ${e.message}", e)
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(
                        TAG,
                        "$TAG1 : showAdmobInterstitialAd onAdFailedToLoad : " + adError.responseInfo
                    )
                }
            },
        )
    }

    fun showAdmobRewardedAd(context: Context) {

        val adRequest = AdRequest.Builder().build()

        adRequest.isTestDevice(context)

        RewardedAd.load(
            context, "ca-app-pub-7450680965442270/2233446514", adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "$TAG1 : showAdmobRewardAd onAdLoaded")

                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobRewardAd onAdDismissedFullScreenContent : "
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobRewardAd onAdFailedToShowFullScreenContent : " + p0.code + " " + p0.message
                            )
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Log.d(TAG, "$TAG1 : showAdmobRewardAd onAdImpression : ")
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            Log.d(TAG, "$TAG1 : showAdmobRewardAd onAdShowedFullScreenContent : ")
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            Log.d(TAG, "$TAG1 : showAdmobRewardAd onAdClicked : ")
                        }

                    }
                    ad.show(context as Activity) { p0 ->
                        Log.d(
                            TAG,
                            "$TAG1 : showAdmobRewardAd onUserEarnedReward : " + p0.type + "" + p0.amount
                        )
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(
                        TAG,
                        "$TAG1 : showAdmobRewardAd onAdFailedToLoad : " + adError.responseInfo
                    )

                }
            },
        )

    }

    fun showAdmobAppOpenAd(context: Context) {
        AppOpenAd.load(
            context,
            "ca-app-pub-7450680965442270/4141424552",
            AdRequest.Builder().build(),
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    Log.d(TAG, "$TAG1 : showAdmobAppOpenAd onAdLoaded")

                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobAppOpenAd onAdDismissedFullScreenContent : "
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobAppOpenAd onAdFailedToShowFullScreenContent : " + p0.code + " " + p0.message
                            )
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Log.d(TAG, "$TAG1 : showAdmobAppOpenAd onAdImpression : ")
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            Log.d(TAG, "$TAG1 : showAdmobAppOpenAd onAdShowedFullScreenContent : ")
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            Log.d(TAG, "$TAG1 : showAdmobAppOpenAd onAdClicked : ")
                        }

                    }


                    ad.show(context as Activity)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(
                        TAG,
                        "$TAG1 : showAdmobAppOpenAd onAdFailedToLoad : " + adError.responseInfo
                    )
                }
            },
        )

    }

    fun showAdmobNativeAd(
        context: Context,
        progressBar: ProgressBar,
        nativeAdContainer: FrameLayout
    ) {

        progressBar.visibility = View.VISIBLE

        val adLoader =
            AdLoader.Builder(context, "ca-app-pub-7450680965442270/8599544313")
                .forNativeAd { nativeAd ->
                    Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdLoaded")
                    progressBar.visibility = View.GONE

                    val inflater = LayoutInflater.from(context)
                    val adView = AdmobNativeAdBinding.inflate(inflater)
                    nativeAdContainer.removeAllViews()
                    nativeAdContainer.addView(adView.root)
                    populateNativeAdView(nativeAd, adView)

                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        progressBar.visibility = View.GONE
                        Log.d(
                            TAG,
                            "$TAG1 : showAdmobNativeAd onAdFailedToLoad : " + adError.responseInfo
                        )
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                        Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdClicked")
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdClosed")
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdImpression")
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdLoaded")
                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
                        Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdOpened")
                    }

                    override fun onAdSwipeGestureClicked() {
                        super.onAdSwipeGestureClicked()
                        Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdSwipeGestureClicked")
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()

        adLoader.loadAd(AdRequest.Builder().build())

    }


    private fun populateNativeAdView(nativeAd: NativeAd, unifiedAdBinding: AdmobNativeAdBinding) {
        val nativeAdView = unifiedAdBinding.root

        // Set the media view.
        nativeAdView.mediaView = unifiedAdBinding.adMedia

        // Set other ad assets.
        nativeAdView.headlineView = unifiedAdBinding.adHeadline
        nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
        nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
        nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        unifiedAdBinding.adHeadline.text = nativeAd.headline

        nativeAd.mediaContent?.let {
            unifiedAdBinding.adMedia.mediaContent = it
            unifiedAdBinding.adMedia.visibility = View.VISIBLE
        } ?: {
            unifiedAdBinding.adMedia.visibility = View.GONE
        }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            unifiedAdBinding.adBody.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.GONE
            unifiedAdBinding.adBody.text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            unifiedAdBinding.adCallToAction.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        } else {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon?.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            unifiedAdBinding.adPrice.visibility = View.GONE
        } else {
            unifiedAdBinding.adPrice.visibility = View.GONE
            unifiedAdBinding.adPrice.text = nativeAd.price
        }

        if (nativeAd.store == null) {
            unifiedAdBinding.adStore.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStore.visibility = View.VISIBLE
            unifiedAdBinding.adStore.text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            unifiedAdBinding.adStars.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            unifiedAdBinding.adAdvertiser.visibility = View.INVISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
//        val mediaContent = nativeAd.mediaContent
//        val vc = mediaContent?.videoController

        // Updates the UI to say whether or not this ad has a video asset.
//        if (vc != null && mediaContent.hasVideoContent()) {
//            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
//            // VideoController will call methods on this object when events occur in the video
//            // lifecycle.
//            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
//                override fun onVideoEnd() {
//                    super.onVideoEnd()
//                }
//            }
//        }
    }
}