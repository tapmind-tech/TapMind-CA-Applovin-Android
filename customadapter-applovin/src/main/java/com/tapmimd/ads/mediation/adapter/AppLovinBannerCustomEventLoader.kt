package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.tapmindsads.TapMindAdFormat

class AppLovinBannerCustomEventLoader private constructor(
    val maxAdapterResponseParameters: MaxAdapterResponseParameters,
    val maxAdFormat: MaxAdFormat,
    val activity: Activity,
    val maxAdViewAdapterListener: MaxAdViewAdapterListener
){


    private val TAG = "APP@@@"
    private val TAG1 = "AppLovinBannerCustomEventLoader"
    companion object{

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppLovinBannerCustomEventLoader? = null

        fun getInstance(maxAdapterResponseParameters: MaxAdapterResponseParameters,
                        maxAdFormat: MaxAdFormat,
                        activity: Activity,
                        maxAdViewAdapterListener: MaxAdViewAdapterListener): AppLovinBannerCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppLovinBannerCustomEventLoader(maxAdapterResponseParameters,maxAdFormat,activity,maxAdViewAdapterListener).also {
                    INSTANCE = it
                }
            }
        }

    }

    fun loadAdd(){
        val request = object : TapMindAdapterResponseParameters {

            override fun getThirdPartyAdPlacementId(): String {
                return ""
            }

            override fun getBidResponse(): String {
                return  ""
            }

            override fun getAdUnitId(): String {
                return ""
            }

            override fun getLocalExtraParameters(): Map<String, Any> {
                return emptyMap()
            }

            override fun getServerParameters(): Bundle {
                return Bundle()
            }

            override fun getCustomParameters(): Bundle {
                // If you need custom params, pass using adConfig.mediationExtrasBundle (optional)
                return Bundle()
            }

            override fun hasUserConsent(): Boolean? {
                return true
            }

            override fun isAgeRestrictedUser(): Boolean? {
                return false
            }

            override fun isDoNotSell(): Boolean? {
                return false
            }

            override fun getConsentString(): String? {
                return ""
            }

            override fun isTesting(): Boolean {
                return false
            }
        }
        
        TapMindsMediationAdapter.getInstance(activity)
            .loadAdViewAd(request, TapMindAdFormat.formatFromString(maxAdFormat.label)!!, activity,
                object : TapMindAdViewAdapterListener {
                    override fun onAdViewAdLoaded(view: View) {
                        maxAdViewAdapterListener.onAdViewAdLoaded(view)
                        Log.d(TAG,"$TAG1 : onAdLoaded")
                    }

                    override fun onAdViewAdLoaded(view: View, bundle: Bundle?) {
                        maxAdViewAdapterListener.onAdViewAdLoaded(view,bundle)
                    }

                    override fun onAdViewAdLoadFailed(error: TapMindAdapterError) {
                        Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error.getErrorCode()+ " "+error.getErrorMessage())
                        maxAdViewAdapterListener.onAdViewAdLoadFailed(MaxAdapterError(error.getErrorCode(), error.getMessage()))
                    }

                    override fun onAdViewAdDisplayed() {
                        Log.d(TAG,"$TAG1 : onAdOpened")
                        maxAdViewAdapterListener.onAdViewAdDisplayed()
                    }

                    override fun onAdViewAdDisplayed(bundle: Bundle?) {
                        Log.d(TAG,"$TAG1 : onAdOpened Bundle")
                        maxAdViewAdapterListener.onAdViewAdDisplayed(bundle)
                    }

                    override fun onAdViewAdDisplayFailed(error: TapMindAdapterError) {
                        Log.d(TAG,"$TAG1 : onAdViewAdDisplayFailed "+error.getErrorCode()+ " "+error.getErrorMessage())
                        maxAdViewAdapterListener.onAdViewAdDisplayFailed(MaxAdapterError(error.getErrorCode(), error.getMessage()))
                    }

                    override fun onAdViewAdDisplayFailed(error: TapMindAdapterError, bundle: Bundle?) {
                        maxAdViewAdapterListener.onAdViewAdDisplayFailed(MaxAdapterError(error.getErrorCode(), error.getMessage()),bundle)
                    }

                    override fun onAdViewAdClicked() {
                        Log.d(TAG,"$TAG1 : onAdClicked")
                        maxAdViewAdapterListener.onAdViewAdClicked()
                    }

                    override fun onAdViewAdClicked(bundle: Bundle?) {
                        Log.d(TAG,"$TAG1 : onAdClicked onAdViewAdClicked")
                        maxAdViewAdapterListener.onAdViewAdClicked(bundle)
                    }

                    override fun onAdViewAdHidden() {
                        Log.d(TAG,"$TAG1 : onAdClosed")
                        maxAdViewAdapterListener.onAdViewAdHidden()
                    }

                    override fun onAdViewAdHidden(bundle: Bundle?) {
                        Log.d(TAG,"$TAG1 : onAdClosed Bundle")
                        maxAdViewAdapterListener.onAdViewAdHidden(bundle)
                    }

                    override fun onAdViewAdExpanded() {
                        maxAdViewAdapterListener.onAdViewAdExpanded()
                    }

                    override fun onAdViewAdExpanded(bundle: Bundle?) {
                        maxAdViewAdapterListener.onAdViewAdExpanded(bundle)
                    }

                    override fun onAdViewAdCollapsed() {
                        maxAdViewAdapterListener.onAdViewAdCollapsed()
                    }

                    override fun onAdViewAdCollapsed(bundle: Bundle?) {
                        maxAdViewAdapterListener.onAdViewAdCollapsed(bundle)
                    }
                })
    }
    

}