package com.ironsource.adapters.custom.istapmindcustomadapter

import android.content.Context
import android.util.Log
import com.ironsource.mediationsdk.adunit.adapter.BaseAdapter
import com.ironsource.mediationsdk.adunit.adapter.listener.NetworkInitializationListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrors

class ISTapMindCustomAdapterCustomAdapter : BaseAdapter() {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindAdapterIronSource"

    companion object {
        const val ADAPTER_VERSION = "1.0.0"
        const val NETWORK_NAME = "TapMind"
        private var isInitialized = false
    }

    override fun init(
        adData: AdData,
        context: Context,
        initializationListener: NetworkInitializationListener?
    ) {
        try {
            if (isInitialized) {
                initializationListener?.onInitSuccess()
                Log.d(TAG, "Init skipped (already initialized)")
                return
            }

            isInitialized = true

            Log.d(TAG, "Init success (one-time)")
            initializationListener?.onInitSuccess()

            Log.e(TAG, "initializationListener onInitSuccess: IronSource")
        } catch (_: Exception) {
            initializationListener?.onInitFailed(
                AdapterErrors.ADAPTER_ERROR_MISSING_PARAMS,
                "Init failed"
            )
        }
    }

    override fun getNetworkSDKVersion(): String {
        return ADAPTER_VERSION
    }

    override fun getAdapterVersion(): String {
        return ADAPTER_VERSION
    }

    override fun setAdapterDebug(p0: Boolean) {
        super.setAdapterDebug(true)
    }
}