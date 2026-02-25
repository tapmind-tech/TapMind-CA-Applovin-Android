package com.tapmind

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.unity3d.mediation.LevelPlay
import com.unity3d.mediation.LevelPlayConfiguration
import com.unity3d.mediation.LevelPlayInitError
import com.unity3d.mediation.LevelPlayInitListener
import com.unity3d.mediation.LevelPlayInitRequest
import java.util.concurrent.Executors

class App : Application() {

    private val TAG = "APP@@@"

    //        private var flow: String = "Admob"
//    private var flow : String = "ironsource"
    private var flow: String = "applovin"

    companion object {
        @Volatile
        var isIronSourceInitialized: Boolean = false
        var isAppLovinInitialized: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()

        when (flow) {
            "Admob" -> {
                Admob().init(this)
            }

            "ironsource" -> {
                val apiKey = "2517ad13d"
                val userId = Settings.Secure.getString(
                    contentResolver, Settings.Secure.ANDROID_ID
                )

                val initRequest = LevelPlayInitRequest.Builder(apiKey).withUserId(userId).build()
                LevelPlay.init(this, initRequest, object : LevelPlayInitListener {
                    override fun onInitFailed(error: LevelPlayInitError) {
                        isIronSourceInitialized = false
                        Log.e(TAG, "onInitFailed: IronSource")
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                applicationContext, "IronSource init failed", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onInitSuccess(configuration: LevelPlayConfiguration) {
                        isIronSourceInitialized = true
                        Log.e(TAG, "onInitSuccess: IronSource")
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                applicationContext,
                                "IronSource initialized successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }

            "applovin" -> {
                val sdkKey =
                    "iIqgM0gm6T2PNNT_gMWAs7CCUaTgf80tqmTxfQOc6TtOn5rSmkSA3h79SGxTS0fkGw6zbDhvZLve_hnfd9eDeX"

                val executor = Executors.newSingleThreadExecutor()
                executor.execute {
                    val initConfigBuilder =
                        AppLovinSdkInitializationConfiguration.builder(sdkKey, this)
                    initConfigBuilder.mediationProvider = AppLovinMediationProvider.MAX

                    try {
                        // Enable test mode by default for the current device. Cannot be run on the main thread.
                        val currentGaid = AdvertisingIdClient.getAdvertisingIdInfo(this).id
                        currentGaid?.let {
                            initConfigBuilder.testDeviceAdvertisingIds = listOf(it)
                        }
                    } catch (_: Throwable) {
                    }

                    // Initialize the AppLovin SDK
                    AppLovinSdk.getInstance(this)
                        .initialize(initConfigBuilder.build()) { sdkConfig ->
                            // AppLovin SDK is initialized, start loading ads now or later if ad gate is reached
                            Log.d(TAG, "AppLovin SDK initialized: $sdkConfig")
//
                            isAppLovinInitialized = true
                        }

                    executor.shutdown()

//                val context = applicationContext
//                AppLovinSdk.getInstance(context).settings.setVerboseLogging(true)
//
////                TapMindAdapterApplovin.getInstance(context, sdkKey)
//
//                Executors.newSingleThreadExecutor().execute {
//                    val gaid = try {
//                        AdvertisingIdClient.getAdvertisingIdInfo(context).id
//                    } catch (_: Throwable) {
//                        null
//                    }
//
//                    runOnUiThread {
//                        val builder = AppLovinSdkInitializationConfiguration
//                            .builder(sdkKey, context)
//                            .setMediationProvider(AppLovinMediationProvider.MAX)
//
//                        gaid?.let {
//                            builder.setTestDeviceAdvertisingIds(listOf(it))
//                        }
//
//                        val sdk = AppLovinSdk.getInstance(context)
//
//                        sdk.initialize(builder.build()) { sdkConfig ->
//                            Log.d(TAG, "AppLovin SDK initialized: $sdkConfig")
//
//                            isAppLovinInitialized = true
//
//                            val networks = sdk.availableMediatedNetworks
//                            Log.d("App@@@", "Total Networks: ${networks.size}")
//                            networks.forEachIndexed { index, network ->
//                                Log.d("App@@@", "Network ${index + 1}: $network")
//                            }
//
////                             sdk.showMediationDebugger()
//                        }
//                    }
                }
            }
        }

//        val context: Context = this
//        MobileAds.initialize(context) { status ->
//            for ((adapter, state) in status.adapterStatusMap) {
//                Log.d(TAG, "Adapter: $adapter, status: ${state.description}")
//            }
//        }
//
        verifyCustomAdapter()

//        Facebook().init(this)
//        init(this)

//        val YOUR_SDK_KEY = "05TMDQ5tZabpXQ45_UTbmEGNUtVAzSTzT6KmWQc5_CuWdzccS4DCITZoL3yIWUG3bbq60QC_d4WF28tUC4gVTF"
//
//        val executor = Executors.newSingleThreadExecutor();
//        executor.execute {
//            val initConfigBuilder = AppLovinSdkInitializationConfiguration.builder(YOUR_SDK_KEY, this)
//            initConfigBuilder.mediationProvider = AppLovinMediationProvider.MAX
//
////         Enable test mode by default for the current device. Cannot be run on the main thread.
//            val currentGaid = AdvertisingIdClient.getAdvertisingIdInfo(this).id
//
//
//            if (currentGaid != null) {
//                initConfigBuilder.testDeviceAdvertisingIds = Collections.singletonList(currentGaid)
//            }
//
////         Initialize the AppLovin SDK
//            val sdk = AppLovinSdk.getInstance(this)
//            sdk.initialize(initConfigBuilder.build()) {
//                val tapMindsMediationAdapter = TapMindsMediationAdapter(sdk)
//                tapMindsMediationAdapter.initialize(initParams, this@App) { status, _ ->
//                    Log.d("Mediation", "Adapter initialized: $status")
//
//                }
//                Log.d(TAG,"AppLovinSdk onInitSuccess")
//            }
//
//            executor.shutdown()
//        }
//
//        init(this)
    }

    private fun verifyCustomAdapter() {
        try {
            val adapterClass =
//                Class.forName("com.ironsource.adapters.custom.tapmind.TapMindCustomAdapter")
//                Class.forName("com.tapmimd.ads.mediation.adapter.TapMindAdapterAdmob")
                Class.forName("com.tapmimd.ads.mediation.adapter.TapMindAdapterApplovin")
            Log.d(TAG, "✓ Custom TapMind adapter found in classpath")
            Log.d(TAG, "  Class: ${adapterClass.name}")
        } catch (_: ClassNotFoundException) {
            Log.e(TAG, "✗ Custom TapMind adapter NOT found in classpath")
            Log.e(TAG, "  Make sure the adapter library is added as a dependency")
        }
    }

//    fun init(context: Context) {
//        LevelPlay.setAdaptersDebug(true)
////        85460dcd
////        2473dffcd
//        val initRequest = LevelPlayInitRequest.Builder("2473dffcd")
//            .build()
//
//        LevelPlay.init(context, initRequest, object : LevelPlayInitListener {
//            override fun onInitSuccess(configuration: LevelPlayConfiguration) {
//                Log.d(TAG, "LevelPlay onInitSuccess")
//                LevelPlay.setMetaData("is_test_suite", "enable");
//                LevelPlay.launchTestSuite(context);
//
//
//            }
//
//            override fun onInitFailed(error: LevelPlayInitError) {
//                Log.d(TAG, "LevelPlay onInitFailed")
//            }
//
//        })
//    }
}