package com.example.client

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.browse.MediaBrowser
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
//import android.support.v4.media.session.MediaSessionCompat




import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkRequest.Builder.Companion.fromUri
import androidx.navigation.fragment.findNavController
import com.example.client.databinding.FragmentSecondBinding
import com.example.client.network.retrofit
import com.example.client.servise.AudioPlayerService
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaItem.fromUri
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var webView: WebView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaController: MediaControllerCompat

    private lateinit var audioPlayerService: AudioPlayerService
    private var isServiceBound = false


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as AudioPlayerService.LocalBinder
            audioPlayerService = binder.getService()
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }
    lateinit var mainUrl:String
    lateinit var listFiles: MutableList<Files>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val testId = "21_20231206"
        CoroutineScope(Dispatchers.IO).launch {
            var info = retrofit.RetrofitModule.iRetrofit.getinfo(testId)
            var info2 = info
             var x: MutableList<Files> = mutableListOf<Files>()
            var mainUrlTemp=info.d1+info.dir+"/"
            for (i in info.files)
            {
                try {


                    if (i.name!!.contains(".mp3")) {
                        x.add(i)
                    }
                }catch (e:Exception){}
            }
            try {
                mainUrl = mainUrlTemp
                listFiles = x
            }catch (e:Exception){
                println("problem")
            }
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.bPlay.setOnClickListener {
            createPlay()
            updateSeekbar()
            mediaPlayer.start()

        }
        binding.bPlayAndPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                binding.bPlayAndPause.text = "Play"
              //  mediaController.transportControls.pause()
            } else {
                mediaPlayer.start()
                binding.bPlayAndPause.text = "Pause"
               // mediaController.transportControls.play()
            }
        }

        binding.bScrolingnext10sec.setOnClickListener {
            //mediaController.transportControls.seekTo(mediaController.playbackState.position + 10000)
            val newPosition = mediaPlayer.currentPosition + 10000
            mediaPlayer.seekTo(newPosition)

        }
        binding.bScrolingbefore10sec.setOnClickListener {
            //mediaController.transportControls.seekTo(mediaController.playbackState.position - 10000)
            val newPositionBack = mediaPlayer.currentPosition - 10000
            mediaPlayer.seekTo(newPositionBack)
        }
        binding.bScrolingNext.setOnClickListener {
          //  mediaController.transportControls.skipToNext()
            newPlay("https://"+mainUrl+listFiles[1].name)
            mediaPlayer.start()

        }
        binding.bScrolingBefore.setOnClickListener {
            newPlay("https://"+mainUrl+listFiles[0].name)
            mediaPlayer.start()
          //  mediaController.transportControls.skipToPrevious()
        }

        // Установите слушатель окончания воспроизведения для обновления seekBar
    /*    mediaPlayer.setOnCompletionListener {
            binding.seekBar.progress = 0
        }*/

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Обновите позицию воспроизведения при изменении положения ползунка
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Пользователь начал перемещение ползунка
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Пользователь завершил перемещение ползунка
                seekBar?.let {
                    // Получаем позицию, куда переместил пользователь
                    val newPosition = it.progress

                    // Перемотка аудио к новой позиции
                    mediaPlayer.seekTo(newPosition)
                }

            }
        })

        /////////
        binding.bStartservise.setOnClickListener {
            // Запускаем сервис
         //   val serviceIntent = Intent(requireContext(), AudioPlayerService::class.java)
          //  getActivity()?.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

            val intent = Intent(requireContext(),AudioPlayerService::class.java)
            getActivity()?.bindService(intent,serviceConnection,BIND_AUTO_CREATE)
            binding.tvInfo.text="servis start"

        }
        binding.bPlayFromServis.setOnClickListener {
           // startAudioPlayback("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            binding.tvInfo.text = audioPlayerService.test()
           // audioPlayerService.startAudioPlayback("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
           // startAudioPlayback("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
            checkPermision("https://ia800500.us.archive.org/24/items/21_20231206/01.mp3")
        }

    }
    fun checkPermision(audioFileUri: String){

        val permission = Manifest.permission.FOREGROUND_SERVICE
        val requestCode = 123 // Любое уникальное целочисленное значение

        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            // Разрешение уже предоставлено, выполняйте свой код
        startAudioPlayback(audioFileUri)
        } else {
            // Запросите разрешение у пользователя
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            123 -> {
                // Проверка, предоставлено ли разрешение
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Разрешение предоставлено, теперь вы можете запустить службу в фоновом режиме
                } else {
                    // Разрешение отклонено, возможно, стоит предоставить дополнительную информацию пользователю
                }
            }
            // Другие обработчики requestCode, если они есть
        }
    }

    fun updateSeekbar(){
        mediaPlayer.setOnPreparedListener {
            binding.seekBar.progress = mediaPlayer.currentPosition
            binding.seekBar.max = mediaPlayer.duration
        }
    }

    fun newPlay(url: String) {
        var temp = url
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(
            requireContext(),
            Uri.parse(temp)
        ) // Замените на свой аудиофайл в папке res/raw

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mediaPlayer.setAudioAttributes(audioAttributes)

        updateSeekbar()
    }
    fun createPlay(){
        var temp = "https://"+mainUrl+listFiles[0].name

        mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(temp)) // Замените на свой аудиофайл в папке res/raw

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mediaPlayer.setAudioAttributes(audioAttributes)

       // mediaController = MediaControllerCompat.fromMediaSession(requireContext(), mediaSession)

     /*   mediaSession = MediaSessionCompat(requireContext(), "YourMediaSession")
        mediaSession.isActive = true

        //val mediaController = MediaControllerCompat(requireContext(), mediaSession.controller)
        mediaController = MediaControllerCompat(requireContext(), mediaSession)*/



    }
    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
        mediaSession.release()
        _binding = null

        if (isServiceBound) {
            getActivity()?.unbindService(serviceConnection)
            isServiceBound = false
        }

    }

    // Метод для запуска воспроизведения
    private fun startAudioPlayback(audioFileUri: String) {
        val intent = Intent(requireContext(), AudioPlayerService::class.java).apply {
            action = AudioPlayerService.ACTION_PLAY
            putExtra(AudioPlayerService.EXTRA_AUDIO_FILE_URI, audioFileUri)
        }
        getActivity()?.startService(intent)
    }

    // Метод для остановки воспроизведения
    private fun stopAudioPlayback() {
        if (isServiceBound) {
            val intent = Intent(requireContext(), AudioPlayerService::class.java)
            getActivity()?.stopService(intent)
        }
    }
}