package com.tapmimd.ads.mediation.adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.mediation.NativeAdMapper
import com.google.android.gms.ads.nativead.NativeAd
import com.tapminds.ads.native.TapMindNativeAd

class AdmobUnifiedNativeAdMapper(
    private val admobAd: TapMindNativeAd
) : NativeAdMapper() {

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobUnifiedNativeAdMapper"

    init {

        admobAd.title?.let { headline = it }

//        val imagess =  arrayListOf(AdmobNativeMappedImage(admobAd.mainImage?.drawable, admobAd.mainImage?.uri, ADMOB_SDK_IMAGE_SCALE))

//        Log.d("APP@@@","size "+imagess.size)


        admobAd.mainImage?.let { mainImage ->
            images = mutableListOf<NativeAd.Image>(
                AdmobNativeMappedImage(
                    mainImage.drawable,
                    mainImage.uri,
                    ADMOB_SDK_IMAGE_SCALE
                )
            )
        }
        admobAd.body?.let { body = it }
        admobAd.icon?.let { iconImage ->
            icon =
                AdmobNativeMappedImage(iconImage.drawable, iconImage.uri, ADMOB_SDK_IMAGE_SCALE)
        }
        admobAd.callToAction?.let { callToAction = it }
        admobAd.advertiser?.let { advertiser = it }
        admobAd.starRating?.let { starRating = it }
        admobAd.store?.let { store = it }
        admobAd.price?.let { price = it }
        setHasVideoContent(admobAd.hasVideoContent)
        admobAd.adChoise?.let { adChoicesContent = it }
        admobAd.mediaView?.let { setMediaView(it) }
        admobAd.mediaContentAspectRatio.let { mediaContentAspectRatio = it }

        val extras = Bundle()
        extras.putString("degree_of_awesomeness", "100") // example
        setExtras(extras)

        // GOOGLE HANDLES IMPRESSIONS + CLICKS
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
}

class AdmobNativeMappedImage(
    private val drawable: Drawable?,
    private val uri: Uri?,
    private val scale: Double
) : NativeAd.Image() {

    override fun getDrawable(): Drawable? = drawable

    override fun getUri(): Uri? = uri

    override fun getScale(): Double = scale
}
