package com.example.client.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.client.R
import com.example.client.databinding.FragmentFirstBinding
import com.example.client.databinding.FragmentMiniPlayerBinding
import com.example.client.iMiniPlayer
import com.example.client.iShowMiniPlayer
import com.example.client.servise.AudioPlayerService
import kotlinx.coroutines.*
import java.lang.Thread.sleep

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MiniPlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MiniPlayerFragment : Fragment(),iMiniPlayer {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

     var _binding: FragmentMiniPlayerBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var audioPlayerService: AudioPlayerService? = null
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
        _binding = FragmentMiniPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private var i_showMiniPlayer: iShowMiniPlayer? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is iShowMiniPlayer) {
            i_showMiniPlayer = context
            i_showMiniPlayer?.setIMiniPlayer(this)
        } else {
            throw ClassCastException("$context must implement OnSomeActionListener")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CoroutineScope(Dispatchers.IO).launch {
            while (true){
                //var isVisible =
                val isVisible:Int? = withContext(Dispatchers.Main) {
                    var isVisibletemp = i_showMiniPlayer?.miniPlayerStatusVisible()
                    isVisibletemp
                }
                if (isVisible==View.VISIBLE){
                    CoroutineScope(Dispatchers.Main).launch {
                        init()
                    }
                }
                sleep(1000)
            }
    }
        binding.icPlayandpauseMiniplayer.setOnClickListener {
            if (!audioPlayerService?.playnow!!){
                binding.icPlayandpauseMiniplayer.setImageResource(R.drawable.ic_media_pause)
                audioPlayerService?.playAudio()
                audioPlayerService?.playnow=true
                init()
            }else{
                binding.icPlayandpauseMiniplayer.setImageResource(R.drawable.ic_media_play)
                audioPlayerService?.pauseAudio()
                audioPlayerService?.playnow=false
            }
        }

    }

    override fun onStop() {
        super.onStop()
        stop()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MiniPlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MiniPlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun init(){
        binding.tvTrackMiniplayer.text = audioPlayerService?.book?.name
        binding.tvCurentduration.text = audioPlayerService?.mediaPlayer?.currentPosition?.let {
            formatDuration(
                it
            )
        }
    }
    fun formatDuration(durationInMillis: Int): String {
        val hours = durationInMillis / (1000 * 60 * 60)
        val minutes = (durationInMillis % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (durationInMillis % (1000 * 60)) / 1000

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    override fun start() {
            val intent = Intent(requireContext(), AudioPlayerService::class.java)
            getActivity()?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            CoroutineScope(Dispatchers.IO).launch {
                var check = true
                while (check) {
                    if (isServiceBound == true) {
                        check = false

                        uploadCorutine.launch {
                           // while (true) {
                                if (isServiceBound == true) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        init()
                                    }
                                    sleep(1000)
                                } else {
                                    sleep(1000)
                                }
                          // }
                        }

                    }
                }
            }

    }
    private var uploadCorutine=CoroutineScope(Dispatchers.IO)
    override fun stop() {
        isServiceBound=false
        audioPlayerService=null
        uploadCorutine.cancel()
    }
}