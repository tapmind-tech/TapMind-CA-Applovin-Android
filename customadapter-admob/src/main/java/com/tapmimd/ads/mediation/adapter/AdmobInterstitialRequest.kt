package com.tapmimd.ads.mediation.adapter

class AdmobInterstitialRequest {

    private var testMode = false
    private var keywords: Set<String>? = null

    fun setTestMode(enable: Boolean) {
        testMode = enable
    }

    fun isTestMode(): Boolean = testMode

    fun setKeywords(list: Set<String>) {
        keywords = list
    }

    fun getKeywords() = keywords
}
