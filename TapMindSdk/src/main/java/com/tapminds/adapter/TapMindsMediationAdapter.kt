package com.tapminds.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterResponseParameters
import com.tapminds.adapter.listener.TapMindMediationAdapterBase
import com.tapminds.adapter.listener.TapMindsAdapter
import com.tapminds.adapter.listener.TapMindsAdapterInitializationParameters
import com.tapminds.admob.AdMobManager
import com.tapminds.ads.banner.TapMindAdViewAdapter
import com.tapminds.ads.banner.TapMindAdViewAdapterListener
import com.tapminds.ads.interstitial.TapMindInterstitialAdapterListener
import com.tapminds.ads.native.TapMindNativeAd
import com.tapminds.ads.native.TapMindNativeAdAdapterListener
import com.tapminds.ads.native.TapMindNativeAdapter
import com.tapminds.ads.reward.TapMindReward
import com.tapminds.ads.reward.TapMindRewardedAdapterListener
import com.tapminds.ironSource.IronSourceManager
import com.tapminds.network.AdData
import com.tapminds.network.AdRequest
import com.tapminds.network.AdRequestPayloadHolder
import com.tapminds.network.AdRestManagerImpl
import com.tapminds.network.DataItem
import com.tapminds.network.Failure
import com.tapminds.network.Success
import com.tapminds.tapmindsads.TapMindAdFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TapMindsMediationAdapter private constructor() : TapMindMediationAdapterBase(),
    TapMindAdViewAdapter, TapMindNativeAdapter, CoroutineScope {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindsMediationAdapter"
//    private var admobManager : AdMobManager?= null
//    private var fbManager: FbManager?= null


    private var job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: TapMindsMediationAdapter? = null

        fun getInstance(): TapMindsMediationAdapter {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TapMindsMediationAdapter().also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun initialize(
        tapMindsAdapterInitializationParameters: TapMindsAdapterInitializationParameters?,
        context: Context?,
        onCompletionListener: TapMindsAdapter.OnCompletionListener?
    ) {

//        tapMindsAdapterInitializationParameters?.let {
//            onCompletionListener?.let { it1 ->
//                FbManager.Companion.getInstance().initialize(
//                    it,
//                    context as Activity?, it1
//                )
//            }
//        }
//        fbManager = FbManager()
//        fbManager?.initialize(tapMindsAdapterInitializationParameters!!, context as Activity, object : TapMindsAdapter.OnCompletionListener {
//            override fun onCompletion(p0: TapMindsAdapter.InitializationStatus?, p1: String?) {
//                Log.d(TAG, "$TAG1 TapMindsMediationAdapter Initializing failed")
//                onCompletionListener?.onCompletion(p0,p1)
////                checkCompletion(p0, p1)
//            }
//
//        })

//        admobManager = AdMobManager()
//        admobManager?.initialize(tapMindsAdapterInitializationParameters, context as Activity, object : TapMindsAdapter.OnCompletionListener {
//            override fun onCompletion(p0: TapMindsAdapter.InitializationStatus?, p1: String?) {
//                Log.d(TAG, "TapMindsMediationAdapter Initializing failed")
//                onCompletionListener?.onCompletion(p0,p1)
////                checkCompletion(p0, p1)
//            }
//
//        })
        onCompletionListener?.onCompletion(
            TapMindsAdapter.InitializationStatus.INITIALIZED_SUCCESS,
            "TapMinds Mediation Adapter initialized"
        )
    }

//    private lateinit var admobManager: AdMobManager
//    private lateinit var fbManager: FbManager
//
//    private val admob = "admob"
//    private val facebook = "facebook"
//    private val adsnetwork = "ads_network"
//
//    fun initialize(
//        parameters: MaxAdapterInitializationParameters,
//        activity: Activity,
//        onCompletionListener: MaxAdapter.OnCompletionListener
//    ) {
//
//        var completedCount = 0
//        val totalAdapters = 2
//        var finalStatus: MaxAdapter.InitializationStatus? = null
//        var finalMessage: String? = null
//
//        fun checkCompletion(status: MaxAdapter.InitializationStatus?, message: String?) {
//            synchronized(this) {
//                completedCount++
//                // Keep the latest status/message
//                finalStatus = status
//                finalMessage = message
//
//                if (completedCount == totalAdapters) {
//                    onCompletionListener.onCompletion(finalStatus, finalMessage)
//                }
//            }
//        }
//
//        admobManager = AdMobManager()
//        admobManager.initialize(parameters, activity, object : MaxAdapter.OnCompletionListener {
//            override fun onCompletion(p0: MaxAdapter.InitializationStatus?, p1: String?) {
//                checkCompletion(p0, p1)
//            }
//
//        })
//
//        fbManager = FbManager()
//        fbManager.initialize(parameters, activity, object : MaxAdapter.OnCompletionListener {
//            override fun onCompletion(p0: MaxAdapter.InitializationStatus?, p1: String?) {
//                checkCompletion(p0, p1)
//            }
//
//        })


//        AdMobAdapter.initialize(parameters,activity,object : MaxAdapter.OnCompletionListener{
//            override fun onCompletion(adapter: MaxAdapter.InitializationStatus?, p1: String?) {
//                if(adapter!!.equals(MaxAdapter.InitializationStatus.INITIALIZED_SUCCESS)){
//                    onCompletionListener.onCompletion(adapter,null)
//                }else{
//                    onCompletionListener.onCompletion(adapter,null)
//                }
//            }
//        })
//    }


    //region MaxSignalProvider Methods
//    fun collectSignal(
//        parameters: MaxAdapterSignalCollectionParameters?,
//        activity: Activity?,
//        callback: MaxSignalCollectionListener?
//    ) {
//
//        val map = HashMap<String, Any>()
//        map.put("test","value")
//        val restManager = AdRestManagerImpl()
//
//        launch {
//            val response = restManager.getAd(map)
//            withContext(Dispatchers.Main) {
//                when (response) {
//                    is Success -> {
//                        Log.d(TAG, "api call : "+response.data)
//                    }
//                    is Failure ->{
//                        Log.d(TAG, "api call error: "+response.error)
//                    }
//                }
////                when (response) {
////                    is Success -> listener.onAdLoaded(response.data)
////                    is Failure -> listener.onAdLoadFailure(response.error)
////                }
//            }
//        }
//
//        Log.d(TAG, "collectSignal")
//    }
    //endregion

    override fun loadAdViewAd(
        params: TapMindAdapterResponseParameters,
        format: TapMindAdFormat,
        activity: Context,
        index: Int,
        listener: TapMindAdViewAdapterListener
    ) {
        Log.d(TAG, "$TAG1 : loadBannerAd")

        fetchAdFromServer(onSuccess = { adapters, adData, adapterName ->
            if (index >= adapters.size) {
                listener.onAdViewAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all networks")
                )
                return@fetchAdFromServer
            }
            val current = adapters[index]
            val networkListener = object : TapMindAdViewAdapterListener {
                override fun onAdViewAdLoaded(view: View) {
                    listener.onAdViewAdLoaded(view)
                }

                override fun onAdViewAdLoaded(view: View, bundle: Bundle?) {
                    listener.onAdViewAdLoaded(view, bundle)
                }

                override fun onAdViewAdLoadFailed(error: TapMindAdapterError) {
                    val i = index + 1
                    loadAdViewAd(params, format, activity, i, listener)
                }

                override fun onAdViewAdDisplayed() {
                    listener.onAdViewAdDisplayed()
                }

                override fun onAdViewAdDisplayed(bundle: Bundle?) {
                    listener.onAdViewAdDisplayed(bundle)
                }

                override fun onAdViewAdDisplayFailed(error: TapMindAdapterError) {
                    listener.onAdViewAdLoadFailed(error)
                }

                override fun onAdViewAdDisplayFailed(
                    error: TapMindAdapterError,
                    bundle: Bundle?
                ) {
                    listener.onAdViewAdDisplayFailed(error, bundle)
                }

                override fun onAdViewAdClicked() {
                    listener.onAdViewAdClicked()
                }

                override fun onAdViewAdClicked(bundle: Bundle?) {
                    listener.onAdViewAdClicked(bundle)
                }

                override fun onAdViewAdHidden() {
                    listener.onAdViewAdHidden()
                }

                override fun onAdViewAdHidden(bundle: Bundle?) {
                    listener.onAdViewAdHidden(bundle)
                }

                override fun onAdViewAdExpanded() {
                    listener.onAdViewAdExpanded()
                }

                override fun onAdViewAdExpanded(bundle: Bundle?) {
                    listener.onAdViewAdExpanded(bundle)
                }

                override fun onAdViewAdCollapsed() {
                    listener.onAdViewAdCollapsed()
                }

                override fun onAdViewAdCollapsed(bundle: Bundle?) {
                    listener.onAdViewAdCollapsed(bundle)
                }

            }
            when (current.partner) {
                "AdMob", "GAM" -> {
                    AdMobManager.getInstance().loadAdViewAd(
                        adData,
                        current,
                        params,
                        format,
                        activity as Activity,
                        networkListener,
                        adapterName
                    )
                }

                "IronSource" -> {
                    IronSourceManager.getInstance().loadAdViewAd(
                        adData,
                        current,
                        params,
                        format,
                        activity as Activity,
                        networkListener,
                        adapterName
                    )
                }
            }
        }, onFailure = { error ->
            listener.onAdViewAdLoadFailed(error)
        })

//        val adPayload = AdRequestPayloadHolder.playLoad
//        val appName = adPayload?.appName
//        val placementName = adPayload?.placementId
//        val appVersion = adPayload?.appVersion
//        val adType = adPayload?.adType
//        val country = adPayload?.country
//
//        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")
//
//        val map =
//            AdRequest(appName, placementName, appVersion, "Android", "dev", adType, country)
//
//        launch {
//            val restManager = AdRestManagerImpl()
//            val response = restManager.bidRequest(map)
//            withContext(Dispatchers.Main) {
//                when (response) {
//                    is Success -> {
//                        Log.d(TAG, "api call : " + response.data)
//                        val adId = response.data.adapters?.get(0)?.adUnitId
//                            ?: "ca-app-pub-3940256099942544/6300978111"
//                        AdMobManager.getInstance().loadAdViewAd(
//                            parameters,
//                            adId,
//                            adFormat,
//                            activity as Activity,
//                            callback
//                        )
//                    }
//
//                    is Failure -> {
//                        Log.d(TAG, "api call : " + response.error.errorCode)
//                        callback.onAdViewAdLoadFailed(
//                            TapMindAdapterError(
//                                response.error.errorCode,
//                                response.error.errorMessage!!
//                            )
//                        )
//                    }
//                }
//            }
//        }

//        FbManager.getInstance().loadAdViewAd(parameters, adFormat, activity as Activity, callback)
    }

    fun loadInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        context: Context,
        index: Int = 0,
        callback: TapMindInterstitialAdapterListener
    ) {
        Log.d(TAG, "$TAG1 : loadInterstitialAd")
        fetchAdFromServer(onSuccess = { adapters, adData, adapterName ->
            if (index >= adapters.size) {
                callback.onInterstitialAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all networks")
                )
                return@fetchAdFromServer
            }
            val current = adapters[index]

            val networkListener = object : TapMindInterstitialAdapterListener {
                override fun onInterstitialAdLoaded() {
                    callback.onInterstitialAdLoaded()
                }

                override fun onInterstitialAdLoaded(bundle: Bundle?) {
                    callback.onInterstitialAdLoaded(bundle)
                }

                override fun onInterstitialAdLoadFailed(tapMindAdapterError: TapMindAdapterError?) {
                    Log.e("AdUnitData", "onInterstitialAdLoadFailed")
                    val i = index + 1
                    loadInterstitialAd(
                        parameters, context, i, callback
                    )
                }

                override fun onInterstitialAdDisplayed() {
                    callback.onInterstitialAdDisplayed()
                }

                override fun onInterstitialAdDisplayed(bundle: Bundle?) {
                    callback.onInterstitialAdDisplayed(bundle)
                }

                override fun onInterstitialAdClicked() {
                    callback.onInterstitialAdClicked()
                }

                override fun onInterstitialAdClicked(bundle: Bundle?) {
                    callback.onInterstitialAdClicked(bundle)
                }

                override fun onInterstitialAdHidden() {
                    callback.onInterstitialAdHidden()
                }

                override fun onInterstitialAdHidden(bundle: Bundle?) {
                    callback.onInterstitialAdHidden(bundle)
                }

                override fun onInterstitialAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?) {
                    callback.onInterstitialAdDisplayFailed(tapMindAdapterError)
                }

                override fun onInterstitialAdDisplayFailed(
                    tapMindAdapterError: TapMindAdapterError?,
                    bundle: Bundle?
                ) {
                    callback.onInterstitialAdDisplayFailed(tapMindAdapterError, bundle)
                }
            }

            when (current.partner) {
                "AdMob", "GAM" -> {
                    AdMobManager.getInstance().loadInterstitialAd(
                        adData,
                        parameters,
                        current,
                        context,
                        networkListener,
                        adapterName
                    )
                }

                "IronSource" -> {
                    IronSourceManager.getInstance().loadInterstitialAd(
                        adData,
                        parameters,
                        current,
                        context,
                        networkListener,
                        adapterName
                    )
                }
            }
        }, onFailure = { error ->
            callback.onInterstitialAdLoadFailed(error)
        })
    }

    fun showInterstitialAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        callback: TapMindInterstitialAdapterListener
    ) {
        AdMobManager.getInstance().showInterstitialAd(parameters, activity, callback)
//        FbManager.getInstance().showInterstitialAd(parameters, activity, callback)
    }


    fun loadRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        context: Context,
        index: Int = 0,
        callback: TapMindRewardedAdapterListener
    ) {
//        val adPayload = AdRequestPayloadHolder.playLoad
//        val appName = adPayload?.appName
//        val appVersion = adPayload?.appVersion
//        val adType = adPayload?.adType
//        val country = adPayload?.country
//
//        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")
//        AdMobManager.getInstance().loadRewardedAd(parameters, context, callback)

        fetchAdFromServer(
            onSuccess = { adapters, adData, adapterName ->
                if (index >= adapters.size) {
                    callback.onRewardedAdLoadFailed(
                        TapMindAdapterError(204, "No fill from all networks")
                    )
                    return@fetchAdFromServer
                }
                val current = adapters[index]
                val networkListener = object : TapMindRewardedAdapterListener {
                    override fun onRewardedAdLoaded() {
                        callback.onRewardedAdLoaded()
                    }

                    override fun onRewardedAdLoaded(bundle: Bundle?) {
                        callback.onRewardedAdLoaded(bundle)
                    }

                    override fun onRewardedAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                        val i = index + 1
                        loadRewardedAd(parameters, context, i, callback)
                    }

                    override fun onRewardedAdDisplayed() {
                        callback.onRewardedAdDisplayed()
                    }

                    override fun onRewardedAdDisplayed(bundle: Bundle) {
                        callback.onRewardedAdDisplayed(bundle)
                    }

                    override fun onRewardedAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?) {
                        callback.onRewardedAdDisplayFailed(tapMindAdapterError)
                    }

                    override fun onRewardedAdDisplayFailed(
                        tapMindAdapterError: TapMindAdapterError?,
                        bundle: Bundle?
                    ) {
                        callback.onRewardedAdDisplayFailed(tapMindAdapterError, bundle)
                    }

                    override fun onRewardedAdClicked() {
                        callback.onRewardedAdClicked()
                    }

                    override fun onRewardedAdClicked(bundle: Bundle?) {
                        callback.onRewardedAdClicked(bundle)
                    }

                    override fun onRewardedAdHidden() {
                        callback.onRewardedAdHidden()
                    }

                    override fun onRewardedAdHidden(bundle: Bundle?) {
                        callback.onRewardedAdHidden(bundle)
                    }

                    override fun onUserRewarded(tapMindReward: TapMindReward) {
                        callback.onUserRewarded(tapMindReward)
                    }

                    override fun onUserRewarded(
                        tapMindReward: TapMindReward?,
                        bundle: Bundle?
                    ) {
                        callback.onUserRewarded(tapMindReward, bundle)
                    }

                }
                when (current.partner) {
                    "AdMob", "GAM" -> {
                        AdMobManager.getInstance().loadRewardedAd(
                            adData, parameters, current, context, networkListener, adapterName
                        )
                    }

                    "IronSource" -> {
                        IronSourceManager.getInstance()
                            .loadRewardedAd(
                                adData,
                                parameters,
                                current,
                                context,
                                networkListener,
                                adapterName
                            )
                    }
                }
            },
            onFailure = { error ->
                callback.onRewardedAdLoadFailed(error)
            }
        )

//        FbManager.getInstance().loadRewardedAd(parameters, context as Activity, callback)
    }

    fun showRewardedAd(
        parameters: TapMindAdapterResponseParameters,
        activity: Activity,
        callback: TapMindRewardedAdapterListener
    ) {
        AdMobManager.getInstance().showRewardedAd(parameters, activity, callback)
//        FbManager.getInstance().showRewardedAd(parameters, activity, callback)
    }


    override fun loadNativeAd(
        var1: TapMindAdapterResponseParameters?,
        var2: Activity?,
        index: Int,
        var3: TapMindNativeAdAdapterListener?
    ) {
//        val adPayload = AdRequestPayloadHolder.playLoad
//        val appName = adPayload?.appName
//        val appVersion = adPayload?.appVersion
//        val adType = adPayload?.adType
//        val country = adPayload?.country
//
//        Log.e("TapMindAdapterAdmob", "loadAdViewAd: $adPayload")
//        AdMobManager.getInstance().loadNativeAd(parameters!!, activity!!, callback!!)
//        FbManager.getInstance().loadNativeAd(parameters!!,  activity as Activity, callback!!)
        fetchAdFromServer(onSuccess = { adapters, adData, adapterName ->
            if (index >= adapters.size) {
                var3?.onNativeAdLoadFailed(
                    TapMindAdapterError(204, "No fill from all networks")
                )
                return@fetchAdFromServer
            }
            val current = adapters[index]
            val networkListener = object : TapMindNativeAdAdapterListener {

                override fun onNativeAdLoaded(
                    var1: TapMindNativeAd?,
                    var2: Bundle?
                ) {
                    var3?.onNativeAdLoaded(var1, var2)
                }

                override fun onNativeAdLoadFailed(tapMindAdapterError: TapMindAdapterError) {
                    val i = index + 1
                    loadNativeAd(var1, var2, i, var3)
                }

                override fun onNativeAdDisplayed(bundle: Bundle?) {
                    var3?.onNativeAdDisplayed(bundle)
                }

                override fun onNativeAdClicked() {
                    var3?.onNativeAdClicked()
                }

                override fun onNativeAdClicked(var1: Bundle?) {
                    var3?.onNativeAdClicked(var1)
                }
            }
            when (current.partner) {
                "AdMob", "GAM" -> {
                    AdMobManager.getInstance().loadNativeAd(
                        adData, var1!!, current, var2!!, networkListener, adapterName
                    )
                }
            }

        }, onFailure = { error ->
            var3?.onNativeAdLoadFailed(error)
        })
    }

    private fun fetchAdFromServer(
        onSuccess: (List<DataItem>, AdData, String) -> Unit,
        onFailure: (TapMindAdapterError) -> Unit
    ) {
        val adPayload = AdRequestPayloadHolder.playLoad

        Log.e("App@@@", "adPayload: $adPayload")

        val request = AdRequest(
            adPayload?.appName,
            adPayload?.placementId,
            adPayload?.packageName,
            adPayload?.appVersion,
            "Android",
            "dev",
            adPayload?.adType,
            adPayload?.country
        )

        Log.e("TapMindAdapterAdmob", "AdRequest: $request")

        launch {
            val restManager = AdRestManagerImpl()
            val response = restManager.bidRequest(request)

            withContext(Dispatchers.Main) {
                when (response) {
                    is Success -> {
                        val sortedAdapters = response.data.adapters?.sortedBy {
                            it.priority ?: Int.MAX_VALUE
                        } ?: emptyList()

                        Log.e("TapMindAdapterAdmob", "fetchAdFromServer: ${response.data}")
                        onSuccess(sortedAdapters, response.data, adPayload?.adapterName.toString())
                    }

                    is Failure -> {
                        Log.e("TapMindAdapterAdmob", "fetchAdFromServer: ${response.error}")
                        onFailure(
                            TapMindAdapterError(
                                response.error.errorCode,
                                response.error.errorMessage ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }
}