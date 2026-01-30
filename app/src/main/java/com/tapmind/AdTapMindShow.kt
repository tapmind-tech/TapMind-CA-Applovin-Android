package com.tapmind

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tapmimd.ads.mediation.adapter.TapMindSdk
import com.tapmind.databinding.ActivityAdTapMindShowBinding
import com.tapmind.databinding.AdmobNativeAdBinding

class AdTapMindShow : AppCompatActivity() {

    private lateinit var binding: ActivityAdTapMindShowBinding
    private val TAG = "APP@@@"
    val TAG1 = "Admob"
    private val ironsource: Ironsource = Ironsource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdTapMindShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val adType = intent.getStringExtra("adType")
//
//        when (adType) {
//            "Banner" -> showAdmobBannerAd(this, binding.adContainer)
//            "Native" -> showAdmobNativeAd(this, binding.adContainer)
//            "Interstitial" -> showAdmobInterstitialAd(this)
//            "Reward" -> showAdmobRewardedAd(this)
//        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        TapMindSdk.initIronSource(
            context = this,
            apiKey = "aeyqi3vqlv6o8sh9",
            userId = "aeyqi3vqlv6o8sh9",
            onCallBack = { bool ->
                ironsource.showIronsourceInterstitialAd(this, binding.progressBar)
                if (bool) {
                    ironsource.showIronsourceInterstitialAd(this, binding.progressBar)
                }
            })
    }

    fun showAdmobInterstitialAd(context: Activity) {
        val adRequest = AdRequest.Builder().build()

//        val adID = "ca-app-pub-7450680965442270/1478768049"
        val adID = "ca-app-pub-3940256099942544/1033173712"

        InterstitialAd.load(
            context, adID, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "$TAG1 : showAdmobInterstitialAd onAdLoaded")

                    if (context.isFinishing || context.isDestroyed) {
                        Log.d(TAG, "Activity not valid")
                        return
                    }


                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            Log.d(TAG, "$TAG1 : showAdmobInterstitialAd onAdClicked : ")
                            super.onAdClicked()
                        }

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobInterstitialAd onAdDismissedFullScreenContent : "
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
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
                    Log.d(TAG, "About to show ad...")
                    try {
                        ad.show(context)
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
                                TAG, "$TAG1 : showAdmobRewardAd onAdDismissedFullScreenContent : "
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
                        TAG, "$TAG1 : showAdmobRewardAd onAdFailedToLoad : " + adError.responseInfo
                    )

                }
            },
        )

    }

    fun showAdmobNativeAd(
        context: Context, nativeAdContainer: FrameLayout
    ) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-7450680965442270/8599544313")
            .forNativeAd { nativeAd ->
                Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdLoaded")

                val inflater = LayoutInflater.from(context)
                val adView = AdmobNativeAdBinding.inflate(inflater)
                nativeAdContainer.removeAllViews()
                nativeAdContainer.addView(adView.root)
                populateNativeAdView(nativeAd, adView)

            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(
                        TAG, "$TAG1 : showAdmobNativeAd onAdFailedToLoad : " + adError.responseInfo
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
            }).withNativeAdOptions(NativeAdOptions.Builder().build()).build()

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

        nativeAdView.setNativeAd(nativeAd)
    }

    fun showAdmobBannerAd(context: Context, adContainer: FrameLayout) {

        val adView = AdView(context)
        adView.adUnitId = "ca-app-pub-7450680965442270/1794874535"
//        adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
        adView.setAdSize(AdSize.BANNER)
        adContainer.removeAllViews()
        adContainer.addView(adView)

        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdClicked")
            }

            override fun onAdClosed() {
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(
                    TAG, "$TAG1 : showAdmobBannerAd onAdViewAdLoadFailed : " + adError.responseInfo
                )
            }

            override fun onAdImpression() {
                Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdImpression")
            }

            override fun onAdLoaded() {
                Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdLoaded")
            }

            override fun onAdOpened() {
                Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdOpened")
            }
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}