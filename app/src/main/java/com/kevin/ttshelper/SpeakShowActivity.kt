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

class SpeakShowActivity:AppCompatActivity(){
    var clipboardManager: ClipboardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speak_show)

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
                   SpeakUtil.speak(firstData.toString())
               }
           },500)

        }else{
            val data: ClipData? = clipboardManager?.primaryClip
            if(data!==null && data.itemCount>0){
                val item = data.getItemAt(0)
                val firstData = item.coerceToText(this)
                SpeakUtil.speak(firstData.toString())
            }
        }
    }
}