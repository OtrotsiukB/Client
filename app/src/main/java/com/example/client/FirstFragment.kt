package com.example.client

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
import com.example.client.databinding.FragmentFirstBinding
import com.example.client.servise.AudioPlayerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var i_showMiniPlayer: iShowMiniPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
           /* val x = Ckienttest()
            CoroutineScope(Dispatchers.IO).launch {
                x.main()
            }*/
        }
        binding.bGotobooks.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_allBooksInGroup)
        }
        checkServis()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is iShowMiniPlayer) {
            i_showMiniPlayer = context
        } else {
            throw ClassCastException("$context must implement OnSomeActionListener")
        }
    }
    fun checkServis() {



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}