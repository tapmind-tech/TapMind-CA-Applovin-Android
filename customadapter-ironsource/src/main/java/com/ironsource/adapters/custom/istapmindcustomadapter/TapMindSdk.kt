package com.ironsource.adapters.custom.istapmindcustomadapter

import android.content.Context
import android.util.Log
import com.unity3d.mediation.LevelPlay
import com.unity3d.mediation.LevelPlayConfiguration
import com.unity3d.mediation.LevelPlayInitError
import com.unity3d.mediation.LevelPlayInitListener
import com.unity3d.mediation.LevelPlayInitRequest

object TapMindSdk {

    fun initIronSource(
        context: Context,
        apiKey: String,
        userId: String,
        onCallBack: (Boolean) -> Unit
    ) {
        val initRequest = LevelPlayInitRequest.Builder(apiKey)
            .withUserId(userId)
            .build()
        LevelPlay.init(context, initRequest, object : LevelPlayInitListener {
            override fun onInitFailed(error: LevelPlayInitError) {
                Log.e("App@@@", "onInitFailed: IronSource")
                onCallBack(false)
            }

            override fun onInitSuccess(configuration: LevelPlayConfiguration) {
                Log.e("App@@@", "onInitSuccess: IronSource" )
                onCallBack(true)
            }
        })
    }
}