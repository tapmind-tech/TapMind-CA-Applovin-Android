package com.tapmind

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = "APP@@@"

    private lateinit var admob: Admob
    private lateinit var tapMinds :  TapMinds
    private lateinit var ironsource: Ironsource
    private lateinit var appLovinMax: AppLovinMax
    private lateinit var activity : Activity
    private lateinit var loader : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loader = findViewById(R.id.loader)
        activity = this
        admob = Admob()
        tapMinds = TapMinds()
        ironsource = Ironsource()
        appLovinMax = AppLovinMax()
//        tapMinds.init(activity)
//        admob.init(activity)


        findViewById<MaterialButton>(R.id.admobInterstitial).setOnClickListener {
            admob.showAdmobInterstitialAd(activity)
        }

        findViewById<MaterialButton>(R.id.admobReward).setOnClickListener {
            admob.showAdmobRewardedAd(activity)
        }

        findViewById<MaterialButton>(R.id.admobAppOpen).setOnClickListener {
            admob.showAdmobAppOpenAd(activity)
        }

        findViewById<MaterialButton>(R.id.fbInterstitial).setOnClickListener {
            tapMinds.showFbInterstitialAd(activity)
        }

        findViewById<MaterialButton>(R.id.fbReward).setOnClickListener {
            tapMinds.showFbRewardedAd(activity)
        }


        findViewById<MaterialButton>(R.id.gotoNextAds).setOnClickListener {
            startActivity(Intent(activity, MainActivity2::class.java))
        }

        findViewById<MaterialButton>(R.id.interIronsource).setOnClickListener {
            ironsource.showIronsourceInterstitialAd(activity,loader)
        }
        findViewById<MaterialButton>(R.id.rewardIronsource).setOnClickListener{
            ironsource.showIronsourceRewardedAd(activity,loader)
        }

        findViewById<MaterialButton>(R.id.interApplovin).setOnClickListener {
            appLovinMax.showAppLovinMaxInterstitialAd(activity,loader)
        }

        findViewById<MaterialButton>(R.id.rewardApplovin).setOnClickListener {
            appLovinMax.showAppLovinMaxRewardedAd(activity,loader)
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Anirudh",""+getAdId(this@MainActivity))
        }

    }


    suspend fun getAdId(context: Context): String? {
        return withContext(Dispatchers.IO) {
            try {
                val info = AdvertisingIdClient.getAdvertisingIdInfo(context)
                if (!info.isLimitAdTrackingEnabled) {
                    info.id // return AdID
                } else {
                    null // user disabled ad tracking
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}