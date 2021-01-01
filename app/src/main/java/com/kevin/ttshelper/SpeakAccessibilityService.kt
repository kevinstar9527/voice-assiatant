package com.kevin.ttshelper

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent


/**
 * 语音朗读辅助
 * */
class SpeakAccessibilityService : AccessibilityService(){
    companion object{
        private val TAG = SpeakAccessibilityService::class.java.simpleName
    }
    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: ")
    }

    override fun onServiceConnected() {
        Log.d(TAG, "onServiceConnected: ")
        return super.onServiceConnected()

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event?.eventType == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED){
            Log.d(TAG, "onAccessibilityEvent: ${event.text}")
        }
        Log.d(TAG, "onAccessibilityEvent: ${event}")
    }

}