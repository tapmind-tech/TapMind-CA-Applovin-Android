package com.tapmimd.ads.mediation.adapter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

object AdapterActivityHolder : Application.ActivityLifecycleCallbacks {

    @Volatile
    private var currentActivityRef: WeakReference<Activity>? = null

    fun register(context: Context) {
        val app = (context.applicationContext as? Application) ?: return
        app.registerActivityLifecycleCallbacks(this)
    }

    fun getActivity(): Activity? = currentActivityRef?.get()

    override fun onActivityResumed(activity: Activity) {
        currentActivityRef = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        val cur = currentActivityRef?.get()
        if (cur === activity) {
            currentActivityRef?.clear()
            currentActivityRef = null
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}
