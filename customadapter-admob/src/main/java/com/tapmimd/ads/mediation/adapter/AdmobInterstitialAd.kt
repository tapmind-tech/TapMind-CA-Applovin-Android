package com.tapmimd.ads.mediation.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

class AdmobInterstitialAd(private val context: Context) {

    private var adUnit: String? = null
    private var listener: AdmobInterstitialListener? = null
    private var isLoaded = false

    fun setAdUnit(adUnitId: String?) {
        adUnit = adUnitId
    }

    fun setAdListener(l: AdmobInterstitialListener) {
        listener = l
    }

    fun loadAd(request: AdmobInterstitialRequest) {
        Log.d("AdmobInterstitial", "Loading interstitial…")

        // Fake network loading delay
        Handler(Looper.getMainLooper()).postDelayed({
            val success = (0..10).random() > 2

            if (success) {
                isLoaded = true
                listener?.onAdLoaded()
            } else {
                listener?.onAdFailed(-1)
            }

        }, 1500)
    }

    fun showAd() {
        if (!isLoaded) {
            listener?.onAdFailed(-2)
            return
        }

        listener?.onAdShown()

        // Simulate ad being visible for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            listener?.onAdClosed()
        }, 2000)
    }
}
