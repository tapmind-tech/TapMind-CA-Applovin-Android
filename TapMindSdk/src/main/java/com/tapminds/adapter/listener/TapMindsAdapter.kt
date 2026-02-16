package com.tapminds.adapter.listener

import android.content.Context

interface TapMindsAdapter {
    fun initialize(
        tapMindsAdapterInitializationParameters: TapMindsAdapterInitializationParameters?,
        context: Context?,
        onCompletionListener: OnCompletionListener?
    )

//    fun getSdkVersion(): String
//
//    fun getAdapterVersion(): String
//
//    fun isBeta(): Boolean
//
//    fun shouldInitializeOnUiThread(): Boolean?
//
//    fun shouldCollectSignalsOnUiThread(): Boolean?
//
//    fun shouldLoadAdsOnUiThread(format: TapMindAdFormat): Boolean?
//
//    fun shouldShowAdsOnUiThread(format: TapMindAdFormat): Boolean?
//
//    fun shouldDestroyOnUiThread(): Boolean?
//
//    fun onDestroy()

    interface OnCompletionListener {
        fun onCompletion(var1: InitializationStatus?, var2: String?)
    }

    enum class InitializationStatus(val code: Int) {
        NOT_INITIALIZED(-4),
        DOES_NOT_APPLY(-3),
        INITIALIZING(-2),
        INITIALIZED_UNKNOWN(-1),
        INITIALIZED_FAILURE(0),
        INITIALIZED_SUCCESS(1)
    }
}