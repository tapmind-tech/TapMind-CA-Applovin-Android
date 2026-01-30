package com.tapmimd.ads.mediation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration
import com.google.android.gms.ads.rewarded.RewardItem
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.adapter.TapMindsMediationAdapter

class AdmobRewardedCustomEventLoader(
    private val adConfig: MediationRewardedAdConfiguration,
    private val loadCallback: MediationAdLoadCallback<MediationRewardedAd?, MediationRewardedAdCallback?>
) {


    private val TAG = "APP@@@"
    private val TAG1 = "AdmobRewardedCustomEventLoader"


    private var  request : TapMindAdapterResponseParameters?= null
    private var tapMindRewardedAdapterListener : TapMindRewardedAdapterListener ?= null

    private var returnedRewarded: TapMindAdmobRewardedReturned? = null
    private var callback: MediationRewardedAdCallback? = null

    companion object{

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AdmobRewardedCustomEventLoader? = null

        fun getInstance(adConfig: MediationRewardedAdConfiguration,callback: MediationAdLoadCallback<MediationRewardedAd?, MediationRewardedAdCallback?>): AdmobRewardedCustomEventLoader {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AdmobRewardedCustomEventLoader(adConfig,callback).also {
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
                return adConfig.bidResponse ?: ""
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

        tapMindRewardedAdapterListener = object : TapMindRewardedAdapterListener{
            override fun onRewardedAdLoaded() {
                returnedRewarded = TapMindAdmobRewardedReturned(this@AdmobRewardedCustomEventLoader)
                callback = loadCallback.onSuccess(returnedRewarded!!)
                returnedRewarded?.callback = callback
                Log.d(TAG,"$TAG1 : onAdLoaded")
            }

            override fun onRewardedAdLoaded(bundle: Bundle?) {
                returnedRewarded = TapMindAdmobRewardedReturned(this@AdmobRewardedCustomEventLoader)
                callback = loadCallback.onSuccess(returnedRewarded!!)
                returnedRewarded?.callback = callback
                Log.d(TAG,"$TAG1 : onAdLoaded Bundle")
            }

            override fun onRewardedAdLoadFailed(error: TapMindAdapterError) {
                Log.d(TAG,"$TAG1 : onAdFailedToLoad "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                loadCallback.onFailure(AdError(error!!.getErrorCode(), error.getMessage(),"AdmobRewardedCustomEventLoader"))
            }

            override fun onRewardedAdDisplayed() {
                callback?.onAdOpened()
                callback?.reportAdImpression()
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayed")
            }

            override fun onRewardedAdDisplayed(bundle: Bundle) {
                callback?.onAdOpened()
                callback?.reportAdImpression()
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayed Bundle")
            }

            override fun onRewardedAdDisplayFailed(error: TapMindAdapterError?) {
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayFailed "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                callback?.onAdFailedToShow(AdError(error!!.getErrorCode(), error.getMessage(),"AdmobRewardedCustomEventLoader"))
            }

            override fun onRewardedAdDisplayFailed(error: TapMindAdapterError?, bundle: Bundle?) {
                Log.d(TAG,"$TAG1 : onRewardedAdDisplayFailed Bundle "+error?.getErrorCode()+ " "+error?.getErrorMessage())
                callback?.onAdFailedToShow(AdError(error!!.getErrorCode(), error.getMessage(),"AdmobRewardedCustomEventLoader"))
            }

            override fun onRewardedAdClicked() {
                callback?.reportAdClicked()
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked")
            }

            override fun onRewardedAdClicked(bundle: Bundle?) {
                callback?.reportAdClicked()
                Log.d(TAG,"$TAG1 : onInterstitialAdClicked Bundle")
            }

            override fun onRewardedAdHidden() {
                callback?.onAdClosed()
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden")
            }

            override fun onRewardedAdHidden(bundle: Bundle?) {
                callback?.onAdClosed()
                Log.d(TAG,"$TAG1 : onInterstitialAdHidden Bundle")
            }

            override fun onUserRewarded(tapMindReward: TapMindReward) {
                callback?.onUserEarnedReward(object : RewardItem {
                    override fun getType(): String {
                        return tapMindReward.label
                    }

                    override fun getAmount(): Int {
                        return tapMindReward.amount
                    }

                })
            }

            override fun onUserRewarded(tapMindReward: TapMindReward?, bundle: Bundle?) {
                callback?.onUserEarnedReward(object : RewardItem {
                    override fun getType(): String {
                        return tapMindReward!!.label
                    }

                    override fun getAmount(): Int {
                        return tapMindReward!!.amount
                    }

                })
            }

        }

        TapMindsMediationAdapter.getInstance(adConfig.context)
            .loadRewardedAd(request!!, adConfig.context, tapMindRewardedAdapterListener!!)

    }


    fun showAd(context: Context) {
        TapMindsMediationAdapter.getInstance(context)
            .showRewardedAd(request!!,context as Activity,tapMindRewardedAdapterListener!!)
    }
}