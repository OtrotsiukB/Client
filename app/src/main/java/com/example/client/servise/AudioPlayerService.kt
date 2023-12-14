package com.example.client.servise

import android.app.*
import android.content.ContentValues
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.client.MainActivity
import com.example.client.R
import java.util.Date.from


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

    }
    fun test():String{
        return "servis wirking"
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action == ACTION_PLAY) {
            //val audioFileUri = intent.getStringExtra("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            val audioFileUri = intent.getStringExtra(EXTRA_AUDIO_FILE_URI)

            if(mediaPlayer==null) {
                startAudioPlayback(audioFileUri)
            }else
            {
                mediaPlayer?.start()
            }

        }
        if (intent != null && intent.action == ACTION_PAUSE) {
            //val audioFileUri = intent.getStringExtra("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            val audioFileUri = intent.getStringExtra(EXTRA_AUDIO_FILE_URI)

            mediaPlayer?.pause()
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
         try{
        showNotification("","","")}
         catch (e:Exception){
             val x =e
             val y = x
             val d = y
         }



    }

    // Intent для кнопки Play


    fun showNotification(id: String,name:String,rating:String){
        val playIntent = Intent(applicationContext, AudioPlayerService::class.java)
            .setAction(ACTION_PLAY)
        val playPendingIntent = PendingIntent.getService(
            applicationContext,
            1,//0
            playIntent,
            PendingIntent.FLAG_MUTABLE
        )

        // Intent для кнопки Pause
        val pauseIntent = Intent(applicationContext, AudioPlayerService::class.java)
            .setAction(ACTION_PAUSE)
        val pausePendingIntent = PendingIntent.getService(
            applicationContext,
            1,//0
            pauseIntent,
            PendingIntent.FLAG_MUTABLE
        )

        var intent = Intent(applicationContext, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .putExtra("bookId", id)


        val pendingIntent =  PendingIntent.getActivity(applicationContext, 1, intent,
            PendingIntent.FLAG_MUTABLE)



        val notification = NotificationCompat.Builder(applicationContext, "AudioBooks")
            .setContentTitle("Аудикниги")
            .setContentText("Now Playing "+id)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_media_play, "Play", playPendingIntent)
            .addAction(android.R.drawable.ic_media_pause, "Pause", pausePendingIntent)

            .build()




           //




        //.setSmallIcon(R.drawable.ic_message)
        // .setWhen(message.timestamp)
        // .setLargeIcon(bitmapIcon)
        // .build()
        Log.i(ContentValues.TAG, "создалось notification!")
        try {
            startForeground(NOTIFICATION_ID, notification)
        }catch (e:Exception){
            val x =e
            val y = x
            val d = y
        }

       /* try {


            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(2, notification.build())
            }
        }  catch (e: Exception) {
            Log.i(ContentValues.TAG, "error"+e.toString())
        }*/
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()

        stopForeground(true)

    }

    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val EXTRA_AUDIO_FILE_URI = "extra_audio_file_uri"
        const val CHANNEL_ID = "audio_player_channel"
        const val NOTIFICATION_ID = 1
    }
}