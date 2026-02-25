package com.tapmimd.ads.mediation.adapter

import android.content.Context

object GeoProviderAppLovin {

    private val data = mapOf(

        // ================= ASIA =================
        "IN" to GeoInfoAppLovin("Asia", "Southern Asia"),
        "PK" to GeoInfoAppLovin("Asia", "Southern Asia"),
        "BD" to GeoInfoAppLovin("Asia", "Southern Asia"),
        "NP" to GeoInfoAppLovin("Asia", "Southern Asia"),
        "LK" to GeoInfoAppLovin("Asia", "Southern Asia"),
        "AF" to GeoInfoAppLovin("Asia", "Southern Asia"),

        "CN" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "JP" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "KR" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "KP" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "MN" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "TW" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "HK" to GeoInfoAppLovin("Asia", "Eastern Asia"),
        "MO" to GeoInfoAppLovin("Asia", "Eastern Asia"),

        "ID" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "SG" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "MY" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "TH" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "VN" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "PH" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "MM" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "KH" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "LA" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "BN" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),
        "TL" to GeoInfoAppLovin("Asia", "South-Eastern Asia"),

        "AE" to GeoInfoAppLovin("Asia", "Western Asia"),
        "SA" to GeoInfoAppLovin("Asia", "Western Asia"),
        "IL" to GeoInfoAppLovin("Asia", "Western Asia"),
        "TR" to GeoInfoAppLovin("Asia", "Western Asia"),
        "IR" to GeoInfoAppLovin("Asia", "Western Asia"),
        "IQ" to GeoInfoAppLovin("Asia", "Western Asia"),
        "QA" to GeoInfoAppLovin("Asia", "Western Asia"),
        "KW" to GeoInfoAppLovin("Asia", "Western Asia"),
        "OM" to GeoInfoAppLovin("Asia", "Western Asia"),
        "JO" to GeoInfoAppLovin("Asia", "Western Asia"),
        "LB" to GeoInfoAppLovin("Asia", "Western Asia"),
        "YE" to GeoInfoAppLovin("Asia", "Western Asia"),
        "SY" to GeoInfoAppLovin("Asia", "Western Asia"),
        "BH" to GeoInfoAppLovin("Asia", "Western Asia"),

        "KZ" to GeoInfoAppLovin("Asia", "Central Asia"),
        "UZ" to GeoInfoAppLovin("Asia", "Central Asia"),
        "TM" to GeoInfoAppLovin("Asia", "Central Asia"),
        "TJ" to GeoInfoAppLovin("Asia", "Central Asia"),
        "KG" to GeoInfoAppLovin("Asia", "Central Asia"),

        // ================= EUROPE =================
        "DE" to GeoInfoAppLovin("Europe", "Western Europe"),
        "FR" to GeoInfoAppLovin("Europe", "Western Europe"),
        "NL" to GeoInfoAppLovin("Europe", "Western Europe"),
        "BE" to GeoInfoAppLovin("Europe", "Western Europe"),
        "CH" to GeoInfoAppLovin("Europe", "Western Europe"),
        "AT" to GeoInfoAppLovin("Europe", "Western Europe"),
        "LU" to GeoInfoAppLovin("Europe", "Western Europe"),

        "GB" to GeoInfoAppLovin("Europe", "Northern Europe"),
        "IE" to GeoInfoAppLovin("Europe", "Northern Europe"),
        "SE" to GeoInfoAppLovin("Europe", "Northern Europe"),
        "NO" to GeoInfoAppLovin("Europe", "Northern Europe"),
        "DK" to GeoInfoAppLovin("Europe", "Northern Europe"),
        "FI" to GeoInfoAppLovin("Europe", "Northern Europe"),
        "IS" to GeoInfoAppLovin("Europe", "Northern Europe"),

        "IT" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "ES" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "PT" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "GR" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "RS" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "HR" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "SI" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "BA" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "ME" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "MK" to GeoInfoAppLovin("Europe", "Southern Europe"),
        "AL" to GeoInfoAppLovin("Europe", "Southern Europe"),

        "PL" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "CZ" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "HU" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "RO" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "UA" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "BY" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "BG" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "SK" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "MD" to GeoInfoAppLovin("Europe", "Eastern Europe"),
        "RU" to GeoInfoAppLovin("Europe", "Eastern Europe"),

        // ================= AFRICA =================
        "NG" to GeoInfoAppLovin("Africa", "Western Africa"),
        "GH" to GeoInfoAppLovin("Africa", "Western Africa"),
        "SN" to GeoInfoAppLovin("Africa", "Western Africa"),
        "CI" to GeoInfoAppLovin("Africa", "Western Africa"),
        "CM" to GeoInfoAppLovin("Africa", "Middle Africa"),
        "TD" to GeoInfoAppLovin("Africa", "Middle Africa"),
        "CG" to GeoInfoAppLovin("Africa", "Middle Africa"),
        "CD" to GeoInfoAppLovin("Africa", "Middle Africa"),
        "GA" to GeoInfoAppLovin("Africa", "Middle Africa"),
        "GQ" to GeoInfoAppLovin("Africa", "Middle Africa"),

        "KE" to GeoInfoAppLovin("Africa", "Eastern Africa"),
        "UG" to GeoInfoAppLovin("Africa", "Eastern Africa"),
        "TZ" to GeoInfoAppLovin("Africa", "Eastern Africa"),
        "ET" to GeoInfoAppLovin("Africa", "Eastern Africa"),
        "SO" to GeoInfoAppLovin("Africa", "Eastern Africa"),
        "RW" to GeoInfoAppLovin("Africa", "Eastern Africa"),
        "BI" to GeoInfoAppLovin("Africa", "Eastern Africa"),

        "ZA" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "BW" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "ZW" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "ZM" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "NA" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "LS" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "SZ" to GeoInfoAppLovin("Africa", "Southern Africa"),
        "MZ" to GeoInfoAppLovin("Africa", "Eastern Africa"),

        "EG" to GeoInfoAppLovin("Africa", "Northern Africa"),
        "MA" to GeoInfoAppLovin("Africa", "Northern Africa"),
        "DZ" to GeoInfoAppLovin("Africa", "Northern Africa"),
        "TN" to GeoInfoAppLovin("Africa", "Northern Africa"),
        "LY" to GeoInfoAppLovin("Africa", "Northern Africa"),
        "SD" to GeoInfoAppLovin("Africa", "Northern Africa"),

        // ================= AMERICA =================
        "US" to GeoInfoAppLovin("America", "Northern America"),
        "CA" to GeoInfoAppLovin("America", "Northern America"),

        "MX" to GeoInfoAppLovin("America", "Central America"),
        "GT" to GeoInfoAppLovin("America", "Central America"),
        "CU" to GeoInfoAppLovin("America", "Caribbean"),
        "DO" to GeoInfoAppLovin("America", "Caribbean"),
        "JM" to GeoInfoAppLovin("America", "Caribbean"),
        "HT" to GeoInfoAppLovin("America", "Caribbean"),

        "BR" to GeoInfoAppLovin("America", "South America"),
        "AR" to GeoInfoAppLovin("America", "South America"),
        "CL" to GeoInfoAppLovin("America", "South America"),
        "CO" to GeoInfoAppLovin("America", "South America"),
        "PE" to GeoInfoAppLovin("America", "South America"),
        "VE" to GeoInfoAppLovin("America", "South America"),
        "EC" to GeoInfoAppLovin("America", "South America"),
        "BO" to GeoInfoAppLovin("America", "South America"),
        "PY" to GeoInfoAppLovin("America", "South America"),
        "UY" to GeoInfoAppLovin("America", "South America"),

        // ================= OCEANIA =================
        "AU" to GeoInfoAppLovin("Oceania", "Australia & New Zealand"),
        "NZ" to GeoInfoAppLovin("Oceania", "Australia & New Zealand"),
        "PG" to GeoInfoAppLovin("Oceania", "Melanesia"),
        "FJ" to GeoInfoAppLovin("Oceania", "Melanesia"),
        "SB" to GeoInfoAppLovin("Oceania", "Melanesia"),
        "VU" to GeoInfoAppLovin("Oceania", "Melanesia"),
        "NC" to GeoInfoAppLovin("Oceania", "Melanesia"),

        "FM" to GeoInfoAppLovin("Oceania", "Micronesia"),
        "GU" to GeoInfoAppLovin("Oceania", "Micronesia"),
        "KI" to GeoInfoAppLovin("Oceania", "Micronesia"),
        "MH" to GeoInfoAppLovin("Oceania", "Micronesia"),
        "NR" to GeoInfoAppLovin("Oceania", "Micronesia"),
        "PW" to GeoInfoAppLovin("Oceania", "Micronesia"),

        "WS" to GeoInfoAppLovin("Oceania", "Polynesia"),
        "TO" to GeoInfoAppLovin("Oceania", "Polynesia"),
        "TV" to GeoInfoAppLovin("Oceania", "Polynesia"),

        "AQ" to GeoInfoAppLovin("Antarctica", "Antarctica")
    )

    fun get(countryCode: String): GeoInfoAppLovin =
        data[countryCode.uppercase()] ?: GeoInfoAppLovin("Unknown", "Unknown")


    fun getAppInfo(context: Context): AppInfoAppLovin {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val appName = packageManager.getApplicationLabel(context.applicationInfo).toString()

            AppInfoAppLovin(
                appName = appName,
                versionName = packageInfo.versionName ?: "unknown",
                packageName = packageName
            )

        } catch (e: Exception) {
            AppInfoAppLovin(
                appName = "Unknown",
                versionName = "unknown",
                packageName = "Unknown"
            )
        }
    }
}

data class AppInfoAppLovin(
    val appName: String,
    val versionName: String,
    val packageName: String,
)

data class GeoInfoAppLovin(
    val continent: String,
    val region: String
)