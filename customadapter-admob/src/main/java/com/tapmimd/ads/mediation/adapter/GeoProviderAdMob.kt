package com.tapmimd.ads.mediation.adapter

import android.content.Context

object GeoProviderAdMob {

    private val data = mapOf(

        // ================= ASIA =================
        "IN" to GeoInfoAdMob("Asia", "Southern Asia"),
        "PK" to GeoInfoAdMob("Asia", "Southern Asia"),
        "BD" to GeoInfoAdMob("Asia", "Southern Asia"),
        "NP" to GeoInfoAdMob("Asia", "Southern Asia"),
        "LK" to GeoInfoAdMob("Asia", "Southern Asia"),
        "AF" to GeoInfoAdMob("Asia", "Southern Asia"),

        "CN" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "JP" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "KR" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "KP" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "MN" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "TW" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "HK" to GeoInfoAdMob("Asia", "Eastern Asia"),
        "MO" to GeoInfoAdMob("Asia", "Eastern Asia"),

        "ID" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "SG" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "MY" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "TH" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "VN" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "PH" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "MM" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "KH" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "LA" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "BN" to GeoInfoAdMob("Asia", "South-Eastern Asia"),
        "TL" to GeoInfoAdMob("Asia", "South-Eastern Asia"),

        "AE" to GeoInfoAdMob("Asia", "Western Asia"),
        "SA" to GeoInfoAdMob("Asia", "Western Asia"),
        "IL" to GeoInfoAdMob("Asia", "Western Asia"),
        "TR" to GeoInfoAdMob("Asia", "Western Asia"),
        "IR" to GeoInfoAdMob("Asia", "Western Asia"),
        "IQ" to GeoInfoAdMob("Asia", "Western Asia"),
        "QA" to GeoInfoAdMob("Asia", "Western Asia"),
        "KW" to GeoInfoAdMob("Asia", "Western Asia"),
        "OM" to GeoInfoAdMob("Asia", "Western Asia"),
        "JO" to GeoInfoAdMob("Asia", "Western Asia"),
        "LB" to GeoInfoAdMob("Asia", "Western Asia"),
        "YE" to GeoInfoAdMob("Asia", "Western Asia"),
        "SY" to GeoInfoAdMob("Asia", "Western Asia"),
        "BH" to GeoInfoAdMob("Asia", "Western Asia"),

        "KZ" to GeoInfoAdMob("Asia", "Central Asia"),
        "UZ" to GeoInfoAdMob("Asia", "Central Asia"),
        "TM" to GeoInfoAdMob("Asia", "Central Asia"),
        "TJ" to GeoInfoAdMob("Asia", "Central Asia"),
        "KG" to GeoInfoAdMob("Asia", "Central Asia"),

        // ================= EUROPE =================
        "DE" to GeoInfoAdMob("Europe", "Western Europe"),
        "FR" to GeoInfoAdMob("Europe", "Western Europe"),
        "NL" to GeoInfoAdMob("Europe", "Western Europe"),
        "BE" to GeoInfoAdMob("Europe", "Western Europe"),
        "CH" to GeoInfoAdMob("Europe", "Western Europe"),
        "AT" to GeoInfoAdMob("Europe", "Western Europe"),
        "LU" to GeoInfoAdMob("Europe", "Western Europe"),

        "GB" to GeoInfoAdMob("Europe", "Northern Europe"),
        "IE" to GeoInfoAdMob("Europe", "Northern Europe"),
        "SE" to GeoInfoAdMob("Europe", "Northern Europe"),
        "NO" to GeoInfoAdMob("Europe", "Northern Europe"),
        "DK" to GeoInfoAdMob("Europe", "Northern Europe"),
        "FI" to GeoInfoAdMob("Europe", "Northern Europe"),
        "IS" to GeoInfoAdMob("Europe", "Northern Europe"),

        "IT" to GeoInfoAdMob("Europe", "Southern Europe"),
        "ES" to GeoInfoAdMob("Europe", "Southern Europe"),
        "PT" to GeoInfoAdMob("Europe", "Southern Europe"),
        "GR" to GeoInfoAdMob("Europe", "Southern Europe"),
        "RS" to GeoInfoAdMob("Europe", "Southern Europe"),
        "HR" to GeoInfoAdMob("Europe", "Southern Europe"),
        "SI" to GeoInfoAdMob("Europe", "Southern Europe"),
        "BA" to GeoInfoAdMob("Europe", "Southern Europe"),
        "ME" to GeoInfoAdMob("Europe", "Southern Europe"),
        "MK" to GeoInfoAdMob("Europe", "Southern Europe"),
        "AL" to GeoInfoAdMob("Europe", "Southern Europe"),

        "PL" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "CZ" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "HU" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "RO" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "UA" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "BY" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "BG" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "SK" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "MD" to GeoInfoAdMob("Europe", "Eastern Europe"),
        "RU" to GeoInfoAdMob("Europe", "Eastern Europe"),

        // ================= AFRICA =================
        "NG" to GeoInfoAdMob("Africa", "Western Africa"),
        "GH" to GeoInfoAdMob("Africa", "Western Africa"),
        "SN" to GeoInfoAdMob("Africa", "Western Africa"),
        "CI" to GeoInfoAdMob("Africa", "Western Africa"),
        "CM" to GeoInfoAdMob("Africa", "Middle Africa"),
        "TD" to GeoInfoAdMob("Africa", "Middle Africa"),
        "CG" to GeoInfoAdMob("Africa", "Middle Africa"),
        "CD" to GeoInfoAdMob("Africa", "Middle Africa"),
        "GA" to GeoInfoAdMob("Africa", "Middle Africa"),
        "GQ" to GeoInfoAdMob("Africa", "Middle Africa"),

        "KE" to GeoInfoAdMob("Africa", "Eastern Africa"),
        "UG" to GeoInfoAdMob("Africa", "Eastern Africa"),
        "TZ" to GeoInfoAdMob("Africa", "Eastern Africa"),
        "ET" to GeoInfoAdMob("Africa", "Eastern Africa"),
        "SO" to GeoInfoAdMob("Africa", "Eastern Africa"),
        "RW" to GeoInfoAdMob("Africa", "Eastern Africa"),
        "BI" to GeoInfoAdMob("Africa", "Eastern Africa"),

        "ZA" to GeoInfoAdMob("Africa", "Southern Africa"),
        "BW" to GeoInfoAdMob("Africa", "Southern Africa"),
        "ZW" to GeoInfoAdMob("Africa", "Southern Africa"),
        "ZM" to GeoInfoAdMob("Africa", "Southern Africa"),
        "NA" to GeoInfoAdMob("Africa", "Southern Africa"),
        "LS" to GeoInfoAdMob("Africa", "Southern Africa"),
        "SZ" to GeoInfoAdMob("Africa", "Southern Africa"),
        "MZ" to GeoInfoAdMob("Africa", "Eastern Africa"),

        "EG" to GeoInfoAdMob("Africa", "Northern Africa"),
        "MA" to GeoInfoAdMob("Africa", "Northern Africa"),
        "DZ" to GeoInfoAdMob("Africa", "Northern Africa"),
        "TN" to GeoInfoAdMob("Africa", "Northern Africa"),
        "LY" to GeoInfoAdMob("Africa", "Northern Africa"),
        "SD" to GeoInfoAdMob("Africa", "Northern Africa"),

        // ================= AMERICA =================
        "US" to GeoInfoAdMob("America", "Northern America"),
        "CA" to GeoInfoAdMob("America", "Northern America"),

        "MX" to GeoInfoAdMob("America", "Central America"),
        "GT" to GeoInfoAdMob("America", "Central America"),
        "CU" to GeoInfoAdMob("America", "Caribbean"),
        "DO" to GeoInfoAdMob("America", "Caribbean"),
        "JM" to GeoInfoAdMob("America", "Caribbean"),
        "HT" to GeoInfoAdMob("America", "Caribbean"),

        "BR" to GeoInfoAdMob("America", "South America"),
        "AR" to GeoInfoAdMob("America", "South America"),
        "CL" to GeoInfoAdMob("America", "South America"),
        "CO" to GeoInfoAdMob("America", "South America"),
        "PE" to GeoInfoAdMob("America", "South America"),
        "VE" to GeoInfoAdMob("America", "South America"),
        "EC" to GeoInfoAdMob("America", "South America"),
        "BO" to GeoInfoAdMob("America", "South America"),
        "PY" to GeoInfoAdMob("America", "South America"),
        "UY" to GeoInfoAdMob("America", "South America"),

        // ================= OCEANIA =================
        "AU" to GeoInfoAdMob("Oceania", "Australia & New Zealand"),
        "NZ" to GeoInfoAdMob("Oceania", "Australia & New Zealand"),
        "PG" to GeoInfoAdMob("Oceania", "Melanesia"),
        "FJ" to GeoInfoAdMob("Oceania", "Melanesia"),
        "SB" to GeoInfoAdMob("Oceania", "Melanesia"),
        "VU" to GeoInfoAdMob("Oceania", "Melanesia"),
        "NC" to GeoInfoAdMob("Oceania", "Melanesia"),

        "FM" to GeoInfoAdMob("Oceania", "Micronesia"),
        "GU" to GeoInfoAdMob("Oceania", "Micronesia"),
        "KI" to GeoInfoAdMob("Oceania", "Micronesia"),
        "MH" to GeoInfoAdMob("Oceania", "Micronesia"),
        "NR" to GeoInfoAdMob("Oceania", "Micronesia"),
        "PW" to GeoInfoAdMob("Oceania", "Micronesia"),

        "WS" to GeoInfoAdMob("Oceania", "Polynesia"),
        "TO" to GeoInfoAdMob("Oceania", "Polynesia"),
        "TV" to GeoInfoAdMob("Oceania", "Polynesia"),

        "AQ" to GeoInfoAdMob("Antarctica", "Antarctica")
    )

    fun get(countryCode: String): GeoInfoAdMob =
        data[countryCode.uppercase()] ?: GeoInfoAdMob("Unknown", "Unknown")


    fun getAppInfo(context: Context): AppInfoAdMob {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val appName = packageManager.getApplicationLabel(context.applicationInfo).toString()

            AppInfoAdMob(
                appName = appName,
                versionName = packageInfo.versionName ?: "unknown",
                packageName = packageName
            )

        } catch (e: Exception) {
            AppInfoAdMob(
                appName = "Unknown",
                versionName = "unknown",
                packageName = "Unknown"
            )
        }
    }

    const val sdkVersion = "1.0.25"
}

data class AppInfoAdMob(
    val appName: String,
    val versionName: String,
    val packageName: String,
)

data class GeoInfoAdMob(
    val continent: String,
    val region: String
)