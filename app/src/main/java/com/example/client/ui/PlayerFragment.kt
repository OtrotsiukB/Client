package com.example.client.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.client.MainActivity
import com.example.client.R
import com.example.client.databinding.FragmentFirstBinding
import com.example.client.databinding.FragmentPlayerBinding
import com.example.client.iShowMiniPlayer
import com.example.client.servise.AudioPlayerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPlayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var audioPlayerService: AudioPlayerService
    private var isServiceBound = false
   // private  var mediaPlayer: MediaPlayer? = null

    var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(requireContext(), AudioPlayerService::class.java)
        getActivity()?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        try {
           i_showMiniPlayer?.nowPlaing(true)
            i_showMiniPlayer?.miniPlayerOffVisible()
            val miniPlayer = i_showMiniPlayer?.getIMiniPlayer()
            //miniPlayer?.stop()
        }catch (e:Exception){

        }
        CoroutineScope(Dispatchers.IO).launch {

            while (!check) {
                if (isServiceBound == true) {
                    check=true
                    audioPlayerService.initPlayMedia()
                    audioPlayerService.startAudio()


                    CoroutineScope(Dispatchers.Main).launch {
                       try {
                           initListener()
                           initTVandIC()
                           CoroutineScope(Dispatchers.IO).launch {
                           while (true){
                               CoroutineScope(Dispatchers.Main).launch {
                                   try {
                                       binding.tvTimenow.text =
                                           audioPlayerService.mediaPlayer?.currentPosition?.let {
                                               formatDuration(
                                                   it
                                               )
                                           }
                                       updateSeekbar()
                                   } catch (e: Exception) {
                                   }
                               }
                               sleep(1000)
                           }

                           }
                           }catch (e:Exception){}
                    }

                }
            }
        }

    }
    fun initTVandIC(){
        binding.tvAutorPlayer.text = audioPlayerService.book?.autor

        binding.tvNamebookplay.text = audioPlayerService.book?.name
        if(audioPlayerService.listFiles[audioPlayerService.numberPlay.toInt()].title == "---"){
            binding.tvTitleplayer.text =
                audioPlayerService.listFiles[audioPlayerService.numberPlay.toInt()].title +" ("+
                    audioPlayerService.listFiles[audioPlayerService.numberPlay.toInt()].name+")"
        }else {
            binding.tvTitleplayer.text =
                audioPlayerService.listFiles[audioPlayerService.numberPlay.toInt()].title
        }
        Glide.with(requireContext())
            //.load(R.drawable.movie6)
            .load(audioPlayerService.book?.urlImage)

            .into(binding.icAfishaplayer)

        updateSeekbar()
        binding.tbTimeend.text = audioPlayerService.mediaPlayer?.duration?.let { formatDuration(it) }
    }
    fun updateSeekbar(){
        //audioPlayerService.mediaPlayer?.setOnPreparedListener {
            binding.seekBarPlayer.progress = audioPlayerService.mediaPlayer?.currentPosition!!
            binding.seekBarPlayer.max = audioPlayerService.mediaPlayer?.duration!!
      //  }
    }
    fun initListener()
    {
        binding.icPalyAndPause.setOnClickListener {
            if (!audioPlayerService.playnow){
                binding.icPalyAndPause.setImageResource(R.drawable.ic_media_pause)
                audioPlayerService.playAudio()
                audioPlayerService.playnow=true
            }else{
                binding.icPalyAndPause.setImageResource(R.drawable.ic_media_play)
                audioPlayerService.pauseAudio()
                audioPlayerService.playnow=false
            }
        }
        binding.seekBarPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Обновите позицию воспроизведения при изменении положения ползунка
                if (fromUser) {
                    audioPlayerService.mediaPlayer?.seekTo(progress)
                   // mediaPlayer.seekTo(progress)
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
                    audioPlayerService.mediaPlayer?.seekTo(newPosition)
                }

            }
        })

        binding.icNl.setOnClickListener {
            val newPosition = audioPlayerService.mediaPlayer?.currentPosition?.plus(10000)
            if (newPosition != null) {
                audioPlayerService.mediaPlayer?.seekTo(newPosition)
            }
        }
        binding.icPl.setOnClickListener {
            val newPosition = audioPlayerService.mediaPlayer?.currentPosition?.minus(10000)
            if (newPosition != null) {
                audioPlayerService.mediaPlayer?.seekTo(newPosition)
            }
        }
        binding.icNext.setOnClickListener {
             audioPlayerService.playNext()
            initTVandIC()

        }
        binding.icPrev.setOnClickListener {
            audioPlayerService.playPrev()
            initTVandIC()
        }
    }
    fun formatDuration(durationInMillis: Int): String {
        val hours = durationInMillis / (1000 * 60 * 60)
        val minutes = (durationInMillis % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (durationInMillis % (1000 * 60)) / 1000

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
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

    private var i_showMiniPlayer: iShowMiniPlayer? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is iShowMiniPlayer) {
            i_showMiniPlayer = context
        } else {
            throw ClassCastException("$context must implement OnSomeActionListener")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}