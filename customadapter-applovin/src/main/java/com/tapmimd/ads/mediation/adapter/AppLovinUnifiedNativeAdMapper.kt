package com.tapmimd.ads.mediation.adapter

import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.nativeAds.MaxNativeAd
import com.tapminds.ads.native.TapMindNativeAd

class AppLovinUnifiedNativeAdMapper(
    private val tapMindNativeAd: TapMindNativeAd
) {

    fun mapToMaxNativeAd(): MaxNativeAd {
        val builder = MaxNativeAd.Builder()
            .setAdFormat(MaxAdFormat.NATIVE)
            .setTitle(tapMindNativeAd.title)
            .setAdvertiser(tapMindNativeAd.advertiser)
            .setBody(tapMindNativeAd.body)
            .setCallToAction(tapMindNativeAd.callToAction)
            .setIcon(tapMindNativeAd.icon?.uri?.let{MaxNativeAd.MaxNativeAdImage(it)} ?: tapMindNativeAd.icon?.drawable?.let{MaxNativeAd.MaxNativeAdImage(it)})
            .setMainImage(tapMindNativeAd.mainImage?.uri?.let{MaxNativeAd.MaxNativeAdImage(it)} ?: tapMindNativeAd.mainImage?.drawable?.let{MaxNativeAd.MaxNativeAdImage(it)})
            .setStarRating(tapMindNativeAd.starRating)
            .setMediaContentAspectRatio(1.78f)

        // Optional views if you have them
        builder.setMediaView(tapMindNativeAd.mediaView)
        builder.setOptionsView(tapMindNativeAd.adChoise)
        builder.setIconView(tapMindNativeAd.iconView)

        return builder.build()
    }
}