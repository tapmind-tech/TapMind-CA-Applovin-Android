package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAd
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration
import com.tapminds.adapter.TapMindsMediationAdapter
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener

class AdmobInterstitialCustomEventLoader(
    private val adConfig: MediationInterstitialAdConfiguration,
    private val loadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
) {

//    private var interstitialAdCallback: MediationInterstitialAdCallback? = null
//    private var interstitialAd: MediationInterstitialAd? = null


    private var returnedInterstitial: TapMindAdmobInterstitialReturned? = null
    private var callback: MediationInterstitialAdCallback? = null
    private var request: TapMindAdapterResponseParameters? = null
    private var tapMindInterstitialAdapterListener: TapMindInterstitialAdapterListener? = null


    private val TAG = "APP@@@"
    private val TAG1 = "AdmobInterstitialCustomEventLoader"

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AdmobInterstitialCustomEventLoader? = null

        fun getInstance(
            adConfig: MediationInterstitialAdConfiguration,
            loadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
        ): AdmobInterstitialCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AdmobInterstitialCustomEventLoader(adConfig, loadCallback).also {
                    INSTANCE = it
                }
            }
        }
    }

    fun loadAd() {
        request = object : TapMindAdapterResponseParameters {

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
                return adConfig.isTestRequest
            }
        }

        tapMindInterstitialAdapterListener = object : TapMindInterstitialAdapterListener {
            override fun onInterstitialAdLoaded() {
                returnedInterstitial =
                    TapMindAdmobInterstitialReturned(this@AdmobInterstitialCustomEventLoader)
                callback = loadCallback.onSuccess(returnedInterstitial!!)
                returnedInterstitial?.callback = callback
                Log.d(TAG, "$TAG1 : onAdLoaded")
            }

            override fun onInterstitialAdLoaded(bundle: Bundle?) {
                returnedInterstitial =
                    TapMindAdmobInterstitialReturned(this@AdmobInterstitialCustomEventLoader)
                callback = loadCallback.onSuccess(returnedInterstitial!!)
                returnedInterstitial?.callback = callback
                Log.d(TAG, "$TAG1 : onAdLoaded Bundle")
            }

            override fun onInterstitialAdLoadFailed(error: TapMindAdapterError?) {
                Log.d(
                    TAG,
                    "$TAG1 : onAdFailedToLoad " + error?.getErrorCode() + " " + error?.getErrorMessage()
                )
                loadCallback.onFailure(
                    AdError(
                        error!!.getErrorCode(),
                        error.getMessage(),
                        "AdmobInterstitialCustomEventLoader"
                    )
                )
            }

            override fun onInterstitialAdDisplayed() {
                callback?.onAdOpened()
                callback?.reportAdImpression()
                Log.d(TAG, "$TAG1 : onInterstitialAdDisplayed")
            }

            override fun onInterstitialAdDisplayed(bundle: Bundle?) {
                callback?.onAdOpened()
                callback?.reportAdImpression()
                Log.d(TAG, "$TAG1 : onInterstitialAdDisplayed Bundle")
            }

            override fun onInterstitialAdClicked() {
                callback?.reportAdClicked()
                Log.d(TAG, "$TAG1 : onInterstitialAdClicked")
            }

            override fun onInterstitialAdClicked(bundle: Bundle?) {
                Log.d(TAG, "$TAG1 : onInterstitialAdClicked Bundle")
            }

            override fun onInterstitialAdHidden() {
                callback?.onAdClosed()
                Log.d(TAG, "$TAG1 : onInterstitialAdHidden")
            }

            override fun onInterstitialAdHidden(bundle: Bundle?) {
                callback?.onAdClosed()
                Log.d(TAG, "$TAG1 : onInterstitialAdHidden Bundle")
            }

            override fun onInterstitialAdDisplayFailed(error: TapMindAdapterError?) {
                Log.d(
                    TAG,
                    "$TAG1 : onAdFailedToLoad " + error?.getErrorCode() + " " + error?.getErrorMessage()
                )
                callback?.onAdFailedToShow(
                    AdError(
                        error!!.getErrorCode(),
                        error.getMessage(),
                        "AdmobInterstitialCustomEventLoader"
                    )
                )
            }

            override fun onInterstitialAdDisplayFailed(
                error: TapMindAdapterError?,
                bundle: Bundle?
            ) {
                Log.d(
                    TAG,
                    "$TAG1 : onAdFailedToLoad " + error?.getErrorCode() + " " + error?.getErrorMessage()
                )
                callback?.onAdFailedToShow(
                    AdError(
                        error!!.getErrorCode(),
                        error.getMessage(),
                        bundle.toString()
                    )
                )
            }
        }


        TapMindsMediationAdapter.getInstance(adConfig.context)
            .loadInterstitialAd(request!!, adConfig.context, tapMindInterstitialAdapterListener!!)


    }

    fun showAd(context: Context) {
        Log.d(TAG, "$TAG1 : showAd")
        TapMindsMediationAdapter.getInstance(context)
            .showInterstitialAd(
                request!!,
                context as Activity,
                tapMindInterstitialAdapterListener!!
            )
    }
}