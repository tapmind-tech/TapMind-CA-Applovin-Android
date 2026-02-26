package com.ironsource.adapters.custom.istapmindcustomadapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.ironsource.adapters.custom.istapmindcustomadapter.GeoProviderIronSource.getAppInfo
import com.ironsource.mediationsdk.adunit.adapter.BaseNativeAd
import com.ironsource.mediationsdk.adunit.adapter.listener.NativeAdListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrorType
import com.ironsource.mediationsdk.model.NetworkSettings
import com.ironsource.mediationsdk.utils.IronSourceUtils
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.ads.native.TapMindNativeAdAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import java.util.Locale

class TapMindNativeAdapter(networkSettings: NetworkSettings) :
    BaseNativeAd<ISTapMindCustomAdapterCustomAdapter>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindNativeAdapter"

    override fun loadAd(adData: AdData, context: Context, nativeAdListener: NativeAdListener) {

        val adUnitData = adData.adUnitData

        for ((key, value) in adUnitData) {
            Log.d("AdUnitData", "$key : $value")
        }
        val appData = getAppInfo(context)
        val country = Locale.getDefault().country
        val geo = GeoProviderIronSource.get(country)

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = appData.appName,
            placementId = "tapmind_native",
            appVersion = appData.versionName,
            adType = "Native",
            country = country,
            packageName = appData.packageName,
            adapterName = "ISTapMindCustomAdapterCustomAdapter",
            sdkVersion = GeoProviderIronSource.sdkVersion
        )

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
                return false
            }
        }

        TapMindsMediationAdapter.getInstance()
            .loadNativeAd(
                request, context as Activity, 0,
                object : TapMindNativeAdAdapterListener {
                    override fun onNativeAdLoaded(
                        tapMindNativeAd: TapMindNativeAd?,
                        bundle: Bundle?
                    ) {
                        Log.d(TAG, "$TAG1 : onNativeAdLoaded")
//                        tapMindNativeAd?.let {

//                            val adapterNativeAdData = AdmobUnifiedNativeAdMapperIronSouce(tapMindNativeAd)
//                            val adapterNativeAdViewBinder = AdmobUnifiedNativeAdBinderIronSouce(tapMindNativeAd)
//                            nativeAdListener.onAdLoadSuccess()
//
//                            AdapterNativeAdData
//                            AdapterNativeAdViewBinder

//                            nativeAdListener.onAdLoadSuccess
//                            val mapper = AdmobUnifiedNativeAdMapper(tapMindNativeAd)
//                            callback = loadCallback.onSuccess(mapper)
//                        }
                    }


                    override fun onNativeAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                        Log.d(
                            TAG,
                            "$TAG1 : onNativeAdLoadFailed " + tapMindAdapterError.getErrorCode() + " " + tapMindAdapterError.getErrorMessage()
                        )
                        nativeAdListener.onAdLoadFailed(
                            AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,
                            tapMindAdapterError.getErrorCode(),
                            tapMindAdapterError.getMessage()
                        )
                    }

                    override fun onNativeAdDisplayed(bundle: Bundle?) {
                        Log.d(TAG, "$TAG1 : onNativeAdDisplayed ")
                        nativeAdListener.onAdOpened()
                    }

                    override fun onNativeAdClicked() {
                        Log.d(TAG, "$TAG1 : onNativeAdClicked ")
                        nativeAdListener.onAdClicked()
                    }

                    override fun onNativeAdClicked(var1: Bundle?) {
                        Log.d(TAG, "$TAG1 : onNativeAdClicked Bundle")
                        nativeAdListener.onAdClicked()
                    }
                })
    }

    override fun destroyAd(adData: AdData) {

    }
}