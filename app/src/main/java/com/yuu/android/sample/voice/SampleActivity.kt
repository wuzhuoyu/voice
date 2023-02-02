package com.yuu.android.sample.voice

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yuu.android.component.voice.tts.EngineService
import java.util.*

class SampleActivity : AppCompatActivity(), OnInitListener {
    private var tts:TextToSpeech?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EngineService().checkVoiceService(this){
            tts = TextToSpeech(this,this,"com.iflytek.speechcloud")
        }
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            tts!!.speak("支付宝到帐101元", TextToSpeech.QUEUE_ADD, null);
        }
    }

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            if (tts!!.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_AVAILABLE) {
                tts!!.language = Locale.CHINESE
                tts!!.setSpeechRate(0.9f)
            }else{
                Log.e("TextToSpeech", "current area not support chinese")
            }
        } else {
            Log.e("TextToSpeech", "init fail")
        }
    }
}