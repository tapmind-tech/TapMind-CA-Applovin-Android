package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.ads.native.TapMindNativeAdAdapterListener

class AppLovinNativeCustomEventLoader private constructor(
    val maxAdapterResponseParameters: MaxAdapterResponseParameters?,
    val activity: Activity?,
    val maxAdViewAdapterListener: MaxNativeAdAdapterListener?
){

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobNativeCustomEventLoaderprivate"
    companion object{

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppLovinNativeCustomEventLoader? = null

        fun getInstance(maxAdapterResponseParameters: MaxAdapterResponseParameters?,
                        activity: Activity?,
                        callback: MaxNativeAdAdapterListener?): AppLovinNativeCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppLovinNativeCustomEventLoader(maxAdapterResponseParameters,activity,callback).also {
                    INSTANCE = it
                }
            }
        }

    }

    fun loadAd(){
        val request = object : TapMindAdapterResponseParameters {

            override fun getThirdPartyAdPlacementId(): String {
                return ""

            }

            override fun getBidResponse(): String {
                return ""
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
        TapMindsMediationAdapter.getInstance(activity!!).loadNativeAd(request,activity!!, object : TapMindNativeAdAdapterListener{

            override fun onNativeAdLoaded(tapMindNativeAd: TapMindNativeAd?, bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onNativeAdLoaded")
                tapMindNativeAd?.let {
                    val maxNativeAd = AppLovinUnifiedNativeAdMapper(tapMindNativeAd)
                    maxAdViewAdapterListener?.onNativeAdLoaded(maxNativeAd.mapToMaxNativeAd(),bundle)
                }
            }


            override fun onNativeAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                Log.d(TAG,"$TAG1 : onNativeAdLoadFailed "+tapMindAdapterError.getErrorCode() + " "+tapMindAdapterError.getErrorMessage())
                maxAdViewAdapterListener?.onNativeAdLoadFailed(MaxAdapterError(tapMindAdapterError.getErrorCode(), tapMindAdapterError.getMessage()))
            }

            override fun onNativeAdDisplayed(bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onNativeAdDisplayed ")
                maxAdViewAdapterListener?.onNativeAdDisplayed(bundle)
            }

            override fun onNativeAdClicked() {
                Log.d(TAG,"$TAG1 : onNativeAdClicked ")
                maxAdViewAdapterListener?.onNativeAdClicked()
            }

            override fun onNativeAdClicked(var1: Bundle?) {
                Log.d(TAG,"$TAG1 : onNativeAdClicked Bundle")
                maxAdViewAdapterListener?.onNativeAdClicked(var1)
            }
        })
    }

}