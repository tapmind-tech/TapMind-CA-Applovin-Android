package com.tapminds.tapmindsads

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.Dimension
//import com.applovin.impl.o0
//import com.applovin.impl.sdk.utils.ImageViewUtils
import com.google.android.gms.common.util.CollectionUtils
import com.google.android.gms.common.util.JsonUtils
import org.json.JSONException
import org.json.JSONObject


object TapmindsSdkUtils {

    private val handler = Handler(Looper.getMainLooper())

    @JvmStatic
    @Dimension(unit = Dimension.PX)
    fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Int {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), metrics).toInt()
    }

    @JvmStatic
    @Dimension(unit = Dimension.DP)
    fun pxToDp(context: Context, @Dimension(unit = Dimension.PX) px: Int): Int {
        return kotlin.math.ceil(px / context.resources.displayMetrics.density.toDouble()).toInt()
    }

//    @JvmStatic
//    fun isTablet(context: Context): Boolean {
//        val point: Point = o0.b(context) // assuming your o0.b() returns screen size
//        return minOf(point.x, point.y) >= dpToPx(context, 600)
//    }

//    @JvmStatic
//    fun isEmulator(): Boolean {
//        return startsWith(Build.DEVICE, "goldfish,vbox") ||
//                startsWith(Build.HARDWARE, "ranchu,generic,vbox") ||
//                startsWith(Build.MANUFACTURER, "Genymotion") ||
//                startsWith(Build.MODEL, "Android SDK built for x86")
//    }

//    private fun startsWith(str: String?, prefixes: String): Boolean {
//        return StringUtils.startsWithAtLeastOnePrefix(str, CollectionUtils.explode(prefixes))
//    }

//    @JvmStatic
//    fun getOrientation(context: Context?): Int {
//        val res: Resources? = context?.resources
//        val conf: Configuration? = res?.configuration
//        return conf?.orientation ?: 0
//    }

    @JvmStatic
    fun isValidString(str: String?): Boolean = !TextUtils.isEmpty(str)

    @JvmStatic
    fun runOnUiThread(runnable: Runnable) {
        runOnUiThread(false, runnable)
    }

    @JvmStatic
    fun runOnUiThread(forcePost: Boolean, runnable: Runnable) {
        if (!forcePost && h()) { // assuming o7.h() = isMainThread
            runnable.run()
        } else {
            handler.post(runnable)
        }
    }

    fun h(): Boolean = Looper.myLooper() == Looper.getMainLooper()


    @JvmStatic
    fun runOnUiThreadDelayed(runnable: Runnable, delayMs: Long) {
        runOnUiThreadDelayed(runnable, delayMs, handler)
    }

    @JvmStatic
    fun runOnUiThreadDelayed(runnable: Runnable, delayMs: Long, handler: Handler) {
        if (delayMs > 0) {
            handler.postDelayed(runnable, delayMs)
        } else if (h()) {
            runnable.run()
        } else {
            handler.post(runnable)
        }
    }

//    @JvmStatic
//    @Throws(JSONException::class)
//    fun toMap(json: JSONObject): Map<String, String> {
//        return JsonUtils.toStringMap(json)
//    }

//    @JvmStatic
//    fun isSdkVersionGreaterThanOrEqualTo(version: String): Boolean {
//        return AppLovinSdk.VERSION_CODE >= o7.g(version)
//    }

//    @JvmStatic
//    fun isInclusiveVersion(value: String?, min: String?, max: String?): Boolean {
//        if (TextUtils.isEmpty(value)) return true
//
//        val v = o7.g(value)
//        if (min != null && v < o7.g(min)) return false
//        if (max != null && v > o7.g(max)) return false
//        return true
//    }

//    @JvmStatic
//    fun isFireOS(context: Context): Boolean {
//        return Build.MANUFACTURER.equals("amazon", ignoreCase = true) || isFireTv(context)
//    }

    @JvmStatic
    fun isFireTv(context: Context): Boolean {
        return context.packageManager.hasSystemFeature("amazon.hardware.fire_tv")
    }

//    @JvmStatic
//    fun isTv(context: Context): Boolean {
//        if (isFireTv(context)) return true
//        val pm: PackageManager = context.packageManager
//        return if (o0.e()) { // assume o0.e detects API level
//            pm.hasSystemFeature("android.software.leanback")
//        } else {
//            pm.hasSystemFeature("android.hardware.type.television")
//        }
//    }

//    @JvmStatic
//    fun setImageUrl(url: String, imageView: ImageView, sdk: AppLovinSdk) {
//        val uri = Uri.parse(url)
//        ImageViewUtils.setImageUri(imageView, uri, sdk.a()) // assuming sdk.a() returns some loader
//    }

    // --------------------------------------------------------
    // Nested Size Class Converted
    // --------------------------------------------------------

    data class Size(val width: Int, val height: Int) {
        companion object {
            val ZERO = Size(0, 0)
        }
        override fun toString(): String = "${width}x${height}"
    }

//    data class Size(val width: Int, val height: Int) {
//
//        companion object {
//            val ZERO = Size(0, 0)
//        }
//
//        fun getWidth(): Int = width
//        fun getHeight(): Int = height
//
//        override fun equals(other: Any?): Boolean {
//            if (this === other) return true
//            if (other !is Size) return false
//            return width == other.width && height == other.height
//        }
//
//        override fun hashCode(): Int {
//            return height xor (width shl 16 or (width ushr 16))
//        }
//
//        override fun toString(): String = "${width}x$height"
//    }
}