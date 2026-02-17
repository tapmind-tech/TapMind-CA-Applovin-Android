package com.tapminds.ads.native

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

class TapMindNativeAdViewBinder private constructor(
    @LayoutRes val layoutResourceId: Int,
    @IdRes val titleTextViewId: Int,
    @IdRes val bodyTextViewId: Int,
    @IdRes val advertiserTextViewId: Int,
    @IdRes val iconImageViewId: Int,
    @IdRes val optionsContentViewGroupId: Int,
    @IdRes val mediaContentViewGroupId: Int,
    @IdRes val callToActionButtonId: Int
) {

    class Builder(@LayoutRes private val layoutId: Int) {
        private var titleId: Int = 0
        private var bodyId: Int = 0
        private var advertiserId: Int = 0
        private var iconId: Int = 0
        private var optionsId: Int = 0
        private var mediaId: Int = 0
        private var ctaId: Int = 0

        fun setTitleTextViewId(@IdRes id: Int) = apply { titleId = id }
        fun setBodyTextViewId(@IdRes id: Int) = apply { bodyId = id }
        fun setAdvertiserTextViewId(@IdRes id: Int) = apply { advertiserId = id }
        fun setIconImageViewId(@IdRes id: Int) = apply { iconId = id }
        fun setOptionsContentViewGroupId(@IdRes id: Int) = apply { optionsId = id }
        fun setMediaContentViewGroupId(@IdRes id: Int) = apply { mediaId = id }
        fun setCallToActionButtonId(@IdRes id: Int) = apply { ctaId = id }

        fun build(): TapMindNativeAdViewBinder {
            return TapMindNativeAdViewBinder(
                layoutResourceId = layoutId,
                titleTextViewId = titleId,
                bodyTextViewId = bodyId,
                advertiserTextViewId = advertiserId,
                iconImageViewId = iconId,
                optionsContentViewGroupId = optionsId,
                mediaContentViewGroupId = mediaId,
                callToActionButtonId = ctaId
            )
        }
    }
}