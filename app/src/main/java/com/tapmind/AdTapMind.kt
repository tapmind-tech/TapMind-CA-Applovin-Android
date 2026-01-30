package com.tapmind

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tapmind.databinding.ActivityAdTapMindBinding

class AdTapMind : AppCompatActivity() {
    private lateinit var binding: ActivityAdTapMindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdTapMindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickListener()
    }

    private fun clickListener() {
        binding.admobBanner.setOnClickListener {
            val intent = Intent(this, AdTapMindShow::class.java)
            intent.putExtra("adType", "Banner")
            startActivity(intent)
        }

        binding.admobNative.setOnClickListener {
            val intent = Intent(this, AdTapMindShow::class.java)
            intent.putExtra("adType", "Native")
            startActivity(intent)
        }

        binding.admobInterstitial.setOnClickListener {
            val intent = Intent(this, AdTapMindShow::class.java)
            intent.putExtra("adType", "Interstitial")
            startActivity(intent)
        }

        binding.admobReward.setOnClickListener {
            val intent = Intent(this, AdTapMindShow::class.java)
            intent.putExtra("adType", "Reward")
            startActivity(intent)
        }
    }
}