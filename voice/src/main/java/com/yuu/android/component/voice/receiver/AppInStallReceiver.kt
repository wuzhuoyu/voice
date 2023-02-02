package com.yuu.android.component.voice.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.yuu.android.component.voice.tts.EngineService

class AppInStallReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        Log.d("AppInStallReceiver", p1.data?.schemeSpecificPart ?: "")
        if (p1.action == Intent.ACTION_PACKAGE_ADDED) {
            val packageName: String = p1.data?.schemeSpecificPart ?: ""
            if (EngineService.VOICE_SERVICE_PACKAGE_NAME == packageName) {
                //语音引擎安装成功, 重启APP
                try {
                    EngineService.getOnVoiceInstalledListener()?.invoke()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}