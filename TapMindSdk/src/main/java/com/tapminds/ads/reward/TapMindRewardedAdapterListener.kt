package com.tapminds.ads.reward

import android.os.Bundle
import com.tapminds.adapter.listener.TapMindAdapterError

interface TapMindRewardedAdapterListener {


    open fun onRewardedAdLoaded()

    open fun onRewardedAdLoaded(bundle: Bundle?)

    open fun onRewardedAdLoadFailed(tapMindAdapterError: TapMindAdapterError)

    open fun onRewardedAdDisplayed()

    open fun onRewardedAdDisplayed(bundle: Bundle)

    open fun onRewardedAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?)

    open fun onRewardedAdDisplayFailed(tapMindAdapterError: TapMindAdapterError?, bundle: Bundle?)

    open fun onRewardedAdClicked()

    open fun onRewardedAdClicked(bundle: Bundle?)

    open fun onRewardedAdHidden()

    open fun onRewardedAdHidden(bundle: Bundle?)

    open fun onUserRewarded(tapMindReward: TapMindReward)

    open fun onUserRewarded(tapMindReward: TapMindReward?, bundle: Bundle?)
}