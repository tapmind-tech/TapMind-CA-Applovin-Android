package com.tapminds.adapter.listener

interface TapMindAdapterResponseParameters : TapMindAdapterParameters {

    fun getThirdPartyAdPlacementId(): String
    fun getBidResponse(): String
}