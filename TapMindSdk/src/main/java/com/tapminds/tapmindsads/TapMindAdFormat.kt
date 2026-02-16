package com.tapminds.tapmindsads

import android.content.Context
import android.util.Log
import androidx.annotation.Nullable

class TapMindAdFormat private constructor(
    private val label: String,
    @Deprecated("Use label instead") private val displayName: String
) {

    companion object {
        val BANNER = TapMindAdFormat("BANNER", "Banner")
        val MREC = TapMindAdFormat("MREC", "MREC")
        val LEADER = TapMindAdFormat("LEADER", "Leader")
        val INTERSTITIAL = TapMindAdFormat("INTER", "Interstitial")
        val APP_OPEN = TapMindAdFormat("APPOPEN", "App Open")
        val REWARDED = TapMindAdFormat("REWARDED", "Rewarded")
        val NATIVE = TapMindAdFormat("NATIVE", "Native")

        @Deprecated("Rewarded Interstitial is deprecated")
        val REWARDED_INTERSTITIAL =
            TapMindAdFormat("REWARDED_INTER", "Rewarded Interstitial")

        @JvmStatic
        @Nullable
        fun formatFromString(value: String?): TapMindAdFormat? {
            if (value.isNullOrEmpty()) return null

            return when {
                value.equals("banner", true) -> BANNER
                value.equals("mrec", true) -> MREC
                value.equals("native", true) -> NATIVE
                value.equals("leaderboard", true) ||
                        value.equals("leader", true) -> LEADER
                value.equals("interstitial", true) ||
                        value.equals("inter", true) -> INTERSTITIAL
                value.equals("appopen", true) ||
                        value.equals("app_open", true) -> APP_OPEN
                value.equals("rewarded", true) ||
                        value.equals("reward", true) -> REWARDED

                else -> {
                    Log.d("TapMindSdk", "Unknown ad format: $value")
                    null
                }
            }
        }
    }

    fun getLabel(): String = label

    fun getSize(): TapmindsSdkUtils.Size {
        return when (this) {
            BANNER -> TapmindsSdkUtils.Size(320, 50)
            LEADER -> TapmindsSdkUtils.Size(728, 90)
            MREC -> TapmindsSdkUtils.Size(300, 250)
            else -> TapmindsSdkUtils.Size(0, 0)
        }
    }

    fun getAdaptiveSize(context: Context): TapmindsSdkUtils.Size {
        return getAdaptiveSize(-1, context)
    }

    fun getAdaptiveSize(width: Int, context: Context): TapmindsSdkUtils.Size {
        return if (this == BANNER || this == LEADER) {
            getSize()
        } else {
            getSize()
        }
    }

    fun isFullscreenAd(): Boolean {
        return this == INTERSTITIAL || this == APP_OPEN || this == REWARDED
    }

    fun isAdViewAd(): Boolean {
        return this == BANNER || this == MREC || this == LEADER
    }

    fun isBannerOrLeaderAd(): Boolean {
        return this == BANNER || this == LEADER
    }

    override fun toString(): String {
        return "MaxAdFormat{label='$label'}"
    }

    @Deprecated("Use label instead")
    fun getDisplayName(): String = displayName
}
