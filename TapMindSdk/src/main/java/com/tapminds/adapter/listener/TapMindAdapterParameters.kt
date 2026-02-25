package com.tapminds.adapter.listener

import android.os.Bundle
import androidx.annotation.Nullable

interface TapMindAdapterParameters {

    fun getAdUnitId(): String

    fun getLocalExtraParameters(): Map<String, Any>

    fun getServerParameters(): Bundle

    fun getCustomParameters(): Bundle

    @Nullable
    fun hasUserConsent(): Boolean?

    /** @deprecated */
    @Deprecated("Use updated privacy API instead")
    @Nullable
    fun isAgeRestrictedUser(): Boolean?

    @Nullable
    fun isDoNotSell(): Boolean?

    @Nullable
    fun getConsentString(): String?

    fun isTesting(): Boolean
}