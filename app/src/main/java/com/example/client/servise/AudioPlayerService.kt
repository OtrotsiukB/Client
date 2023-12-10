package com.example.client.servise

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.app.Notification
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.client.MainActivity
import com.example.client.R


class AudioPlayerService : Service() {


    //////////////////////
    private lateinit var mediaPlayer: MediaPlayer
    val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    inner class LocalBinder : Binder() {
        fun getService(): AudioPlayerService = this@AudioPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action == ACTION_PLAY) {
            val audioFileUri = intent.getStringExtra("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            startAudioPlayback(audioFileUri)
        }

        return START_NOT_STICKY
    }
    private fun startAudioPlayback(audioFileUri: String?) {
        // Ваш код для настройки MediaPlayer и воспроизведения аудио
        mediaPlayer.setDataSource(audioFileUri)
        mediaPlayer.prepare()
        mediaPlayer.start()

        // Создание уведомления для Foreground Service
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "Audiobook")
            .setContentTitle("Audio Player")
            .setContentText("Now Playing...")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        stopForeground(true)
    }

    companion object {
        const val ACTION_PLAY = "action_play"
        const val EXTRA_AUDIO_FILE_URI = "extra_audio_file_uri"
        const val CHANNEL_ID = "audio_player_channel"
        const val NOTIFICATION_ID = 1
    }
}