package com.tapminds.adapter.listener

import com.tapminds.tapmindsads.StringUtils


public open class TapMindErrorImpl : TapMindError {

    private val errorCode: Int
    private val errorMessage: String
    private val mediatedNetworkErrorCode: Int
    private val mediatedNetworkErrorMessage: String

    private var waterfall: MaxAdWaterfallInfo? = null
    private var loadTag: String? = null
    private var requestLatencyMillis: Long = 0
    private var adLoadFailureInfo: String? = null

    constructor(code: Int) : this(code, "")

    constructor(message: String) : this(-1, message)

    constructor(code: Int, message: String) : this(code, message, -1, "")

    constructor(
        code: Int,
        message: String,
        mediatedCode: Int,
        mediatedMessage: String
    ) {
        this.errorCode = code
        this.errorMessage = StringUtils.emptyIfNull(message)
        this.mediatedNetworkErrorCode = mediatedCode
        this.mediatedNetworkErrorMessage = StringUtils.emptyIfNull(mediatedMessage)
    }

    fun getErrorCode(): Int = errorCode

    fun getErrorMessage(): String = errorMessage

    override fun getCode(): Int = errorCode

    override fun getMessage(): String = errorMessage

    override fun getMediatedNetworkErrorCode(): Int = mediatedNetworkErrorCode

    override fun getMediatedNetworkErrorMessage(): String = mediatedNetworkErrorMessage

    override fun getWaterfall(): MaxAdWaterfallInfo? = waterfall

    fun setWaterfall(info: MaxAdWaterfallInfo?) {
        waterfall = info
    }

    override fun getRequestLatencyMillis(): Long = requestLatencyMillis

    fun setRequestLatencyMillis(value: Long) {
        requestLatencyMillis = value
    }

    fun setLoadTag(tag: String?) {
        loadTag = tag
    }

    fun getLoadTag(): String? = loadTag

    override fun getAdLoadFailureInfo():String = adLoadFailureInfo ?: ""

    fun setAdLoadFailureInfo(info: String?) {
        adLoadFailureInfo = info
    }

    override fun toString(): String {
        return "TapMindError{code=${getErrorCode()}, message=\"${getErrorMessage()}\", mediatedNetworkErrorCode=$mediatedNetworkErrorCode, mediatedNetworkErrorMessage=\"$mediatedNetworkErrorMessage\"}"
    }

}