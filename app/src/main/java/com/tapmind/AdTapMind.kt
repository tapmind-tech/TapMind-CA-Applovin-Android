package com.tapmind

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tapmind.databinding.ActivityAdTapMindBinding

class AdTapMind : AppCompatActivity() {
    private lateinit var binding: ActivityAdTapMindBinding
//    private var flow: String = "Admob"
//    private var flow : String = "ironsource"
    private var flow : String = "applovin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdTapMindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (flow == "ironsource") {
            binding.admobNative.visibility = View.GONE
            checkInitAndEnableButtons()
        }
        if (flow == "applovin") {
            binding.admobNative.visibility = View.GONE
            checkInitAndEnableButtons()
        }
        clickListener()
    }

    private fun clickListener() {
        binding.admobBanner.setOnClickListener {
            navigation("Banner")
        }

        binding.admobNative.setOnClickListener {
            navigation("Native")
        }

        binding.admobInterstitial.setOnClickListener {
            navigation("Interstitial")
        }

        binding.admobReward.setOnClickListener {
            navigation("Reward")
        }
    }

    private fun checkInitAndEnableButtons() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if (App.isIronSourceInitialized || App.isAppLovinInitialized) {
                    binding.progressBar.visibility = View.GONE
                    binding.admobNative.visibility = View.GONE
//                    navigation("Banner")
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    handler.postDelayed(this, 300)
                }
            }
        })
    }

    private fun navigation(value: String) {
        if (flow == "ironsource") {
            if (!App.isIronSourceInitialized) {
                Toast.makeText(this, "IronSource not initialized yet", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (flow == "applovin") {
            if (!App.isAppLovinInitialized) {
                Toast.makeText(this, "AppLovin not initialized yet", Toast.LENGTH_SHORT).show()
                return
            }
        }
        val intent = Intent(this, AdTapMindShow::class.java)
        intent.putExtra("adType", value)
        intent.putExtra("flow", flow)
        startActivity(intent)
    }
}