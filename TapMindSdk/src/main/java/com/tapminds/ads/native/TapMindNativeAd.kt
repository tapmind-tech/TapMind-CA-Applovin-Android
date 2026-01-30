package com.tapminds.ads.native

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import com.tapminds.tapmindsads.TapMindAdFormat

open class TapMindNativeAd(builder: Builder) : TapMindNativeAdImpl() {

    companion object {
        private const val MINIMUM_STARS_TO_RENDER = 3.0
    }

    val format: TapMindAdFormat? = builder.format
    val title: String? = builder.title
    val store: String? = builder.store
    val price: String? = builder.price
    val advertiser: String? = builder.advertiser
    val body: String? = builder.body
    val callToAction: String? = builder.callToAction
    val icon: TapMindNativeAdImage? = builder.icon
    val mainImage: TapMindNativeAdImage? = builder.mainImage
    val iconView: View? = builder.iconView
    val adChoise: View? = builder.adChoise
    val mediaView: View? = builder.mediaView       // <--- getter auto exists
    val mediaContentAspectRatio: Float = builder.mediaContentAspectRatio
    val hasVideoContent: Boolean = builder.hasVideoContent

    val starRating: Double? =
        builder.starRating?.takeIf { it >= MINIMUM_STARS_TO_RENDER }


    fun getMediaVieww() = mediaView

    private var nativeAdView: TapMindNativeAdView? = null
    private var expired: Boolean = false

    fun isExpired(): Boolean = expired

    fun setExpired() {
        expired = true
    }

    fun setNativeAdView(view: TapMindNativeAdView) {
        this.nativeAdView = view
    }


    @Deprecated("Deprecated in original Java source")
    fun prepareViewForInteraction(nativeAdView: TapMindNativeAdView) {

    }

    open fun prepareForInteraction(views: List<View>, container: ViewGroup): Boolean {
        return false
    }

    open fun shouldPrepareViewForInteractionOnMainThread(): Boolean = true

    open fun isContainerClickable(): Boolean = false

    fun recordImpression() {

    }

    fun handleClick(view: View) {

    }


    class Builder {
        var format: TapMindAdFormat? = null
        var title: String? = null
        var store: String? = null
        var price: String? = null
        var advertiser: String? = null
        var body: String? = null
        var callToAction: String? = null
        var icon: TapMindNativeAdImage? = null
        var iconView: View? = null
        var adChoise: View? = null
        var mediaView: View? = null
        var mainImage: TapMindNativeAdImage? = null
        var mediaContentAspectRatio: Float = 0f
        var starRating: Double? = null
        var hasVideoContent: Boolean = false

        fun setAdFormat(value: TapMindAdFormat) = apply { format = value }
        fun setTitle(value: String?) = apply { title = value }
        fun setStore(value: String?) = apply { store = value }
        fun setPrice(value: String?) = apply { price = value }
        fun setAdvertiser(value: String?) = apply { advertiser = value }
        fun setBody(value: String?) = apply { body = value }
        fun setCallToAction(value: String?) = apply { callToAction = value }
        fun setIcon(value: TapMindNativeAdImage?) = apply { icon = value }
        fun setIconView(value: View?) = apply { iconView = value }
        fun setAdChoise(value: View?) = apply { adChoise = value }
        fun setMediaView(value: View?) = apply { mediaView = value }
        fun setMainImage(value: TapMindNativeAdImage?) = apply { mainImage = value }
        fun setMediaContentAspectRatio(value: Float) = apply { mediaContentAspectRatio = value }
        fun setHasVideoContent(value: Boolean) = apply { hasVideoContent = value }
        fun setStarRating(value: Double?) = apply { starRating = value }

        fun build(): TapMindNativeAd = TapMindNativeAd(this)
    }

    class TapMindNativeAdImage(
        val drawable: Drawable? = null,
        val uri: Uri? = null
    ) {
        constructor(drawable: Drawable) : this(drawable, null)
        constructor(uri: Uri) : this(null, uri)
    }


}
