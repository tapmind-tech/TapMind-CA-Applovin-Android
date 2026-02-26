package com.tapminds.tapmindsads

object StringUtils {


    @JvmStatic
    fun emptyIfNull(value: String?): String {
        return value ?: ""
    }

    @JvmStatic
    fun isValidString(value: String?): Boolean {
        return !value.isNullOrEmpty()
    }

    @JvmStatic
    fun isNumber(value: String?): Boolean {
        return value?.toDoubleOrNull() != null
    }

    @JvmStatic
    fun toUpperCase(value: String?): String? {
        return value?.uppercase()
    }

    @JvmStatic
    fun toLowerCase(value: String?): String? {
        return value?.lowercase()
    }

    /**
     * Returns true if `text` starts with ANY prefix from the list.
     * Example: startsWithAtLeastOnePrefix("android.sdk", listOf("and", "gen"))
     */
    @JvmStatic
    fun startsWithAtLeastOnePrefix(text: String?, prefixes: List<String>?): Boolean {
        if (text.isNullOrEmpty() || prefixes.isNullOrEmpty()) return false

        for (prefix in prefixes) {
            if (text.startsWith(prefix, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun contains(text: String?, value: String?, ignoreCase: Boolean = false): Boolean {
        if (text == null || value == null) return false
        return text.contains(value, ignoreCase)
    }

    @JvmStatic
    fun equals(a: String?, b: String?, ignoreCase: Boolean = false): Boolean {
        return a?.equals(b ?: "", ignoreCase) ?: (b.isNullOrEmpty())
    }
}
