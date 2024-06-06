package com.example.client.ui

import android.app.ActivityManager
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.client.*
import com.example.client.data.BookInfo
import com.example.client.databinding.FragmentBookDetalBinding
import com.example.client.databinding.FragmentFirstBinding
import com.example.client.network.retrofit
import com.example.client.servise.AudioPlayerService
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookDetalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookDetalFragment : Fragment(),playlistAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentBookDetalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private  var audioPlayerService: AudioPlayerService? = null
    private var isServiceBound = false

    lateinit var book:BookInfo
    lateinit var mainUrl:String
    lateinit var listFiles: MutableList<Files>


    private val bookAdapter = playlistAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           /* val args: BookDetalFragmentArgs by navArgs()
            book = args.book*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookDetalBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (isServiceBound) {
            getActivity()?.unbindService(serviceConnection)
            isServiceBound = false
        }
    }
    fun initComponent(){
        binding.tvTitle.text = book.name
        Glide.with(requireContext())
            //.load(R.drawable.movie6)
            .load(book.urlImage)
            .into(binding.icAfisha)
        binding.tvYear.text=book.year
        binding.tvLong.text = book.duration

        val number = book.numberCycle

       /* val fractionalPart = number.rem(1.0)
        if (fractionalPart>0){*/
            binding.tvCycle.text = book.cycle + " ("+book.numberCycle+")"
       // }else{
         //   binding.tvCycle.text = book.cycle + " ("+book.numberCycle.toInt()+")"

       // }
        var allgenry =""
        for (item in book.genre){
            allgenry = allgenry +""+item+" "
        }
        binding.tvGenre.text = allgenry
        binding.tvInfobook.text = book.infoOfBook
    }
    fun initPlayList(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var id = book.idInArhive
                var x: MutableList<Files> = mutableListOf<Files>()
                var files = getFiles()
                for (i in files){
                        if (i.name!!.contains(".mp3")) {
                            x.add(i)
                        }
                }
                listFiles = x
                initRVAdapter()

            }catch (e: Exception) {

            }
        }
    }
    suspend fun getFiles():MutableList<Files>{
      //  withContext(Dispatchers.IO) {

            try {
                var info = retrofit.RetrofitModule.iRetrofit.getinfo(book.idInArhive)
                mainUrl = "https://" + info.d1 + info.dir + "/"
                val temp = info.files.toMutableList()
                return temp

            }catch (e:Exception){
                try {
                    var info = retrofit.RetrofitModule.iRetrofit.getinfoTest(book.idInArhive)
                    mainUrl = "https://" + info.d1 + info.dir + "/"
                    var temp = ConvetorFiles2ToFiles.convert(info.files)
                    return temp
                }catch (e:Exception)
                {
                    var info = retrofit.RetrofitModule.iRetrofit.getinfoTest3(book.idInArhive)
                    mainUrl = "https://" + info.d1 + info.dir + "/"
                    var temp = ConvetorFiles2ToFiles.convert(info.files)
                    return temp
                }

            }


    return mutableListOf()
    }

    suspend fun initRVAdapter(){
        withContext(Dispatchers.Main) {
            try {
                bookAdapter.setData(listFiles)
                binding.rvPlaylist.adapter = bookAdapter
            }catch (e:Exception){
                val t = e
                val d = t
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: BookDetalFragmentArgs by navArgs()
        book = args.book
        initComponent()
        initPlayList()

        if (i_showMiniPlayer?.nowPlaing()==true){
            i_showMiniPlayer?.miniPlayerOnVisible()
            val miniPlayer = i_showMiniPlayer?.getIMiniPlayer()
            miniPlayer?.start()
        }
    }

    fun startServis(){
      //  if (!isServiceRunning(AudioPlayerService::class.java, requireContext())) {
            // Запустить службу, так как она не запущена
            try {
                val intent = Intent(requireContext(), AudioPlayerService::class.java)
                getActivity()?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
               /////
            }catch (e:Exception){
                val x =e
                val y = x
                val d = y
            }
       /* }else
        {


        }*/



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


    override fun onDestroy() {
        super.onDestroy()
        isServiceBound=false
        audioPlayerService=null

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookDetalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookDetalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(data: Files) {
        val miniPlayer = i_showMiniPlayer?.getIMiniPlayer()
       // miniPlayer?.stop()
        if (!isServiceBound) {
            startServis()
        }

       //
        CoroutineScope(Dispatchers.IO).launch {
            var check = false
            while (!check) {
                if (isServiceBound == true) {
                    audioPlayerService?.setListFilesFromDetallFragment(listFiles)
                    audioPlayerService?.setMainUrlFromDetalFragment(mainUrl)
                    audioPlayerService?.setTargetPlayFromDetallFragment(data)
                    audioPlayerService?.setDataBookPlay(book)
                    check=true
                    CoroutineScope(Dispatchers.Main).launch {
                        findNavController().navigate(R.id.action_bookDetalFragment2_to_playerFragment)
                    }
                }
            }
        }
    }
}