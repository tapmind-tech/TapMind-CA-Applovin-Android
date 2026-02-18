package com.ironsource.adapters.custom.istapmindcustomadapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
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
import kotlin.collections.iterator

class ISTapMindCustomAdapterCustomBanner(networkSettings: NetworkSettings) :
    BaseBanner<ISTapMindCustomAdapterCustomAdapter>(networkSettings) {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindBannerAdapter"
    private var callback: MediationBannerAdCallback? = null
    private var bannerAdListener: BannerAdListener? = null
    private var currentAdView: View? = null
    private var currentContainer: ViewGroup? = null

    override fun loadAd(
        addata: AdData,
        activity: Activity,
        isBannerSize: ISBannerSize,
        bannerAdListener: BannerAdListener
    ) {
        Log.d(TAG, "$TAG1 loadBannerAd")
        this.bannerAdListener = bannerAdListener

        val adUnitData = addata.adUnitData

        for ((key, value) in adUnitData) {
            Log.d(TAG, "adUnitData $key : $value")
        }

        val config = addata.configuration
        val instanceName = config["instanceName"] as? String

        Log.e("AdUnitData", "instanceName = $instanceName")

        AdRequestPayloadHolder.playLoad = AdRequestPayload(
            appName = getAppName(activity),
//            placementId = "banner_map",
            placementId = instanceName,
            appVersion = getAppVersion(activity),
            adType = "Banner",
            country = Locale.getDefault().country,
            packageName = getPackageName(activity),
            "ISTapMindCustomAdapterCustomAdapter"
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

        TapMindsMediationAdapter.getInstance().loadAdViewAd(
            request, TapMindAdFormat.BANNER, activity, object : TapMindAdViewAdapterListener {
                override fun onAdViewAdLoaded(view: View) {

                    val layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        50.dpToPx(view.context)
                    )
                    bannerAdListener.onAdLoadSuccess(view, layoutParams)

                    Log.d("APP@@@", "✅ Success signaled with fixed dimensions")
                }

                override fun onAdViewAdLoaded(view: View, bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onAdLoaded with bundle")
                    onAdViewAdLoaded(view) // Call the single-parameter version
                }

                override fun onAdViewAdLoadFailed(error: TapMindAdapterError) {
                    Log.d(
                        TAG,
                        "$TAG1 : onAdFailedToLoad " + error.getErrorCode() + " " + error.getErrorMessage()
                    )
                    bannerAdListener.onAdLoadFailed(
                        AdapterErrorType.ADAPTER_ERROR_TYPE_INTERNAL,
                        error.getErrorCode(),
                        error.getMessage()
                    )
                }

                override fun onAdViewAdDisplayed() {
                    Log.d(TAG, "$TAG1 : onAdOpened")
                    bannerAdListener.onAdOpened()
                }

                override fun onAdViewAdDisplayed(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onAdOpened Bundle")
                    bannerAdListener.onAdOpened()
                }

                override fun onAdViewAdDisplayFailed(error: TapMindAdapterError) {
                    Log.d(
                        TAG,
                        "$TAG1 : onAdViewAdDisplayFailed " + error.getErrorCode() + " " + error.getErrorMessage()
                    )
                    bannerAdListener.onAdShowFailed(error.getErrorCode(), error.getMessage())
                }

                override fun onAdViewAdDisplayFailed(
                    error: TapMindAdapterError, bundle: Bundle?
                ) {
                    bannerAdListener.onAdShowFailed(error.getErrorCode(), error.getMessage())
                }

                override fun onAdViewAdClicked() {
                    Log.d(TAG, "$TAG1 : onAdClicked")
                    bannerAdListener.onAdClicked()
                }

                override fun onAdViewAdClicked(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onAdViewAdClicked Bundle")
                    bannerAdListener.onAdClicked()
                }

                override fun onAdViewAdHidden() {
                    Log.d(TAG, "$TAG1 : onAdViewAdHidden")
                    bannerAdListener.onAdClicked()
                }

                override fun onAdViewAdHidden(bundle: Bundle?) {
                    Log.d(TAG, "$TAG1 : onAdViewAdHidden Bundle")
                }

                override fun onAdViewAdExpanded() {
                    // Handle if needed
                }

                override fun onAdViewAdExpanded(bundle: Bundle?) {
                    // Handle if needed
                }

                override fun onAdViewAdCollapsed() {
                    // Handle if needed
                }

                override fun onAdViewAdCollapsed(bundle: Bundle?) {
                    // Handle if needed
                }
            })
    }

    override fun destroyAd(addata: AdData) {
        Log.d(TAG, "$TAG1 destroyAd")
        currentAdView = null
        currentContainer = null
        callback = null
        bannerAdListener = null
    }

    private fun getAppVersion(context: Context): String {
        return try {
            val pIInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pIInfo.versionName.toString()
        } catch (_: Exception) {
            "unknown"
        }
    }

    private fun getAppName(context: Context): String {
        return try {
            val applicationInfo = context.applicationInfo
            context.packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (_: Exception) {
            "Unknown"
        }
    }

    private fun findBannerContainer(adUnitData: Map<String, Any>): ViewGroup? {
        return when {
            adUnitData["adView"] is ViewGroup -> adUnitData["adView"] as ViewGroup
            adUnitData["bannerView"] is ViewGroup -> adUnitData["bannerView"] as ViewGroup
            adUnitData["bannerLayout"] is ViewGroup -> adUnitData["bannerLayout"] as ViewGroup
            else -> {
                Log.e("APP@@@", "No valid banner container found in adUnitData")
                null
            }
        }
    }

    private fun getPackageName(context: Context): String {
        return try {
            context.packageName
        } catch (_: Exception) {
            "Unknown"
        }
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}