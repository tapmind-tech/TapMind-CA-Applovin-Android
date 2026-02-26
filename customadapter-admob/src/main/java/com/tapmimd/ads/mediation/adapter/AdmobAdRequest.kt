package com.tapmimd.ads.mediation.adapter

class AdmobAdRequest {

    private var isTestMode: Boolean = false
    private var keywords: Set<String>? = null

    fun setTestMode(test: Boolean) {
        isTestMode = test
    }

    fun isTestMode(): Boolean = isTestMode

    fun setKeywords(keywords: Set<String>) {
        this.keywords = keywords
    }

    fun getKeywords(): Set<String>? = keywords
}
