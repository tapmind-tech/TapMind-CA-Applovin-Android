package com.ironsource.adapters.custom.istapmindcustomadapter

import android.content.Context

object GeoProviderIronSource {

    private val data = mapOf(

        // ================= ASIA =================
        "IN" to GeoInfoIronSource("Asia", "Southern Asia"),
        "PK" to GeoInfoIronSource("Asia", "Southern Asia"),
        "BD" to GeoInfoIronSource("Asia", "Southern Asia"),
        "NP" to GeoInfoIronSource("Asia", "Southern Asia"),
        "LK" to GeoInfoIronSource("Asia", "Southern Asia"),
        "AF" to GeoInfoIronSource("Asia", "Southern Asia"),

        "CN" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "JP" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "KR" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "KP" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "MN" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "TW" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "HK" to GeoInfoIronSource("Asia", "Eastern Asia"),
        "MO" to GeoInfoIronSource("Asia", "Eastern Asia"),

        "ID" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "SG" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "MY" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "TH" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "VN" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "PH" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "MM" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "KH" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "LA" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "BN" to GeoInfoIronSource("Asia", "South-Eastern Asia"),
        "TL" to GeoInfoIronSource("Asia", "South-Eastern Asia"),

        "AE" to GeoInfoIronSource("Asia", "Western Asia"),
        "SA" to GeoInfoIronSource("Asia", "Western Asia"),
        "IL" to GeoInfoIronSource("Asia", "Western Asia"),
        "TR" to GeoInfoIronSource("Asia", "Western Asia"),
        "IR" to GeoInfoIronSource("Asia", "Western Asia"),
        "IQ" to GeoInfoIronSource("Asia", "Western Asia"),
        "QA" to GeoInfoIronSource("Asia", "Western Asia"),
        "KW" to GeoInfoIronSource("Asia", "Western Asia"),
        "OM" to GeoInfoIronSource("Asia", "Western Asia"),
        "JO" to GeoInfoIronSource("Asia", "Western Asia"),
        "LB" to GeoInfoIronSource("Asia", "Western Asia"),
        "YE" to GeoInfoIronSource("Asia", "Western Asia"),
        "SY" to GeoInfoIronSource("Asia", "Western Asia"),
        "BH" to GeoInfoIronSource("Asia", "Western Asia"),

        "KZ" to GeoInfoIronSource("Asia", "Central Asia"),
        "UZ" to GeoInfoIronSource("Asia", "Central Asia"),
        "TM" to GeoInfoIronSource("Asia", "Central Asia"),
        "TJ" to GeoInfoIronSource("Asia", "Central Asia"),
        "KG" to GeoInfoIronSource("Asia", "Central Asia"),

        // ================= EUROPE =================
        "DE" to GeoInfoIronSource("Europe", "Western Europe"),
        "FR" to GeoInfoIronSource("Europe", "Western Europe"),
        "NL" to GeoInfoIronSource("Europe", "Western Europe"),
        "BE" to GeoInfoIronSource("Europe", "Western Europe"),
        "CH" to GeoInfoIronSource("Europe", "Western Europe"),
        "AT" to GeoInfoIronSource("Europe", "Western Europe"),
        "LU" to GeoInfoIronSource("Europe", "Western Europe"),

        "GB" to GeoInfoIronSource("Europe", "Northern Europe"),
        "IE" to GeoInfoIronSource("Europe", "Northern Europe"),
        "SE" to GeoInfoIronSource("Europe", "Northern Europe"),
        "NO" to GeoInfoIronSource("Europe", "Northern Europe"),
        "DK" to GeoInfoIronSource("Europe", "Northern Europe"),
        "FI" to GeoInfoIronSource("Europe", "Northern Europe"),
        "IS" to GeoInfoIronSource("Europe", "Northern Europe"),

        "IT" to GeoInfoIronSource("Europe", "Southern Europe"),
        "ES" to GeoInfoIronSource("Europe", "Southern Europe"),
        "PT" to GeoInfoIronSource("Europe", "Southern Europe"),
        "GR" to GeoInfoIronSource("Europe", "Southern Europe"),
        "RS" to GeoInfoIronSource("Europe", "Southern Europe"),
        "HR" to GeoInfoIronSource("Europe", "Southern Europe"),
        "SI" to GeoInfoIronSource("Europe", "Southern Europe"),
        "BA" to GeoInfoIronSource("Europe", "Southern Europe"),
        "ME" to GeoInfoIronSource("Europe", "Southern Europe"),
        "MK" to GeoInfoIronSource("Europe", "Southern Europe"),
        "AL" to GeoInfoIronSource("Europe", "Southern Europe"),

        "PL" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "CZ" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "HU" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "RO" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "UA" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "BY" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "BG" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "SK" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "MD" to GeoInfoIronSource("Europe", "Eastern Europe"),
        "RU" to GeoInfoIronSource("Europe", "Eastern Europe"),

        // ================= AFRICA =================
        "NG" to GeoInfoIronSource("Africa", "Western Africa"),
        "GH" to GeoInfoIronSource("Africa", "Western Africa"),
        "SN" to GeoInfoIronSource("Africa", "Western Africa"),
        "CI" to GeoInfoIronSource("Africa", "Western Africa"),
        "CM" to GeoInfoIronSource("Africa", "Middle Africa"),
        "TD" to GeoInfoIronSource("Africa", "Middle Africa"),
        "CG" to GeoInfoIronSource("Africa", "Middle Africa"),
        "CD" to GeoInfoIronSource("Africa", "Middle Africa"),
        "GA" to GeoInfoIronSource("Africa", "Middle Africa"),
        "GQ" to GeoInfoIronSource("Africa", "Middle Africa"),

        "KE" to GeoInfoIronSource("Africa", "Eastern Africa"),
        "UG" to GeoInfoIronSource("Africa", "Eastern Africa"),
        "TZ" to GeoInfoIronSource("Africa", "Eastern Africa"),
        "ET" to GeoInfoIronSource("Africa", "Eastern Africa"),
        "SO" to GeoInfoIronSource("Africa", "Eastern Africa"),
        "RW" to GeoInfoIronSource("Africa", "Eastern Africa"),
        "BI" to GeoInfoIronSource("Africa", "Eastern Africa"),

        "ZA" to GeoInfoIronSource("Africa", "Southern Africa"),
        "BW" to GeoInfoIronSource("Africa", "Southern Africa"),
        "ZW" to GeoInfoIronSource("Africa", "Southern Africa"),
        "ZM" to GeoInfoIronSource("Africa", "Southern Africa"),
        "NA" to GeoInfoIronSource("Africa", "Southern Africa"),
        "LS" to GeoInfoIronSource("Africa", "Southern Africa"),
        "SZ" to GeoInfoIronSource("Africa", "Southern Africa"),
        "MZ" to GeoInfoIronSource("Africa", "Eastern Africa"),

        "EG" to GeoInfoIronSource("Africa", "Northern Africa"),
        "MA" to GeoInfoIronSource("Africa", "Northern Africa"),
        "DZ" to GeoInfoIronSource("Africa", "Northern Africa"),
        "TN" to GeoInfoIronSource("Africa", "Northern Africa"),
        "LY" to GeoInfoIronSource("Africa", "Northern Africa"),
        "SD" to GeoInfoIronSource("Africa", "Northern Africa"),

        // ================= AMERICA =================
        "US" to GeoInfoIronSource("America", "Northern America"),
        "CA" to GeoInfoIronSource("America", "Northern America"),

        "MX" to GeoInfoIronSource("America", "Central America"),
        "GT" to GeoInfoIronSource("America", "Central America"),
        "CU" to GeoInfoIronSource("America", "Caribbean"),
        "DO" to GeoInfoIronSource("America", "Caribbean"),
        "JM" to GeoInfoIronSource("America", "Caribbean"),
        "HT" to GeoInfoIronSource("America", "Caribbean"),

        "BR" to GeoInfoIronSource("America", "South America"),
        "AR" to GeoInfoIronSource("America", "South America"),
        "CL" to GeoInfoIronSource("America", "South America"),
        "CO" to GeoInfoIronSource("America", "South America"),
        "PE" to GeoInfoIronSource("America", "South America"),
        "VE" to GeoInfoIronSource("America", "South America"),
        "EC" to GeoInfoIronSource("America", "South America"),
        "BO" to GeoInfoIronSource("America", "South America"),
        "PY" to GeoInfoIronSource("America", "South America"),
        "UY" to GeoInfoIronSource("America", "South America"),

        // ================= OCEANIA =================
        "AU" to GeoInfoIronSource("Oceania", "Australia & New Zealand"),
        "NZ" to GeoInfoIronSource("Oceania", "Australia & New Zealand"),
        "PG" to GeoInfoIronSource("Oceania", "Melanesia"),
        "FJ" to GeoInfoIronSource("Oceania", "Melanesia"),
        "SB" to GeoInfoIronSource("Oceania", "Melanesia"),
        "VU" to GeoInfoIronSource("Oceania", "Melanesia"),
        "NC" to GeoInfoIronSource("Oceania", "Melanesia"),

        "FM" to GeoInfoIronSource("Oceania", "Micronesia"),
        "GU" to GeoInfoIronSource("Oceania", "Micronesia"),
        "KI" to GeoInfoIronSource("Oceania", "Micronesia"),
        "MH" to GeoInfoIronSource("Oceania", "Micronesia"),
        "NR" to GeoInfoIronSource("Oceania", "Micronesia"),
        "PW" to GeoInfoIronSource("Oceania", "Micronesia"),

        "WS" to GeoInfoIronSource("Oceania", "Polynesia"),
        "TO" to GeoInfoIronSource("Oceania", "Polynesia"),
        "TV" to GeoInfoIronSource("Oceania", "Polynesia"),

        "AQ" to GeoInfoIronSource("Antarctica", "Antarctica")
    )

    fun get(countryCode: String): GeoInfoIronSource =
        data[countryCode.uppercase()] ?: GeoInfoIronSource("Unknown", "Unknown")


    fun getAppInfo(context: Context): AppInfoIronSource {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val appName = packageManager.getApplicationLabel(context.applicationInfo).toString()

            AppInfoIronSource(
                appName = appName,
                versionName = packageInfo.versionName ?: "unknown",
                packageName = packageName
            )

        } catch (e: Exception) {
            AppInfoIronSource(
                appName = "Unknown",
                versionName = "unknown",
                packageName = "Unknown"
            )
        }
    }
}

data class AppInfoIronSource(
    val appName: String,
    val versionName: String,
    val packageName: String,
)

data class GeoInfoIronSource(
    val continent: String,
    val region: String
)