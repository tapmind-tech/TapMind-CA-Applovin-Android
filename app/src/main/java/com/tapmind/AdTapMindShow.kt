package com.tapmind

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tapmind.databinding.ActivityAdTapMindShowBinding
import com.tapmind.databinding.AdmobNativeAdBinding

class AdTapMindShow : AppCompatActivity() {

    private lateinit var binding: ActivityAdTapMindShowBinding
    private val ironsource: Ironsource = Ironsource()
    private var maxAdView: MaxAdView? = null
    private var interstitialAppLovinAd: MaxInterstitialAd? = null
    private var rewardedAppLovinAd: MaxRewardedAd? = null
    private val TAG = "APP@@@"
    val TAG1 = "Admob"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdTapMindShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adType = intent.getStringExtra("adType").toString()
        val flow = intent.getStringExtra("flow").toString()

        binding.btnBack.setOnClickListener {
            finish()
        }
        when (flow) {
            "ironsource" -> {
                ironSourceAd(adType)
            }

            "applovin" -> {
                appLovinAd(adType)
            }

            else -> {
                adMobAd(adType)
            }
        }
    }

    private fun appLovinAd(adType: String) {
        Log.e(TAG, "appLovinAd")
        val bannerAdUnitId = "16b2cfdc0535e5f6"
        val interstitialAdUnitId = "eb08a9a43cd7954f"
        val rewardAppLovinAdUnitId = "0ded93032fd6f774"
        when (adType) {
            "Banner" -> loadBannerAppLovin(binding.adNativeContainer, bannerAdUnitId)
            "Interstitial" -> loadAppLovinInterstitial(interstitialAdUnitId)
            "Reward" -> loadAppLovinRewarded(rewardAppLovinAdUnitId)
        }
    }

    private fun adMobAd(adType: String) {
        val bannerAdId = "ca-app-pub-2967653914154128/9844069802"
//        val bannerAdId = "ca-app-pub-2967653914154128/5364787835"
//        val bannerAdId = "ca-app-pub-3940256099942544/6300978111" // <= demo

        val nativeAdId = "ca-app-pub-7450680965442270/8599544313"
//        val nativeAdId = "ca-app-pub-3940256099942544/2247696110" // <= demo

        val interstitialAdID = "ca-app-pub-7450680965442270/1478768049"
//        val interstitialAdID = "ca-app-pub-3940256099942544/1033173712"  // <= demo

//        val rewardedAdId = "ca-app-pub-7450680965442270/2233446514"
        val rewardedAdId = "ca-app-pub-2967653914154128/4199404618"
//        val rewardedAdId = "ca-app-pub-3940256099942544/5224354917" // <= demo

        when (adType) {
            "Banner" -> showAdmobBannerAd(this, binding.adNativeContainer, bannerAdId)
            "Native" -> showAdmobNativeAd(this, binding.adNativeContainer, nativeAdId)
            "Interstitial" -> showAdmobInterstitialAd(this, interstitialAdID)
            "Reward" -> showAdmobRewardedAd(this, rewardedAdId)
        }
    }

    private fun ironSourceAd(adType: String) {
        when (adType) {
            "Banner" -> ironsource.showIronsourceBannerAd(
                this, binding.progressBar, binding.adNativeContainer
            )

//            "Native" -> ironsource.showIronsourceNativeAd(
//                this,
//                binding.progressBar,
//                binding.adContainer
//            )

            "Interstitial" -> {
                ironsource.showIronsourceInterstitialAd(
                    this, binding.progressBar
                )
            }

            "Reward" -> ironsource.showIronsourceRewardedAd(this, binding.progressBar)
        }
    }

    fun showAdmobInterstitialAd(context: Activity, adId: String) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context, adId, adRequest,
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
                            finish()
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
                            binding.progressBar.visibility = View.GONE
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

    fun showAdmobRewardedAd(context: Context, adId: String) {

        val adRequest = AdRequest.Builder().build()
        Log.e(TAG, "showAdmobRewardedAd: $adId")
//        adRequest.isTestDevice(context)
        RewardedAd.load(
            context,
            adId,
            adRequest,
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

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            super.onAdFailedToShowFullScreenContent(adError)
                            Log.d(
                                TAG,
                                "$TAG1 : showAdmobRewardAd onAdFailedToShowFullScreenContent : " + adError.code + " " + adError.message
                            )
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Log.d(TAG, "$TAG1 : showAdmobRewardAd onAdImpression : ")
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            binding.progressBar.visibility = View.GONE
                            Log.d(
                                TAG, "$TAG1 : showAdmobRewardAd onAdShowedFullScreenContent : "
                            )
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            Log.d(TAG, "$TAG1 : showAdmobRewardAd onAdClicked : ")
                        }
                    }
                    ad.show(
                        this@AdTapMindShow,
                    ) { rewardItem ->
                        Log.d(TAG, "User earned the reward.")
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(
                        TAG, "$TAG1 : showAdmobRewardAd onAdFailedToLoad : " + adError.responseInfo
                    )

                    Log.e(TAG, "code: ${adError.code}")
                    Log.e(TAG, "message: ${adError.message}")
                    Log.e(TAG, "domain: ${adError.domain}")
                    Log.e(TAG, "cause: ${adError.cause}")
                }
            },
        )
    }

    fun showAdmobNativeAd(
        context: Context, nativeAdContainer: FrameLayout, adUnitId: String
    ) {
        val adLoader = AdLoader.Builder(
            context, adUnitId
        ).forNativeAd { nativeAd ->
            Log.d(TAG, "$TAG1 : showAdmobNativeAd onAdLoaded")

            nativeAdContainer.removeAllViews()
            val inflater = LayoutInflater.from(context)
            val adView = AdmobNativeAdBinding.inflate(inflater)
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
                binding.progressBar.visibility = View.GONE
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

    private fun populateNativeAdView(
        nativeAd: NativeAd, unifiedAdBinding: AdmobNativeAdBinding
    ) {
        val nativeAdView = unifiedAdBinding.root

        // IMPORTANT: Assign ALL views including ad attribution
        nativeAdView.mediaView = unifiedAdBinding.adMedia
        nativeAdView.headlineView = unifiedAdBinding.adHeadline
        nativeAdView.bodyView = unifiedAdBinding.adBody
        nativeAdView.callToActionView = unifiedAdBinding.adCallToAction
        nativeAdView.iconView = unifiedAdBinding.adAppIcon
        nativeAdView.priceView = unifiedAdBinding.adPrice
        nativeAdView.starRatingView = unifiedAdBinding.adStars
        nativeAdView.storeView = unifiedAdBinding.adStore
        nativeAdView.advertiserView = unifiedAdBinding.adAdvertiser
//        unifiedAdBinding.adAttribution.text = "Ad"
//        unifiedAdBinding.adAttribution.visibility = View.VISIBLE
        unifiedAdBinding.adHeadline.text = nativeAd.headline

        if (nativeAd.adChoicesInfo != null) {
            unifiedAdBinding.adChoices.visibility = View.VISIBLE
            unifiedAdBinding.adChoices.text = nativeAd.adChoicesInfo?.text
        } else {
            unifiedAdBinding.adChoices.visibility = View.GONE
        }

        nativeAd.mediaContent?.let {
            unifiedAdBinding.adMedia.mediaContent = it
            unifiedAdBinding.adMedia.visibility = View.VISIBLE
        } ?: run {
            unifiedAdBinding.adMedia.visibility = View.GONE
        }

        // Body
        if (nativeAd.body != null) {
            unifiedAdBinding.adBody.text = nativeAd.body
            unifiedAdBinding.adBody.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adBody.visibility = View.GONE
        }

        // Call to Action
        if (nativeAd.callToAction != null) {
            unifiedAdBinding.adCallToAction.text = nativeAd.callToAction
            unifiedAdBinding.adCallToAction.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adCallToAction.visibility = View.GONE
        }

        // Icon
        if (nativeAd.icon != null) {
            unifiedAdBinding.adAppIcon.setImageDrawable(nativeAd.icon!!.drawable)
            unifiedAdBinding.adAppIcon.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adAppIcon.visibility = View.GONE
        }

        // Price
        if (nativeAd.price != null) {
            unifiedAdBinding.adPrice.text = nativeAd.price
            unifiedAdBinding.adPrice.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adPrice.visibility = View.GONE
        }

        // Store
        if (nativeAd.store != null) {
            unifiedAdBinding.adStore.text = nativeAd.store
            unifiedAdBinding.adStore.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adStore.visibility = View.GONE
        }

        // Star Rating
        if (nativeAd.starRating != null) {
            unifiedAdBinding.adStars.rating = nativeAd.starRating!!.toFloat()
            unifiedAdBinding.adStars.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adStars.visibility = View.GONE
        }

        // Advertiser
        if (nativeAd.advertiser != null) {
            unifiedAdBinding.adAdvertiser.text = nativeAd.advertiser
            unifiedAdBinding.adAdvertiser.visibility = View.VISIBLE
        } else {
            unifiedAdBinding.adAdvertiser.visibility = View.GONE
        }

        nativeAdView.setNativeAd(nativeAd)
    }

    fun showAdmobBannerAd(context: Context, adContainer: FrameLayout, adUnitId: String) {

        val adView = AdView(context)
        adView.adUnitId = adUnitId
        adView.setAdSize(AdSize.BANNER)
        adContainer.removeAllViews()
        adContainer.addView(adView)
        Log.e(TAG, "showAdmobBannerAd: $adUnitId")

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
                binding.progressBar.visibility = View.GONE
            }

            override fun onAdOpened() {
                Log.d(TAG, "$TAG1 : showAdmobBannerAd onAdOpened")
            }
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

//        binding.adView.post {
//
////            binding.adView.adUnitId = "/6499/example/banner"
//
//            // Banner size
//            binding.adView.setAdSizes(getAdSize())
//
//            binding.adView.adListener = object : AdListener() {
//
//                override fun onAdLoaded() {
//                    Log.e(TAG, "onAdLoaded")
//                    binding.progressBar.visibility = View.GONE
//                }
//
//                override fun onAdFailedToLoad(error: LoadAdError) {
//                    Log.e(TAG, "onAdFailedToLoad: ${error.code}")
//                    Log.e(TAG, "onAdFailedToLoad: ${error.message}")
//                }
//
//                override fun onAdOpened() {
//                    Log.e(TAG, "onAdOpened")
//                }
//
//                override fun onAdClosed() {
//                    Log.e(TAG, "onAdClosed")
//                }
//            }
//
//            val request = AdManagerAdRequest.Builder().build()
//            binding.adView.loadAd(request)
//        }
    }

    fun getAdSize(): AdSize {
        val displayMetrics = resources.displayMetrics
        val density = displayMetrics.density

        // Get actual container width
        var adWidthPixels = binding.adView.width.toFloat()

        // If view not laid out yet → fallback to screen width
        if (adWidthPixels == 0f) {
            adWidthPixels = displayMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }

    fun loadBannerAppLovin(container: FrameLayout, adUnitId: String) {
        Log.e(TAG, "loadBannerAppLovin")

        container.removeAllViews()

        maxAdView = MaxAdView(adUnitId, this)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = resources.getDimensionPixelSize(R.dimen.banner_height)

        maxAdView!!.layoutParams = FrameLayout.LayoutParams(width, height)

        container.addView(maxAdView)

        maxAdView!!.setListener(object : MaxAdViewAdListener {

            override fun onAdLoaded(ad: MaxAd) {
                Log.d(TAG, "onAdLoaded")
                binding.progressBar.visibility = View.GONE
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                Log.d(TAG, "onAdLoadFailed")
            }

            override fun onAdClicked(ad: MaxAd) {
                Log.d(TAG, "onAdClicked")
            }

            override fun onAdExpanded(ad: MaxAd) {
                Log.d(TAG, "onAdExpanded")
            }

            override fun onAdCollapsed(ad: MaxAd) {
                Log.d(TAG, "onAdCollapsed")
            }

            override fun onAdDisplayed(ad: MaxAd) {
                Log.d(TAG, "onAdDisplayed")
            }

            override fun onAdHidden(ad: MaxAd) {
                Log.d(TAG, "onAdHidden")
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                Log.d(TAG, "onAdDisplayFailed")
            }
        })

        maxAdView!!.loadAd()
    }

    private fun loadAppLovinInterstitial(interstitialAdUnitId: String) {
        Log.e(TAG, "loadAppLovinInterstitial")

        interstitialAppLovinAd = MaxInterstitialAd(interstitialAdUnitId, this)

        interstitialAppLovinAd!!.setListener(object : MaxAdListener {

            override fun onAdLoaded(ad: MaxAd) {
                Log.d(TAG, "onAdLoaded")
                showInterstitial(this@AdTapMindShow)
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                Log.d(TAG, "onAdLoadFailed")
            }

            override fun onAdDisplayed(ad: MaxAd) {
                Log.d(TAG, "onAdDisplayed")
            }

            override fun onAdHidden(ad: MaxAd) {
                Log.d(TAG, "onAdHidden")
                interstitialAppLovinAd?.setListener(null)
                interstitialAppLovinAd = null
            }

            override fun onAdClicked(ad: MaxAd) {
                Log.d(TAG, "onAdClicked")
                finish()
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                Log.d(TAG, "onAdDisplayFailed")
            }
        })

        interstitialAppLovinAd!!.loadAd()
    }

    fun isInterstitialReady(): Boolean = interstitialAppLovinAd?.isReady == true

    fun showInterstitial(activity: Activity) {
        if (isInterstitialReady()) {
            binding.progressBar.visibility = View.GONE
            Log.d(TAG, "Interstitial ready")
            interstitialAppLovinAd?.showAd(activity)
        } else {
            Log.d(TAG, "Interstitial not ready")
        }
    }

    private fun loadAppLovinRewarded(rewardAppLovinAdUnitId: String) {
        Log.e(TAG, "loadAppLovinRewarded")

        rewardedAppLovinAd = MaxRewardedAd.getInstance(rewardAppLovinAdUnitId, this)

        rewardedAppLovinAd!!.setListener(object : MaxRewardedAdListener {

            override fun onAdLoaded(ad: MaxAd) {
                Log.d(TAG, "onAdLoaded")
                showRewarded(this@AdTapMindShow)
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                Log.d(TAG, "onAdLoadFailed")
            }

            override fun onAdDisplayed(ad: MaxAd) {
                Log.d(TAG, "onAdDisplayed")
            }

            override fun onAdHidden(ad: MaxAd) {
                Log.d(TAG, "onAdHidden")
                rewardedAppLovinAd?.setListener(null)
                rewardedAppLovinAd = null
            }

            override fun onAdClicked(ad: MaxAd) {
                Log.d(TAG, "onAdClicked")
                finish()
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                Log.d(TAG, "onAdDisplayFailed")
            }

            override fun onUserRewarded(ad: MaxAd, reward: MaxReward) {
                Log.d(TAG, "onUserRewarded")
            }
        })

        rewardedAppLovinAd!!.loadAd()
    }

    fun isRewardedReady(): Boolean = rewardedAppLovinAd?.isReady == true

    fun showRewarded(activity: Activity) {

        if (isRewardedReady()) {
            binding.progressBar.visibility = View.GONE
            Log.d(TAG, "isRewardedReady")
            rewardedAppLovinAd?.showAd(activity)
        } else {
            Log.d(TAG, "Rewarded not ready")
        }
    }
}