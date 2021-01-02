package com.kevin.ttshelper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TestActivity:AppCompatActivity(){
    var clipboardManager: ClipboardManager? = null
    var speakEngine:TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        if(speakEngine==null){
            //初始化播报与剪切板引擎
            speakEngine = TextToSpeech(this) {
                if(it == TextToSpeech.SUCCESS){
                    val result = speakEngine?.setLanguage(Locale.CHINA)
                    if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result!= TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(this, "TTS暂不支持这种语音的朗读", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (clipboardManager ==null){
            clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
           object :Handler(Looper.getMainLooper()){}.postDelayed({
               val data: ClipData? = clipboardManager?.primaryClip
               if(data!==null && data.itemCount>0){
                   val item = data.getItemAt(0)
                   val firstData = item.coerceToText(this)
                   speakEngine?.speak("$firstData", TextToSpeech.QUEUE_FLUSH,null)
               }
           },1000)

        }else{
            val data: ClipData? = clipboardManager?.primaryClip
            if(data!==null && data.itemCount>0){
                val item = data.getItemAt(0)
                val firstData = item.coerceToText(this)
                speakEngine?.speak("$firstData", TextToSpeech.QUEUE_FLUSH,null)
            }
        }
    }
}