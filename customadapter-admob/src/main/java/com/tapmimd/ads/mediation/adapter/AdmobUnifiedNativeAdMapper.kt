package com.tapmimd.ads.mediation.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.mediation.MediationNativeAdCallback
import com.google.android.gms.ads.mediation.NativeAdMapper
import com.google.android.gms.ads.nativead.NativeAd
import com.tapminds.ads.native.TapMindNativeAd

class AdmobUnifiedNativeAdMapper(
    private val admobAd: TapMindNativeAd
) : NativeAdMapper() {

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobUnifiedNativeAdMapper"
    private var callback: MediationNativeAdCallback? = null

    init {
        admobAd.title?.let { headline = it }
        admobAd.body?.let { body = it }

        admobAd.mainImage?.let { mainImage ->
            images = mutableListOf<NativeAd.Image>(
                AdmobNativeMappedImage(
                    mainImage.drawable, mainImage.uri, ADMOB_SDK_IMAGE_SCALE
                )
            )
        }

        admobAd.icon?.let { iconImage ->
            icon = AdmobNativeMappedImage(iconImage.drawable, iconImage.uri, ADMOB_SDK_IMAGE_SCALE)
        }

        admobAd.adChoise?.let { adChoicesContent = it }
        admobAd.callToAction?.let { callToAction = it }
        admobAd.advertiser?.let { advertiser = it }
        admobAd.starRating?.let { starRating = it }
        admobAd.store?.let { store = it }
        admobAd.price?.let { price = it }

        setHasVideoContent(admobAd.hasVideoContent)
        mediaContentAspectRatio = admobAd.mediaContentAspectRatio

        overrideClickHandling = false
        overrideImpressionRecording = false
    }


//    private var callback: MediationNativeAdCallback? = null
//
//    fun setCallback(cb: MediationNativeAdCallback) {
//        callback = cb
//    }


    companion object {
        const val ADMOB_SDK_IMAGE_SCALE = 1.0
    }

    override fun trackViews(
        containerView: View,
        clickableAssetViews: Map<String?, View?>,
        nonClickableAssetViews: Map<String?, View?>
    ) {
        val clickList = clickableAssetViews.values.toList()
        admobAd.prepareForInteraction(clickList as List<View>, containerView as ViewGroup)
    }

//    override fun recordImpression() {
//        admobAd.recordImpression();
//    }
//
//    override fun handleClick(view: View) {
//        admobAd.handleClick(view)
//    }

//    override fun trackViews(containerView: View, clickableAssetViews: Map<String?, View?>, nonClickableAssetViews: Map<String?, View?>) {
//        super.trackViews(containerView, clickableAssetViews, nonClickableAssetViews)
//        clickableAssetViews.forEach { (key, view) ->
//            view?.setOnClickListener {
//                callback?.reportAdClicked()
//                handleClick(view)
//            }
//        }
//    }

    fun setCallback(cb: MediationNativeAdCallback?) {
        this.callback = cb
        Log.d(TAG, "$TAG1 Callback set in mapper")
    }

    override fun recordImpression() {
        Log.d(TAG, "$TAG1 recordImpression called in mapper")
        admobAd.recordImpression()
        callback?.reportAdImpression()
    }

    override fun handleClick(view: View) {
        Log.d(TAG, "$TAG1 handleClick called for view: ${view.javaClass.simpleName}")
        admobAd.handleClick(view)
        callback?.reportAdClicked()
    }
}

class AdmobNativeMappedImage(
    private val drawable: Drawable?, private val uri: Uri?, private val scale: Double
) : NativeAd.Image() {

    override fun getDrawable(): Drawable? = drawable

    override fun getUri(): Uri? = uri

    override fun getScale(): Double = scale
}
