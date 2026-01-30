package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationNativeAdCallback
import com.google.android.gms.ads.mediation.MediationNativeAdConfiguration
import com.google.android.gms.ads.mediation.NativeAdMapper
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.ads.native.TapMindNativeAdAdapterListener

class AdmobNativeCustomEventLoader private constructor(
    private val adConfig: MediationNativeAdConfiguration,
    private val loadCallback: MediationAdLoadCallback<NativeAdMapper?, MediationNativeAdCallback?>
) {


    private var callback: MediationNativeAdCallback? = null

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobNativeCustomEventLoader"

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AdmobNativeCustomEventLoader? = null

        fun getInstance(
            adConfig: MediationNativeAdConfiguration,
            loadCallback: MediationAdLoadCallback<NativeAdMapper?, MediationNativeAdCallback?>
        ): AdmobNativeCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AdmobNativeCustomEventLoader(adConfig, loadCallback).also {
                    INSTANCE = it
                }
            }
        }
    }


    fun loadAd() {

        val request = object : TapMindAdapterResponseParameters {

            override fun getThirdPartyAdPlacementId(): String {
                return adConfig.serverParameters.getString(
                    "placement_id",
                    "ca-app-pub-7450680965442270/1794874535"
                )
            }

            override fun getBidResponse(): String {
                return adConfig.bidResponse
            }

            override fun getAdUnitId(): String {
                return ""
            }

            override fun getLocalExtraParameters(): Map<String, Any> {
                return emptyMap()
            }

            override fun getServerParameters(): Bundle {
                return adConfig.serverParameters
            }

            override fun getCustomParameters(): Bundle {
                // If you need custom params, pass using adConfig.mediationExtrasBundle (optional)
                return Bundle()
            }

            override fun hasUserConsent(): Boolean {
                return true
            }

            override fun isAgeRestrictedUser(): Boolean {
                return false
            }

            override fun isDoNotSell(): Boolean {
                return false
            }

            override fun getConsentString(): String {
                return ""
            }

            override fun isTesting(): Boolean {
                return adConfig.isTestRequest
            }
        }


        TapMindsMediationAdapter.getInstance(adConfig.context).loadNativeAd(
            request, adConfig.context as Activity,
            object : TapMindNativeAdAdapterListener {

                override fun onNativeAdLoaded(tapMindNativeAd: TapMindNativeAd?, bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onNativeAdLoaded")
                    tapMindNativeAd?.let {
                        val mapper = AdmobUnifiedNativeAdMapper(tapMindNativeAd)
//                    callback?.let { cb -> mapper.setCallback(cb) }
                        callback = loadCallback.onSuccess(mapper)
                    }
                }

                override fun onNativeAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                    Log.d(
                        TAG,
                        "$TAG1 : onNativeAdLoadFailed " + tapMindAdapterError.getErrorCode() + " " + tapMindAdapterError.getErrorMessage()
                    )
                    loadCallback.onFailure(
                        AdError(
                            tapMindAdapterError.getErrorCode(),
                            tapMindAdapterError.getMessage(),
                            "AdmobNativeCustomEventLoader"
                        )
                    )
                }

                override fun onNativeAdDisplayed(bundle: Bundle?) {
                    callback?.reportAdImpression()
                    callback?.onAdOpened()
                    Log.d(TAG, "$TAG1 : onNativeAdDisplayed ")
                }

                override fun onNativeAdClicked() {
                    callback?.reportAdClicked()
                    Log.d(TAG, "$TAG1 : onNativeAdClicked ")
                }

                override fun onNativeAdClicked(var1: Bundle?) {
                    callback?.reportAdClicked()
                    Log.d(TAG, "$TAG1 : onNativeAdClicked Bundle")
                }
            })

    }

}