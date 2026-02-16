package com.tapminds.ads.banner

import android.os.Bundle
import android.view.View
import com.tapminds.adapter.listener.TapMindAdapterError
import com.tapminds.adapter.listener.TapMindAdapterListener

interface TapMindAdViewAdapterListener : TapMindAdapterListener {

    fun onAdViewAdLoaded(view: View)

    fun onAdViewAdLoaded(view: View, bundle: Bundle?)

    fun onAdViewAdLoadFailed(error: TapMindAdapterError)

    fun onAdViewAdDisplayed()

    fun onAdViewAdDisplayed(bundle: Bundle?)

    fun onAdViewAdDisplayFailed(error: TapMindAdapterError)

    fun onAdViewAdDisplayFailed(error: TapMindAdapterError, bundle: Bundle?)

    fun onAdViewAdClicked()

    fun onAdViewAdClicked(bundle: Bundle?)

    fun onAdViewAdHidden()

    fun onAdViewAdHidden(bundle: Bundle?)

    fun onAdViewAdExpanded()

    fun onAdViewAdExpanded(bundle: Bundle?)

    fun onAdViewAdCollapsed()

    fun onAdViewAdCollapsed(bundle: Bundle?)
}