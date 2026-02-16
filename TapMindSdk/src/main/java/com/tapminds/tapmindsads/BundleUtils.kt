package com.tapminds.tapmindsads

import android.os.Bundle

object BundleUtils {

    @JvmStatic
    fun getString(key: String, defaultValue: String? = null, bundle: Bundle?): String? {
        return bundle?.getString(key) ?: defaultValue
    }

    @JvmStatic
    fun getBoolean(key: String, bundle: Bundle?,defaultValue: Boolean = false) : Boolean {
        return bundle?.getBoolean(key) ?: defaultValue
    }

    @JvmStatic
    fun getInt( key: String, defaultValue: Int = 0,bundle: Bundle?): Int {
        return bundle?.getInt(key, defaultValue) ?: defaultValue
    }

    @JvmStatic
    fun getLong(bundle: Bundle?, key: String, defaultValue: Long = 0L): Long {
        return bundle?.getLong(key, defaultValue) ?: defaultValue
    }

    @JvmStatic
    fun getDouble(bundle: Bundle?, key: String, defaultValue: Double = 0.0): Double {
        return bundle?.getDouble(key, defaultValue) ?: defaultValue
    }

    @JvmStatic
    fun getStringArrayList(bundle: Bundle?, key: String): ArrayList<String>? {
        return bundle?.getStringArrayList(key)
    }

    @JvmStatic
    fun getBundle(bundle: Bundle?, key: String): Bundle? {
        return bundle?.getBundle(key)
    }

    @JvmStatic
    fun putIfNotNull(bundle: Bundle, key: String, value: Any?) {
        if (value != null) {
            when (value) {
                is String -> bundle.putString(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Bundle -> bundle.putBundle(key, value)
                is ArrayList<*> -> bundle.putStringArrayList(key, value as ArrayList<String>)
            }
        }
    }
}
