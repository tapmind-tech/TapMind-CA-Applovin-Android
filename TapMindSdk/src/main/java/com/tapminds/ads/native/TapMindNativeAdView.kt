package com.tapminds.ads.native

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class TapMindNativeAdView(binder: TapMindNativeAdViewBinder, context: Context): FrameLayout(context) {

    val mainView: View

    val titleView: View?
    val bodyView: View?
    val iconView: View?
    val callToActionView: View?
    val advertiserView: View?
    val mediaContentView: View?

    init {
        // Inflate layout
        val root = inflate(context, binder.layoutResourceId, this)
        mainView = root
        // Bind views
        titleView = root.findViewById(binder.titleTextViewId)
        bodyView = root.findViewById(binder.bodyTextViewId)
        iconView = root.findViewById(binder.iconImageViewId)
        callToActionView = root.findViewById(binder.callToActionButtonId)
        advertiserView = root.findViewById(binder.advertiserTextViewId)
        mediaContentView = root.findViewById(binder.mediaContentViewGroupId)
    }

}