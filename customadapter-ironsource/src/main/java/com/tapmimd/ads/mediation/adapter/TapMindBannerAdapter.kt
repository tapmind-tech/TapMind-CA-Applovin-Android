package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ironsource.mediationsdk.ISBannerSize
import com.ironsource.mediationsdk.adunit.adapter.BaseBanner
import com.ironsource.mediationsdk.adunit.adapter.listener.BannerAdListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrorType
import com.ironsource.mediationsdk.model.NetworkSettings
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.network.AdRequestPayload
import com.tapminds.network.AdRequestPayloadHolder
import com.tapminds.tapmindsads.TapMindAdFormat
import java.util.Locale

class TapMindBannerAdapter(networkSettings: NetworkSettings) :
    BaseBanner<TapMindAdapterIronSource>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindBannerAdapter"

    override fun loadAd(
        addata: AdData,
        activity: Activity,
        isBannerSize: ISBannerSize,
        bannerAdListener: BannerAdListener
    ) {
        Log.d(TAG, "$TAG1 loadBannerAd")

        val adUnitData = addata.adUnitData

        for ((key, value) in adUnitData) {
            Log.d("AdUnitData", "$key : $value")
        }

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(activity),
            placementId = "",
            appVersion = getAppVersion(activity),
            adType = "Banner",
            country = Locale.getDefault().country
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
            .loadAdViewAd(request, TapMindAdFormat.BANNER, activity,
                object : TapMindAdViewAdapterListener {
                    override fun onAdViewAdLoaded(view: View) {
                        Log.d(TAG,"$TAG1 : onAdLoaded")
                        bannerAdListener.onAdLoadSuccess()
                    }

                    override fun onAdViewAdLoaded(view: View, bundle: Bundle?) {
                        bannerAdListener.onAdLoadSuccess()
                    }

                    override fun onAdViewAdLoadFailed(error: TapMindAdapterError) {
                        Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error.getErrorCode()+ " "+error.getErrorMessage())
                        bannerAdListener.onAdLoadFailed(AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,error.getErrorCode(), error.getMessage())
                    }

                    override fun onAdViewAdDisplayed() {
                        Log.d(TAG,"$TAG1 : onAdOpened")
                        bannerAdListener.onAdOpened()
                    }

                    override fun onAdViewAdDisplayed(bundle: Bundle?) {
                        Log.d(TAG,"$TAG1 : onAdOpened Bundle")
                        bannerAdListener.onAdOpened()
                    }

                    override fun onAdViewAdDisplayFailed(error: TapMindAdapterError) {
                        Log.d(TAG,"$TAG1 : onAdViewAdDisplayFailed "+error.getErrorCode()+ " "+error.getErrorMessage())
                        bannerAdListener.onAdShowFailed(error.getErrorCode(), error.getMessage())
                    }

                    override fun onAdViewAdDisplayFailed(error: TapMindAdapterError, bundle: Bundle?) {
                        bannerAdListener.onAdShowFailed(error.getErrorCode(), error.getMessage())
                    }

                    override fun onAdViewAdClicked() {
                        Log.d(TAG,"$TAG1 : onAdClicked")
                        bannerAdListener.onAdClicked()
                    }

                    override fun onAdViewAdClicked(bundle: Bundle?) {
                        Log.d(TAG,"$TAG1 : onAdViewAdClicked Bundle")
                        bannerAdListener.onAdClicked()
                    }

                    override fun onAdViewAdHidden() {
                        Log.d(TAG,"$TAG1 : onAdViewAdHidden")
                        bannerAdListener.onAdClicked()
                    }

                    override fun onAdViewAdHidden(bundle: Bundle?) {
                        Log.d(TAG,"$TAG1 : onAdViewAdHidden Bundle")
                    }

                    override fun onAdViewAdExpanded() {

                    }

                    override fun onAdViewAdExpanded(bundle: Bundle?) {

                    }

                    override fun onAdViewAdCollapsed() {

                    }

                    override fun onAdViewAdCollapsed(bundle: Bundle?) {

                    }
                })
    }

    override fun destroyAd(addata: AdData) {
        Log.d(TAG, "$TAG1 destroyAd")
    }

    private fun getAppVersion(context: Context): String {
        return try {
            val pIInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val appVersion = pIInfo.versionName.toString()
            return appVersion
        } catch (_: Exception) {
            "unknown"
        }
    }

    private fun getAppName(context: Context): String {
        return try {
            val applicationInfo = context.applicationInfo
            val appName = context.packageManager.getApplicationLabel(applicationInfo).toString()
            return appName
        } catch (_: Exception) {
            "Unknown"
        }
    }
}