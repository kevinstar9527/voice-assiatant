package com.kevin.ttshelper

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import java.lang.Exception
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var btnSpeak:Button
    lateinit var btnClipboard:Button
    lateinit var speakEngine:TextToSpeech
    lateinit var btnSystem:Button
    lateinit var btnService:Button
    lateinit var btnCheck:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        speakEngine = TextToSpeech(this) {
//            if(it == TextToSpeech.SUCCESS){
//                val result = speakEngine.setLanguage(Locale.CHINA)
//                if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result!=TextToSpeech.LANG_AVAILABLE){
//                    Toast.makeText(this, "TTS暂不支持这种语音的朗读", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

        btnSpeak = findViewById(R.id.btn_speak)
        btnSpeak.setOnClickListener{
                speakEngine.speak("你好世界",TextToSpeech.QUEUE_FLUSH,null)
        }

        //读取剪切板并朗读
        btnClipboard = findViewById(R.id.btn_clipboard)
        btnClipboard.setOnClickListener{
                val clipboardManager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE)
                if(clipboardManager !== null){
                    val data:ClipData? = (clipboardManager as ClipboardManager).primaryClip
                    if(data!==null && data.itemCount>0){
                        val item = data.getItemAt(0)
                        val firstData = item.coerceToText(this)
                        speakEngine.speak("$firstData",TextToSpeech.QUEUE_FLUSH,null)
                    }

                }

        }

        btnSystem = findViewById(R.id.btn_system)
        btnSystem.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("测试悬浮")
            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setDimAmount(0f)
            if (Build.VERSION.SDK_INT>=26) {//8.0新特性
                dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            }else{
                dialog.window?.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }
            dialog.show()

        }

        btnService = findViewById(R.id.btn_service)
        btnService.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SpeakService.listener.context = this
                startForegroundService(Intent(this,SpeakService::class.java));
            } else {
                //启动引擎服务
                startService(Intent(this,SpeakService::class.java))
            }

        }

        try{
            val settingsIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            settingsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(settingsIntent)
        }catch (e:Exception){
            val actionSettings = Intent(Settings.ACTION_SETTINGS)
            actionSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(actionSettings)
        }

        btnCheck = findViewById(R.id.btn_check)
        btnCheck.setOnClickListener{
            var manager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            var services = manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            for (info in services){
                Log.d("无障碍检查:", info.id)
            }
        }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

}