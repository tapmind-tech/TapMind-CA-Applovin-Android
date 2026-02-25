package com.tapminds.adapter.listener

interface  TapMindError {

    fun getCode(): Int

    fun getMessage(): String

    fun getMediatedNetworkErrorCode(): Int

    fun getMediatedNetworkErrorMessage(): String

    fun getWaterfall(): MaxAdWaterfallInfo?

    fun getRequestLatencyMillis(): Long


    fun getAdLoadFailureInfo(): String
}