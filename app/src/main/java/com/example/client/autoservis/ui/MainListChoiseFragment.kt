package com.example.client.autoservis.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.client.R
import com.example.client.autoservis.i_nterface.iMainActivity
import com.example.client.databinding.FragmentMainListChoiseBinding
import com.example.client.databinding.FragmentStartAuthorizationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainListChoiseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainListChoiseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var iMainActivity: iMainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is iMainActivity) {
            iMainActivity = context
        }
    }

    private var _binding: FragmentMainListChoiseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    fun init(){
        var user = iMainActivity?.getUser()
        var servis = iMainActivity?.getServis()
        var text:String = user?.nameUser + " ("+user?.role+"), "+ servis?.nameServis
        binding.tvInfo.text = text
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        binding.bAddCar.setOnClickListener {

            val action2 = MainListChoiseFragmentDirections.actionMainListChoiseFragmentToCarMainPageFragment()
            findNavController().navigate(action2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_main_list_choise, container, false)
        _binding = FragmentMainListChoiseBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainListChoiseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainListChoiseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}