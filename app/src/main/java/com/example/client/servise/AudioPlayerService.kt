package com.example.client.servise

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.media.AudioAttributes
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.client.MainActivity
import com.example.client.R


class AudioPlayerService : Service() {


    //////////////////////
    private  var mediaPlayer: MediaPlayer? = null
    val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    inner class LocalBinder : Binder() {
        fun getService(): AudioPlayerService = this@AudioPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Audio Player Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
    fun test():String{
        return "servis wirking"
    }
   /* fun newPlay(url: String) {
        var temp = url
        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse(temp)
        ) // Замените на свой аудиофайл в папке res/raw

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mediaPlayer?.setAudioAttributes(audioAttributes)
        mediaPlayer?.start()
        //updateSeekbar()
    }*/

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action == ACTION_PLAY) {
            //val audioFileUri = intent.getStringExtra("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            val audioFileUri = intent.getStringExtra(EXTRA_AUDIO_FILE_URI)

            startAudioPlayback(audioFileUri)
        }

        return START_NOT_STICKY
    }
     fun startAudioPlayback(audioFileUri: String?) {
        // Ваш код для настройки MediaPlayer и воспроизведения аудио
        //mediaPlayer.setDataSource(audioFileUri)
        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse(audioFileUri)
        )
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mediaPlayer?.setAudioAttributes(audioAttributes)
        mediaPlayer?.start()

        // Создание уведомления для Foreground Service
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)

         var intent = Intent(applicationContext, MainActivity::class.java)
             .setAction(Intent.ACTION_VIEW)

         val pendingIntent = PendingIntent.getActivity(
             applicationContext,
             0,
             intent,
             PendingIntent.FLAG_IMMUTABLE
         )

       // val pendingIntent = PendingIntent.getActivity(applicationContext, 1, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, "Audiobook")
            .setContentTitle("Audio Player")
            .setContentText("Now Playing...")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        try {
    startForeground(NOTIFICATION_ID, notification)
        }catch (e:Exception){
            val x =e
            val y = x
            val d = y
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        stopForeground(true)
    }

    companion object {
        const val ACTION_PLAY = "action_play"
        const val EXTRA_AUDIO_FILE_URI = "extra_audio_file_uri"
        const val CHANNEL_ID = "audio_player_channel"
        const val NOTIFICATION_ID = 1
    }
}