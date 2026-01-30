package com.tapmind

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.ironsource.mediationsdk.IronSource
import com.unity3d.mediation.LevelPlay
import com.unity3d.mediation.LevelPlayConfiguration
import com.unity3d.mediation.LevelPlayInitError
import com.unity3d.mediation.LevelPlayInitListener
import com.unity3d.mediation.LevelPlayInitRequest
import java.util.concurrent.Executors

class App : Application() {

    private val TAG = "APP@@@"

    override fun onCreate() {
        super.onCreate()

        Admob().init(this)
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

    fun init(context: Context){
        LevelPlay.setAdaptersDebug(true)
//        85460dcd
//        2473dffcd
        val initRequest = LevelPlayInitRequest.Builder("2473dffcd")
            .build()

        LevelPlay.init(context, initRequest,object : LevelPlayInitListener {
            override fun onInitSuccess(configuration: LevelPlayConfiguration) {
                Log.d(TAG,"LevelPlay onInitSuccess")
                LevelPlay.setMetaData("is_test_suite", "enable");
                LevelPlay.launchTestSuite(context);




            }

            override fun onInitFailed(error: LevelPlayInitError) {
                Log.d(TAG,"LevelPlay onInitFailed")
            }

        })
    }
}