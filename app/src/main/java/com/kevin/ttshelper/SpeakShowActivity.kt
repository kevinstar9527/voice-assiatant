package com.kevin.ttshelper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity


class SpeakShowActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speak_show)

        val window: Window = window
        window.setGravity(Gravity.CENTER)
        window.decorView.setPadding(0, 0, 0, 0)

        val screenWidth:Int = windowManager.defaultDisplay.width
        val lp = window.attributes
        lp.width = ((screenWidth * 0.9).toInt())
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 这里还可以设置lp.x，lp.y在x轴，y轴上的坐标，只是这个位置是基于Gravity的
        window.attributes = lp

    }

    override fun onResume() {
        super.onResume()
        object :Handler(Looper.getMainLooper()){}.postDelayed({
           SpeakUtil.speakFirstClipData()
        },500)
    }
}