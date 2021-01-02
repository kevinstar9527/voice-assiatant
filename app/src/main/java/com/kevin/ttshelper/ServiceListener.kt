package com.kevin.ttshelper

import android.content.Context
import android.content.Intent

class ServiceListener {
    public lateinit var context:Context
    public lateinit var service: SpeakService
    fun execute(){
       // service.showSystemDialog()
//        val intent = Intent(context,MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        context.startActivity(intent)

    }
}