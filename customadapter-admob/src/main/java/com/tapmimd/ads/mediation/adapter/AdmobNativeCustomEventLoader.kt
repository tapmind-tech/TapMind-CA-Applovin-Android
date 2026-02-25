package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
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

class AdmobNativeCustomEventLoader(
    private val adConfig: MediationNativeAdConfiguration,
    private val loadCallback: MediationAdLoadCallback<NativeAdMapper?, MediationNativeAdCallback?>
) {
    private var callback: MediationNativeAdCallback? = null

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobNativeCustomEventLoader"

    fun loadAd() {
        AdapterActivityHolder.register(adConfig.context)

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

        val activity = adConfig.context.findActivity()
            ?: AdapterActivityHolder.getActivity()

        if (activity == null) {
            Log.e(TAG, "$TAG1 : Context is not an Activity. Cannot load native ad.")
            loadCallback.onFailure(
                AdError(
                    0,
                    "Context is not an Activity",
                    "AdmobNativeCustomEventLoader"
                )
            )
            return
        }

        TapMindsMediationAdapter.getInstance().loadNativeAd(
            request, activity,0,
            object : TapMindNativeAdAdapterListener {

                override fun onNativeAdLoaded(var1: TapMindNativeAd?, var2: Bundle?) {
                    Log.d(TAG, "$TAG1 : onNativeAdLoaded")
                    var1?.let { nativeAd ->
                        try {
                            val mapper = AdmobUnifiedNativeAdMapper(nativeAd)
                            callback = loadCallback.onSuccess(mapper)
                            mapper.setCallback(callback)

                            Log.d(TAG, "$TAG1 : Successfully created mapper and callback")
                        } catch (e: Exception) {
                            Log.e(TAG, "$TAG1 : Error creating mapper: ${e.message}", e)
                            loadCallback.onFailure(
                                AdError(
                                    500,
                                    "Failed to create ad mapper: ${e.message}",
                                    "AdmobNativeCustomEventLoader"
                                )
                            )
                        }
                    } ?: run {
                        Log.e(TAG, "$TAG1 : Loaded ad is null")
                        loadCallback.onFailure(
                            AdError(
                                404,
                                "Native ad is null",
                                "AdmobNativeCustomEventLoader"
                            )
                        )
                    }
                }

                override fun onNativeAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                    Log.d(
                        TAG,
                        "$TAG1 : onNativeAdLoadFailed ${tapMindAdapterError.getErrorCode()} ${tapMindAdapterError.getErrorMessage()}"
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
                    Log.d(TAG, "$TAG1 : onNativeAdDisplayed")
                    callback?.reportAdImpression()
                    callback?.onAdOpened()
                }

                override fun onNativeAdClicked() {
                    Log.d(TAG, "$TAG1 : onNativeAdClicked")
                    callback?.reportAdClicked()
                }

                override fun onNativeAdClicked(var1: Bundle?) {
                    Log.d(TAG, "$TAG1 : onNativeAdClicked Bundle")
                    callback?.reportAdClicked()
                }
            })
    }

    private fun Context.findActivity(): Activity? {
        var ctx = this
        while (ctx is ContextWrapper) {
            if (ctx is Activity) {
                return ctx
            }
            ctx = ctx.baseContext
        }
        return null
    }
}