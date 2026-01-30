package com.tapmimd.ads.mediation.adapter

import android.content.Context
import android.util.Log
import com.ironsource.mediationsdk.adunit.adapter.BaseAdapter
import com.ironsource.mediationsdk.adunit.adapter.listener.NetworkInitializationListener
import com.ironsource.mediationsdk.adunit.adapter.utility.AdData
import com.ironsource.mediationsdk.adunit.adapter.utility.AdapterErrors

class TapMindAdapterIronSource : BaseAdapter() {

    private val TAG = "APP@@@"
    private val TAG1 = "TapMindAdapterIronSource"

    private val ADAPTER_VERSION = "1.0.0"
    private val SDK_VERSION = "1.0.0"   // Your SDK version

    override fun init(
        adData: AdData,
        context: Context,
        initializationListener: NetworkInitializationListener?
    ) {
        try {
            initializationListener?.onInitSuccess()
            Log.e("App@@@", "initializationListener onInitSuccess: IronSource")
        } catch (_: Exception) {
            initializationListener?.onInitFailed(
                AdapterErrors.ADAPTER_ERROR_MISSING_PARAMS,
                "Init failed"
            )
        }
    }

    override fun getNetworkSDKVersion(): String {
        return SDK_VERSION
    }

    override fun getAdapterVersion(): String {
        return ADAPTER_VERSION
    }

    override fun setAdapterDebug(p0: Boolean) {
        super.setAdapterDebug(true)
    }
}