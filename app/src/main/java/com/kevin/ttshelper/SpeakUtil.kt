package com.kevin.ttshelper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*

object SpeakUtil {
    private var speakEngine:TextToSpeech? = null
    private var clipboardManager:ClipboardManager? = null
    /**
     * 文字转语音
     * */
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
        speakEngine?.speak(word, TextToSpeech.QUEUE_FLUSH,null)
    }
    /**
     * 获取剪切板第一条数据
     * */
    fun getFirstClipData():String?{
        if (clipboardManager == null){
            clipboardManager = App.instance.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }

        val data: ClipData? = clipboardManager?.primaryClip
        if(data!==null && data.itemCount>0){
            val item = data.getItemAt(0)
            val firstData = item.coerceToText(App.instance)
            return firstData.toString()
        }
        return null
    }
    /**
     * 朗读剪切板第一条数据
     * */
    fun speakFirstClipData(){
        val word = getFirstClipData()
        if (word != null) {
            speak(word)
        }
    }
}