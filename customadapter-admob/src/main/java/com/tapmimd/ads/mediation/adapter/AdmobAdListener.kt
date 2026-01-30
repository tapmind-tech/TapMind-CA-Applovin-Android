package com.tapmimd.ads.mediation.adapter

abstract class AdmobAdListener {

    /** Called when the ad fetch succeeded */
    open fun onAdFetchSucceeded() {}

    /** Called when the ad fetch failed */
    open fun onAdFetchFailed(errorCode: Int) {}

    /** Ad opened fullscreen */
    open fun onAdFullScreen() {}

    /** Ad closed fullscreen */
    open fun onAdClosed() {}
}
