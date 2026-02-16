package com.tapminds.adapter.listener


class TapMindAdapterError : TapMindErrorImpl {

    companion object {
        const val ERROR_CODE_NO_FILL = 204
        val NO_FILL = TapMindAdapterError(204, "No Fill")

        const val ERROR_CODE_UNSPECIFIED = -5200
        val UNSPECIFIED = TapMindAdapterError(-5200, "Unspecified Error")

        const val ERROR_CODE_INVALID_LOAD_STATE = -5201
        val INVALID_LOAD_STATE = TapMindAdapterError(-5201, "Invalid Load State")

        const val ERROR_CODE_INVALID_CONFIGURATION = -5202
        val INVALID_CONFIGURATION = TapMindAdapterError(-5202, "Invalid Configuration")

        const val ERROR_CODE_BAD_REQUEST = -5203
        val BAD_REQUEST = TapMindAdapterError(-5203, "Bad Request")

        const val ERROR_CODE_NOT_INITIALIZED = -5204
        val NOT_INITIALIZED = TapMindAdapterError(-5204, "Not Initialized")

        const val ERROR_CODE_AD_NOT_READY = -5205
        val AD_NOT_READY = TapMindAdapterError(-5205, "Ad Not Ready")

        const val ERROR_CODE_TIMEOUT = -5206
        val TIMEOUT = TapMindAdapterError(-5206, "Request Timed Out")

        const val ERROR_CODE_NO_CONNECTION = -5207
        val NO_CONNECTION = TapMindAdapterError(-5207, "No Connection")

        const val ERROR_CODE_SERVER_ERROR = -5208
        val SERVER_ERROR = TapMindAdapterError(-5208, "Server Error")

        const val ERROR_CODE_INTERNAL_ERROR = -5209
        val INTERNAL_ERROR = TapMindAdapterError(-5209, "Internal Error")

        const val ERROR_CODE_SIGNAL_COLLECTION_TIMEOUT = -5210
        val SIGNAL_COLLECTION_TIMEOUT = TapMindAdapterError(-5210, "Signal Collection Timed Out")

        const val ERROR_CODE_SIGNAL_COLLECTION_NOT_SUPPORTED = -5211
        val SIGNAL_COLLECTION_NOT_SUPPORTED = TapMindAdapterError(-5211, "Signal Collection Not Supported")

        const val ERROR_CODE_WEBVIEW_ERROR = -5212
        val WEBVIEW_ERROR = TapMindAdapterError(-5212, "WebView Error")

        const val ERROR_CODE_AD_EXPIRED = -5213
        val AD_EXPIRED = TapMindAdapterError(-5213, "Ad Expired")

        const val ERROR_CODE_AD_FREQUENCY_CAPPED = -5214
        val AD_FREQUENCY_CAPPED = TapMindAdapterError(-5214, "Ad Frequency Capped")

        const val ERROR_CODE_REWARD_ERROR = -5302
        val REWARD_ERROR = TapMindAdapterError(-5302, "Reward Error")

        const val ERROR_CODE_MISSING_REQUIRED_NATIVE_AD_ASSETS = -5400
        val MISSING_REQUIRED_NATIVE_AD_ASSETS =
            TapMindAdapterError(-5400, "Missing Native Ad Assets")

        const val ERROR_CODE_MISSING_ACTIVITY = -5601
        val MISSING_ACTIVITY = TapMindAdapterError(-5601, "Missing Activity")

        const val ERROR_CODE_AD_DISPLAY_FAILED = -4205
        val AD_DISPLAY_FAILED = TapMindAdapterError(-4205, "Ad Display Failed")
    }

    // Constructors
    constructor(code: Int) : super(code, "")

    constructor(message: String) : super(-1, message)

    constructor(code: Int, message: String) : super(code, message, 0, "")

    constructor(error: TapMindAdapterError, mediatedErrorCode: Int, mediatedErrorMessage: String) :
            super(error.getErrorCode(), error.getErrorMessage(), mediatedErrorCode, mediatedErrorMessage)

    constructor(code: Int, message: String, mediatedErrorCode: Int, mediatedErrorMessage: String) :
            super(code, message, mediatedErrorCode, mediatedErrorMessage)

}