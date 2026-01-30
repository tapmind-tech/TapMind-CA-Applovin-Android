package com.tapmimd.ads.mediation.adapter

import com.ironsource.mediationsdk.ads.nativead.AdapterNativeAdData
import com.ironsource.mediationsdk.ads.nativead.interfaces.NativeAdDataInterface
import com.tapminds.ads.native.TapMindNativeAd

class IronSourceUnifiedNativeAdMapperIronSouce(val tapMindNativeAd: TapMindNativeAd): AdapterNativeAdData() {
    override fun getTitle(): String? {
        return tapMindNativeAd.title
    }

    override fun getAdvertiser(): String? {
        return tapMindNativeAd.advertiser
    }

    override fun getBody(): String? {
        return tapMindNativeAd.body
    }

    override fun getCallToAction(): String? {
        return tapMindNativeAd.callToAction
    }

    override fun getIcon(): NativeAdDataInterface.Image {
        return NativeAdDataInterface.Image(tapMindNativeAd.icon?.drawable,tapMindNativeAd.icon?.uri)
    }

}