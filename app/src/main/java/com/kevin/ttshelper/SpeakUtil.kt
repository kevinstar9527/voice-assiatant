package com.kevin.ttshelper

import android.content.ClipboardManager
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*

object SpeakUtil {
    private var speakEngine:TextToSpeech? = null
    fun speak(word:String){
        if(speakEngine==null){
            //初始化播报与剪切板引擎
            speakEngine = TextToSpeech(App.instance) {
                if(it == TextToSpeech.SUCCESS){
                    val result = speakEngine?.setLanguage(Locale.CHINA)
                    if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result!= TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(App.instance, "TTS暂不支持这种语音的朗读", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        speakEngine?.speak("$word", TextToSpeech.QUEUE_FLUSH,null)
    }
}