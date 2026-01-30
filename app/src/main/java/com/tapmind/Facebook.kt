package com.tapmind

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdOptionsView
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdLayout
import com.facebook.ads.NativeAdListener

class Facebook {


    val TAG = "APP@@@"
    val TAG1 = "Admob"

    fun init(context: Context){
        AudienceNetworkAds.initialize(context);
    }

    fun showFbNativeAd(context: Context, progressBar: ProgressBar, adContainer : FrameLayout){
        progressBar.visibility = View.VISIBLE

        var nativeAd = NativeAd(context, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")

        val nativeAdListener = object : NativeAdListener{
            override fun onMediaDownloaded(ad: Ad?) {
                Log.d(TAG,"$TAG1 : onMediaDownloaded")
            }

            override fun onError(ad: Ad?, error: AdError?) {
                progressBar.visibility = View.GONE
                Log.d(TAG,"$TAG1 : onError :"+error?.errorCode+ " "+error?.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {
                Log.d(TAG,"$TAG1 : onAdLoaded")
                progressBar.visibility = View.GONE
                inflateAd(nativeAd,adContainer,context);
            }

            override fun onAdClicked(ad: Ad?) {
                Log.d(TAG,"$TAG1 : onAdClicked")
            }

            override fun onLoggingImpression(ad: Ad?) {
                Log.d(TAG,"$TAG1 : onLoggingImpression")
            }

        }
        nativeAd.loadAd(
            nativeAd.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build());

    }

    private fun inflateAd(nativeAd: NativeAd,adContainer : FrameLayout,context: Context) {

        nativeAd.unregisterView()

        // Add the Ad view into the ad container
        val nativeAdLayout = adContainer
        val inflater = LayoutInflater.from(context)

        // Inflate the Ad view
        val adView = inflater.inflate(R.layout.facebook_native_ad, nativeAdLayout, false) as NativeAdLayout
        nativeAdLayout.addView(adView)

        // Add the AdOptionsView
        val adChoicesContainer = adView.findViewById<LinearLayout>(R.id.ad_choices_container)
        val adOptionsView = AdOptionsView(context, nativeAd, adView)
        adChoicesContainer.removeAllViews()
        adChoicesContainer.addView(adOptionsView, 0)

        // Create native UI using the ad metadata
        val nativeAdIcon = adView.findViewById<MediaView>(R.id.native_ad_icon)
        val nativeAdTitle = adView.findViewById<TextView>(R.id.native_ad_title)
        val nativeAdMedia = adView.findViewById<MediaView>(R.id.native_ad_media)
        val nativeAdSocialContext = adView.findViewById<TextView>(R.id.native_ad_social_context)
        val nativeAdBody = adView.findViewById<TextView>(R.id.native_ad_body)
        val sponsoredLabel = adView.findViewById<TextView>(R.id.native_ad_sponsored_label)
        val nativeAdCallToAction = adView.findViewById<Button>(R.id.native_ad_call_to_action)

        // Set the Text
        nativeAdTitle.text = nativeAd.advertiserName
        nativeAdBody.text = nativeAd.adBodyText
        nativeAdSocialContext.text = nativeAd.adSocialContext
        nativeAdCallToAction.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction
        sponsoredLabel.text = nativeAd.sponsoredTranslation

        // Create a list of clickable views
        val clickableViews = ArrayList<View>()
        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)


        // Register views for interaction
        nativeAd.registerViewForInteraction(
            adView,
            nativeAdMedia,
            nativeAdIcon,
            clickableViews
        )
    }


}