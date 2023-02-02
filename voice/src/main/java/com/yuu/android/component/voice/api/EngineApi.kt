package com.yuu.android.component.voice.api

import android.content.Context

interface EngineApi {

    fun checkVoiceService(context: Context,onVoiceServiceListener:(() ->Unit)?)

    fun installEngine(context: Context)

}