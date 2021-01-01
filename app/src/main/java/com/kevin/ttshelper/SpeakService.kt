package com.kevin.ttshelper

import android.app.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.util.*


/**
 * 监听剪切板并读取第条1信息进行语音播报服务
 */
class SpeakService : Service() {
    var clipboardManager: ClipboardManager? = null
    var speakEngine:TextToSpeech? = null
    override fun onCreate() {
        super.onCreate()
        val notification = createForegroundNotification()
        startForeground(1,notification)
        Log.d(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if(speakEngine==null){
            //初始化播报与剪切板引擎
            speakEngine = TextToSpeech(this) {
                if(it == TextToSpeech.SUCCESS){
                    val result = speakEngine?.setLanguage(Locale.CHINA)
                    if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result!=TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(this, "TTS暂不支持这种语音的朗读", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        if (clipboardManager ==null){
            clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager?.addPrimaryClipChangedListener {
                if(clipboardManager!!.hasPrimaryClip()){
                    val data: ClipData? = clipboardManager?.primaryClip
                    if(data!==null && data.itemCount>0){
                        val item = data.getItemAt(0)
                        val firstData = item.coerceToText(this)
                        speakEngine?.speak("$firstData", TextToSpeech.QUEUE_FLUSH,null)
                    }
                }
            }
        }

        return START_STICKY
    }

    companion object {
        private val TAG = SpeakService::class.java.simpleName
    }

    /**
     * 创建服务通知
     */
    private fun createForegroundNotification(): Notification? {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 唯一的通知通道的id.
        val notificationChannelId = "notification_channel_id_01"

        // Android8.0以上的系统，新建消息通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //用户可见的通道名称
            val channelName = "Foreground Service Notification"
            //通道的重要程度
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(notificationChannelId, channelName, importance)
            notificationChannel.description = "Channel description"
            //LED灯
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            //震动
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        val builder = NotificationCompat.Builder(this, notificationChannelId)
        //通知小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //通知标题
        builder.setContentTitle("ContentTitle")
        //通知内容
        builder.setContentText("ContentText")
        //设定通知显示的时间
        builder.setWhen(System.currentTimeMillis())
        //设定启动的内容
        val activityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        //创建通知并返回
        return builder.build()
    }
}