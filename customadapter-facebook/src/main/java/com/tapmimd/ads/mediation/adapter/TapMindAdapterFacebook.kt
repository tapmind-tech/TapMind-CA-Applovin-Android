package com.tapmimd.ads.mediation.adapter
//
//import android.app.Activity
//import android.content.Context
//import android.util.Log
//import com.applovin.mediation.MaxAdFormat
//import com.applovin.mediation.adapter.MaxAdViewAdapter
//import com.applovin.mediation.adapter.MaxAdapter
//import com.applovin.mediation.adapter.MaxAppOpenAdapter
//import com.applovin.mediation.adapter.MaxInterstitialAdapter
//import com.applovin.mediation.adapter.MaxRewardedAdapter
//import com.applovin.mediation.adapter.MaxSignalProvider
//import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxAppOpenAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxInterstitialAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxRewardedAdapterListener
//import com.applovin.mediation.adapter.listeners.MaxSignalCollectionListener
//import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
//import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
//import com.applovin.mediation.adapter.parameters.MaxAdapterSignalCollectionParameters
//import com.applovin.sdk.AppLovinSdk
//import com.tapminds.AdRestManagerImpl
//import com.tapminds.Failure
//import com.tapminds.Success
//import com.tapminds.admob.AdMobManager
//import com.tapminds.facebook.FbManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import kotlin.coroutines.CoroutineContext
//
//class TapMindAdapterFacebook(private val sdk: AppLovinSdk) :
//    com.applovin.mediation.adapters.MediationAdapterBase(sdk), MaxSignalProvider,
//    MaxInterstitialAdapter, MaxAppOpenAdapter, MaxRewardedAdapter, MaxAdViewAdapter,
//    CoroutineScope {
//
//
//    private val TAG = "APP@@@"
//    private lateinit var fbManager: FbManager
//
//    private val admob = "admob"
//    private val facebook = "facebook"
//    private val adsnetwork = "ads_network"
//
//    override fun initialize(
//        parameters: MaxAdapterInitializationParameters,
//        activity: Activity,
//        onCompletionListener: MaxAdapter.OnCompletionListener
//    ) {
//
//        fbManager = FbManager()
//        fbManager.initialize(parameters, activity, object : MaxAdapter.OnCompletionListener {
//            override fun onCompletion(p0: MaxAdapter.InitializationStatus?, p1: String?) {
//                onCompletionListener.onCompletion(p0, p1)
//            }
//
//        })
//
//
//
////        AdMobAdapter.initialize(parameters,activity,object : MaxAdapter.OnCompletionListener{
////            override fun onCompletion(adapter: MaxAdapter.InitializationStatus?, p1: String?) {
////                if(adapter!!.equals(MaxAdapter.InitializationStatus.INITIALIZED_SUCCESS)){
////                    onCompletionListener.onCompletion(adapter,null)
////                }else{
////                    onCompletionListener.onCompletion(adapter,null)
////                }
////            }
////        })
//    }
//
//    private var job = Job()
//    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job
//
//    override fun getSdkVersion(): String {
//        return "1.0.0"
//    }
//
//    override fun getAdapterVersion(): String {
//        return "1.0.0.0"
//    }
//
//    //region MaxSignalProvider Methods
//    override fun collectSignal(
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
//    //endregion
//
//    //region MaxInterstitialAdapter Methods
//    override fun loadInterstitialAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        callback: MaxInterstitialAdapterListener
//    ) {
//        if(parameters.serverParameters!!.getString(adsnetwork).equals(facebook)){
//            fbManager.loadInterstitialAd(parameters,activity,callback)
//        }
//    }
//
//    override fun showInterstitialAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        callback: MaxInterstitialAdapterListener
//    ) {
//        if(parameters.serverParameters!!.getString(adsnetwork).equals(facebook)){
//            fbManager.showInterstitialAd(parameters,activity,callback)
//        }
//    }
//    //endregion
//
//    //region MaxAppOpenAdapter Methods
//    override fun loadAppOpenAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity?,
//        callback: MaxAppOpenAdapterListener
//    ) {
//        super.loadAppOpenAd(parameters, activity, callback)
//    }
//
//    override fun showAppOpenAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity?,
//        callback: MaxAppOpenAdapterListener
//    ) {
//        super.showAppOpenAd(parameters, activity, callback)
//    }
//    //endregion
//
//    //region MaxRewardedAdapter Methods
//    override fun loadRewardedAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        callback: MaxRewardedAdapterListener
//    ) {
//        if(parameters.serverParameters!!.getString(adsnetwork).equals(facebook)){
//            fbManager.loadRewardedAd(parameters,activity,callback)
//        }
//
//    }
//
//    override fun showRewardedAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        callback: MaxRewardedAdapterListener
//    ) {
//        if(parameters.serverParameters!!.getString(adsnetwork).equals(facebook)){
//            fbManager.showRewardedAd(parameters,activity,callback)
//        }
//    }
//    //endregion
//
//    //region MaxAdViewAdapter Methods
//    override fun loadAdViewAd(
//        parameters: MaxAdapterResponseParameters,
//        adFormat: MaxAdFormat,
//        activity: Activity,
//        callback: MaxAdViewAdapterListener
//    ) {
//        if(parameters.serverParameters!!.getString(adsnetwork).equals(facebook)){
//            fbManager.loadAdViewAd(parameters,adFormat,activity,callback)
//        }
//    }
//    //endregion
//
//    //region MaxNativeAdAdapter Methods
//    override fun loadNativeAd(
//        parameters: MaxAdapterResponseParameters,
//        activity: Activity,
//        callback: MaxNativeAdAdapterListener
//    ) {
//        super.loadNativeAd(parameters, activity, callback)
//        if(parameters.serverParameters!!.getString(adsnetwork).equals(facebook)){
//            fbManager.loadNativeAd(parameters,activity,callback)
//        }
//    }
//    //endregion
//
//
//    override fun onDestroy() {
//        fbManager.onDestroy()
//    }
//
//
//    override fun shouldLoadAdsOnUiThread(p0: MaxAdFormat?): Boolean? {
//        Log.d(TAG, "shouldLoadAdsOnUiThread : ")
//        return super.shouldLoadAdsOnUiThread(p0)
//    }
//
//    override fun shouldShowAdsOnUiThread(p0: MaxAdFormat?): Boolean? {
//        Log.d(TAG, "shouldShowAdsOnUiThread : ")
//        return super.shouldShowAdsOnUiThread(p0)
//    }
//
//    override fun configureReward(p0: MaxAdapterResponseParameters?) {
//        Log.d(TAG, "configureReward : ")
//        super.configureReward(p0)
//    }
//
//    override fun shouldCollectSignalsOnUiThread(): Boolean? {
//        Log.d(TAG, "shouldCollectSignalsOnUiThread : ")
//        return super.shouldCollectSignalsOnUiThread()
//    }
//
//    override fun shouldDestroyOnUiThread(): Boolean? {
//        Log.d(TAG, "shouldDestroyOnUiThread : ")
//        return super.shouldDestroyOnUiThread()
//    }
//
//    override fun shouldInitializeOnUiThread(): Boolean? {
//        Log.d(TAG, "shouldInitializeOnUiThread : ")
//        return super.shouldInitializeOnUiThread()
//    }
//
//    override fun getApplicationContext(): Context {
//        Log.d(TAG, "getApplicationContext : ")
//        return super.getApplicationContext()
//    }
//
//}