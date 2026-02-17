package com.tapmimd.ads.mediation.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

class AdmobAdView(context: Context) : FrameLayout(context) {

    private val TAG = "APP@@@"

    private var adUnitId: String? = null
    private var adSize: AdmobAdSize? = null
    private var adListener: AdmobAdListener? = null

    fun setAdUnit(unitId: String?) {
        adUnitId = unitId
    }

    fun setSize(size: AdmobAdSize) {
        adSize = size
        layoutParams = LayoutParams(
            (size.widthDp * resources.displayMetrics.density).toInt(),
            (size.heightDp * resources.displayMetrics.density).toInt()
        )
    }

    fun setAdListener(listener: AdmobAdListener) {
        adListener = listener
    }

    /** Simulate ad loading */
    fun fetchAd(request: AdmobAdRequest) {
        Log.d(TAG, "Fetching ad... isTest=${request.isTestMode()} keywords=${request.getKeywords()}")

        // Simulate network fetch delay
//        postDelayed({
//            // Fake success/failure
            val isSuccess = (0..10).random()
//
//            if (isSuccess) {
                createDummyBanner(isSuccess)
                adListener?.onAdFetchSucceeded()
//            } else {
//                adListener?.onAdFetchFailed(-1)
//            }
//
//        }, 1000)
    }

    private fun createDummyBanner(isSuccess : Int) {
        removeAllViews()

        val banner = TextView(context).apply {
            text = "Admob Banner Ad $isSuccess"
            gravity = Gravity.CENTER
            setBackgroundColor(Color.YELLOW)
            setTextColor(Color.BLACK)
            textSize = 16f
        }

        addView(banner, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }
}
