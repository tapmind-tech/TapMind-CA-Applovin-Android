package com.tapminds.ads.native

import android.view.View

open class TapMindNativeAdImpl {
    private var clickableViews: List<View>? = null
    private var adViewTracker: Tracker? = null   // "b" renamed to a readable name

    fun setClickableViews(views: List<View>?) {
        this.clickableViews = views
    }

    fun getClickableViews(): List<View>? {
        return clickableViews
    }

    fun setAdViewTracker(tracker: Tracker?) {
        this.adViewTracker = tracker
    }

    fun getAdViewTracker(): Tracker? {
        return adViewTracker
    }

    // Placeholder for class "b" from Java
    open class Tracker

}