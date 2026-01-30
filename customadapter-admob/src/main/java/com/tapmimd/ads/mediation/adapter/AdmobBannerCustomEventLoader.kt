package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.facebook.FbManager
import com.tapminds.tapmindsads.TapMindAdFormat


class AdmobBannerCustomEventLoader private constructor(
    private val adConfig: MediationBannerAdConfiguration,
    private val loadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
) {

    private var returnedBanner: TapMindAdmobBannerReturned? = null
    private var callback: MediationBannerAdCallback? = null
    private var fbManager: FbManager? = null

    private val TAG = "APP@@@"
    private val TAG1 = "AdmobBannerCustomEventLoader"

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AdmobBannerCustomEventLoader? = null

        fun getInstance(
            adConfig: MediationBannerAdConfiguration,
            loadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
        ): AdmobBannerCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AdmobBannerCustomEventLoader(adConfig, loadCallback).also {
                    INSTANCE = it
                }
            }
        }
    }


    fun loadAdd() {
        val request = object : TapMindAdapterResponseParameters {

            override fun getThirdPartyAdPlacementId(): String {
                return adConfig.serverParameters.getString(
                    "placement_id", "ca-app-pub-7450680965442270/1794874535"
                )
            }

            override fun getBidResponse(): String {
                return adConfig.bidResponse
            }

            override fun getAdUnitId(): String {
                return ""
            }

            override fun getLocalExtraParameters(): Map<String, Any> {
                return mapOf()
            }

            override fun getServerParameters(): Bundle {
                return Bundle()
            }

            override fun getCustomParameters(): Bundle {
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
                return true
            }
        }

        TapMindsMediationAdapter.getInstance(adConfig.context).loadAdViewAd(
                request,
                TapMindAdFormat.BANNER,
                adConfig.context,
                object : TapMindAdViewAdapterListener {
                    override fun onAdViewAdLoaded(view: View) {
                        returnedBanner = TapMindAdmobBannerReturned(bannerView = view)

                        callback = loadCallback.onSuccess(returnedBanner!!)
                        returnedBanner!!.callback = callback
                        Log.d(TAG, "$TAG1 : onAdLoaded")
                    }

                    override fun onAdViewAdLoaded(view: View, bundle: Bundle?) {

                    }

                    override fun onAdViewAdLoadFailed(error: TapMindAdapterError) {
                        Log.d(
                            TAG,
                            "$TAG1 : onAdFailedToLoad " + error.getErrorCode() + " " + error.getErrorMessage()
                        )
                        loadCallback.onFailure(
                            AdError(
                                error.getErrorCode(),
                                error.getMessage(),
                                "AdmobBannerCustomEventLoaderr"
                            )
                        )
                    }

                    override fun onAdViewAdDisplayed() {
                        Log.d(TAG, "$TAG1 : onAdOpened")
                        returnedBanner?.callback?.onAdOpened()
                        callback?.reportAdImpression()
                    }

                    override fun onAdViewAdDisplayed(bundle: Bundle?) {
                        Log.d(TAG, "$TAG1 : onAdOpened Bundle")
                        returnedBanner?.callback?.onAdOpened()
                        callback?.reportAdImpression()
                    }

                    override fun onAdViewAdDisplayFailed(error: TapMindAdapterError) {
                        Log.d(
                            TAG,
                            "$TAG1 : onAdViewAdDisplayFailed " + error.getErrorCode() + " " + error.getErrorMessage()
                        )
                        loadCallback.onFailure(
                            AdError(
                                error.getErrorCode(),
                                error.getMessage(),
                                "AdmobBannerCustomEventLoaderr"
                            )
                        )
                    }

                    override fun onAdViewAdDisplayFailed(
                        error: TapMindAdapterError, bundle: Bundle?
                    ) {

                    }

                    override fun onAdViewAdClicked() {
                        Log.d(TAG, "$TAG1 : onAdClicked")
                        returnedBanner?.callback?.reportAdClicked()
                    }

                    override fun onAdViewAdClicked(bundle: Bundle?) {
                        Log.d(TAG, "$TAG1 : onAdClicked onAdViewAdClicked")
                        returnedBanner?.callback?.reportAdClicked()
                    }

                    override fun onAdViewAdHidden() {
                        Log.d(TAG, "$TAG1 : onAdClosed")
                        returnedBanner?.callback?.onAdClosed()
                    }

                    override fun onAdViewAdHidden(bundle: Bundle?) {
                        Log.d(TAG, "$TAG1 : onAdClosed Bundle")
                        returnedBanner?.callback?.onAdClosed()
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
}
