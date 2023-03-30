package com.yuu.android.component.voice.tts

import android.content.Context
import com.yuu.android.component.voice.api.EngineApi
import com.yuu.android.component.voice.utils.ApkUtils
import com.yuu.android.component.voice.utils.ApkUtils.installApk
import com.yuu.android.component.voice.utils.FileUtils
import java.io.File
import kotlin.concurrent.thread

object EngineService : EngineApi {

    const val VOICE_SERVICE_PACKAGE_NAME = "com.iflytek.speechcloud"
    const val APK_NAME = "kdxfyq3.apk"
    private var onVoiceInstallListener:( ()->Unit)? = null

    fun getOnVoiceInstalledListener() = onVoiceInstallListener

    override fun checkVoiceService(context: Context, onVoiceServiceListener:( ()->Unit)?) {
        onVoiceInstallListener = onVoiceServiceListener
        if (!isVoiceEngineServiceInstalled(context)) {
            installEngine(context)
        } else {
            onVoiceInstallListener?.invoke()
        }
    }

    override fun installEngine(context: Context) {
        Thread{
            val file = File(FileUtils.getVoiceFileDir(context).path.toString() + "/" + APK_NAME)
            if (!file.exists()) {
                FileUtils.copyAssets(
                    context,
                    APK_NAME,
                    FileUtils.getVoiceFileDir(context).path.toString() + "/" + APK_NAME
                )
            }
            installApk(context, FileUtils.getVoiceFileDir(context).path.toString() + "/" + APK_NAME)
        }.start()
    }



    private fun isVoiceEngineServiceInstalled(context: Context) =
        ApkUtils.isAppInstalled(context, VOICE_SERVICE_PACKAGE_NAME)

}