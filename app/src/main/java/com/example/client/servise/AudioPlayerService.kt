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
import com.example.client.Files
import com.example.client.MainActivity
import com.example.client.R
import com.example.client.data.BookInfo
import java.util.Date.from


class AudioPlayerService : Service() {


    //////////////////////

    val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    inner class LocalBinder : Binder() {
        fun getService(): AudioPlayerService = this@AudioPlayerService
    }
     var mediaPlayer: MediaPlayer? = null
     var book: BookInfo? = null
     var listFiles: MutableList<Files> = mutableListOf()
     var targetPlay:Files= Files()
     var mainUrl:String =""
     var numberPlay:Int = 0
     var playnow = true

    override fun onCreate() {
        super.onCreate()

    }
    fun test():String{
        return "servis wirking"
    }
    fun setDataBookPlay(bookNow:BookInfo){
        book = bookNow
    }
    fun setListFilesFromDetallFragment( files: MutableList<Files>){
        listFiles=files
    }
    fun setTargetPlayFromDetallFragment(target:Files){
        targetPlay = target
    }
    fun setMainUrlFromDetalFragment(url:String){
        mainUrl=url
    }

    fun findNubberTrack():Int{
        for (i in 0 until listFiles.size){
            if(targetPlay.name==listFiles[i].name){
                return i
            }
        }
        return -1
    }
    fun initPlayMedia(){
        if(mediaPlayer!=null) {
            mediaPlayer?.stop()
            mediaPlayer=null
        }
        numberPlay = findNubberTrack()
        if (numberPlay!=-1) {
            mediaPlayer = MediaPlayer()

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            mediaPlayer?.setAudioAttributes(audioAttributes)
            mediaPlayer?.setOnCompletionListener {
                // Переключение на следующий трек после завершения воспроизведения
                playNext()
            }
        }
    }

    fun playNext(){
        numberPlay = numberPlay+1
        if (numberPlay<=listFiles.size){
            mediaPlayer?.reset()
            startAudio()
        }else
        {
            numberPlay = numberPlay-1
        }
    }
    fun playPrev(){
        numberPlay = numberPlay-1
        if (numberPlay<0){
            numberPlay = 0
            mediaPlayer?.reset()
            startAudio()
        }else
        {
            mediaPlayer?.reset()
            startAudio()
        }
    }
    fun startAudio(){

        mediaPlayer = MediaPlayer.create(
            this,
            Uri.parse(mainUrl+listFiles[numberPlay].name)
        )
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mediaPlayer?.setAudioAttributes(audioAttributes)
        mediaPlayer?.start()
        showNotification(listFiles[numberPlay].title.toString())
        playnow=true
    }
    fun pauseAudio(){
        mediaPlayer?.pause()
    }
    fun playAudio(){
        mediaPlayer?.start()
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action == ACTION_PLAY) {
            val audioFileUri = intent.getStringExtra("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            //val audioFileUri = intent.getStringExtra(EXTRA_AUDIO_FILE_URI)
           // val audioFileUri = mainUrl+targetPlay.name

            if(mediaPlayer==null) {
                //////////
             //   startAudioPlayback(audioFileUri)
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
        if (intent != null && intent.action == ACTION_STOP) {

            mediaPlayer?.pause()
            stopForegroundService()
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
        showNotification("")}
         catch (e:Exception){

         }



    }

    // Intent для кнопки Play


    fun showNotification(id: String){
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
        // Intent для кнопки стоп
        val stopIntent = Intent(applicationContext, AudioPlayerService::class.java)
            .setAction(ACTION_STOP)
        val stopPendingIntent = PendingIntent.getService(
            applicationContext,
            1,//0
            stopIntent,
            PendingIntent.FLAG_MUTABLE
        )

        var intent = Intent(applicationContext, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .putExtra("bookId", id)


        val pendingIntent =  PendingIntent.getActivity(applicationContext, 1, intent,
            PendingIntent.FLAG_MUTABLE)



        val notification = NotificationCompat.Builder(applicationContext, "AudioBooks")
            .setContentTitle("Аудикниги" )
            .setContentText("Проигрывается:  "+id)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_media_play, "Play", playPendingIntent)
            .addAction(android.R.drawable.ic_media_pause, "Пауза", pausePendingIntent)
            .addAction(android.R.drawable.ic_delete,"Стоп",stopPendingIntent)
            .build()

        Log.i(ContentValues.TAG, "создалось notification!")
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

        //stopForeground(true)

    }
    private fun stopForegroundService() {
        stopForeground(true)
        mediaPlayer?.stop()

        stopSelf() // Останавливаем сам сервис
    }

    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_STOP = "action_stop"
        const val EXTRA_AUDIO_FILE_URI = "extra_audio_file_uri"
        const val CHANNEL_ID = "audio_player_channel"
        const val NOTIFICATION_ID = 1
    }
}