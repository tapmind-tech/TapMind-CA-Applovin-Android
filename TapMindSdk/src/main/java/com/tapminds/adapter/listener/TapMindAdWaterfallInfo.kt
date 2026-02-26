package com.tapminds.adapter.listener

interface MaxAdWaterfallInfo {

//    fun getLoadedAd(): TapMindAd

    fun getName(): String

    fun getTestName(): String

//    fun getNetworkResponses(): List<MaxNetworkResponseInfo>

    fun getLatencyMillis(): Long
}