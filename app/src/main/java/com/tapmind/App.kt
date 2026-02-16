package com.tapmind

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.unity3d.mediation.LevelPlay
import com.unity3d.mediation.LevelPlayConfiguration
import com.unity3d.mediation.LevelPlayInitError
import com.unity3d.mediation.LevelPlayInitListener
import com.unity3d.mediation.LevelPlayInitRequest

class App : Application() {

    private val TAG = "APP@@@"

    companion object {
        @Volatile
        var isIronSourceInitialized: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()

//        Admob().init(this)
//        val context: Context = this
//        MobileAds.initialize(context) { status ->
//            for ((adapter, state) in status.adapterStatusMap) {
//                Log.d(TAG, "Adapter: $adapter, status: ${state.description}")
//            }
//        }
        val apiKey = "2517ad13d"
        val userId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val initRequest = LevelPlayInitRequest.Builder(apiKey)
            .withUserId(userId)
            .build()
        LevelPlay.init(this, initRequest, object : LevelPlayInitListener {
            override fun onInitFailed(error: LevelPlayInitError) {
                isIronSourceInitialized = false
                Log.e(TAG, "onInitFailed: IronSource")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        applicationContext,
                        "IronSource init failed",
                        Toast.LENGTH_SHORT
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
        verifyCustomAdapter()

//        Facebook().init(this)
//        init(this)


//        val YOUR_SDK_KEY = "05TMDQ5tZabpXQ45_UTbmEGNUtVAzSTzT6KmWQc5_CuWdzccS4DCITZoL3yIWUG3bbq60QC_d4WF28tUC4gVTF"
//
//        val executor = Executors.newSingleThreadExecutor();
//        executor.execute {
//            val initConfigBuilder = AppLovinSdkInitializationConfiguration.builder(YOUR_SDK_KEY, this)
//            initConfigBuilder.mediationProvider = AppLovinMediationProvider.MAX

        // Enable test mode by default for the current device. Cannot be run on the main thread.
//            val currentGaid = AdvertisingIdClient.getAdvertisingIdInfo(this).id


//            if (currentGaid != null) {
//                initConfigBuilder.testDeviceAdvertisingIds = Collections.singletonList(currentGaid)
//            }

        // Initialize the AppLovin SDK
//            val sdk = AppLovinSdk.getInstance(this)
//            sdk.initialize(initConfigBuilder.build()) {
//                val tapMindsMediationAdapter = TapMindsMediationAdapter(sdk)
//                tapMindsMediationAdapter.initialize(initParams, this@App) { status, _ ->
//                    Log.d("Mediation", "Adapter initialized: $status")
//
//                }
//                Log.d(TAG,"AppLovinSdk onInitSuccess")
//            }

//            executor.shutdown()
//        }

//        init(this)

    }

    private fun verifyCustomAdapter() {
        try {
            val adapterClass =
                Class.forName("com.ironsource.adapters.custom.tapmind.TapMindCustomAdapter")
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