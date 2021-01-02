package com.kevin.ttshelper

import android.accessibilityservice.AccessibilityService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AlertDialog
import kotlin.math.log


/**
 * 语音朗读辅助
 * */
class SpeakAccessibilityService : AccessibilityService(){
    companion object{
        private val TAG = SpeakAccessibilityService::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()


    }
    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: ")
    }

    override fun onServiceConnected() {
        Log.d(TAG, "onServiceConnected: ")
        return super.onServiceConnected()

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED &&rootInActiveWindow.findAccessibilityNodeInfosByText("复制").isNotEmpty()){
            Log.d(TAG, "onAccessibilityEvent: ${event}")
            val rootNodeInfo = rootInActiveWindow
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                //val list = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/an3")
                SpeakService.listener.execute()
                val intent = Intent(this,TestActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
            }
            //获取聊天列表的 listview
            //val listview = rootNodeInfo  ("com.tencent.mm:id/bny")
        }
        Log.d(TAG, "onAccessibilityEvent: ${event}")
    }

}