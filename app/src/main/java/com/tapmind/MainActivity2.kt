package com.tapmind

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity2 : AppCompatActivity() {

    private val TAG = "APP@@@"

    private lateinit var admob: Admob
    private lateinit var facebook: Facebook
    private lateinit var ironsource: Ironsource
    private lateinit var tapMinds :  TapMinds
    private lateinit var appLovinMax: AppLovinMax
    private lateinit var activity : Activity
    private lateinit var loader : ProgressBar
    private lateinit var adContainer : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activity = this
        admob = Admob()
        facebook = Facebook()
//        tapMinds = TapMinds()
//        appLovinMax = AppLovinMax()
        ironsource = Ironsource()
//        tapMinds.init(activity)

        loader = findViewById<ProgressBar>(R.id.loader)
        adContainer = findViewById<FrameLayout>(R.id.adContainer)

        findViewById<MaterialButton>(R.id.admobBanner).setOnClickListener{
            admob.showAdmobBannerAd(activity,loader,adContainer)
        }


        findViewById<MaterialButton>(R.id.admobNative).setOnClickListener{
            admob.showAdmobNativeAd(activity,loader,adContainer)
        }

        findViewById<MaterialButton>(R.id.fbBanner).setOnClickListener{
            tapMinds.showFbBannerAd(activity,loader,adContainer)
        }

        findViewById<MaterialButton>(R.id.fbNative).setOnClickListener{
            facebook.showFbNativeAd(activity,loader,adContainer)
        }

        findViewById<MaterialButton>(R.id.bannerIronsource).setOnClickListener{
            ironsource.showIronsourceBannerAd(activity,loader,adContainer)
        }

        findViewById<MaterialButton>(R.id.nativeIronsource).setOnClickListener{
            ironsource.showIronsourceNativeAd(activity,loader,adContainer)

        }

        findViewById<MaterialButton>(R.id.bannerApplovin).setOnClickListener{
            appLovinMax.showAppLovinMaxBannerAd(activity,loader,adContainer)
        }

        findViewById<MaterialButton>(R.id.nativeApplovin).setOnClickListener{
            appLovinMax.showAppLovinMaxNativeAd(activity,loader,adContainer)
        }

    }
}


