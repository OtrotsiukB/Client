package com.example.client.autoservis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.client.R
import com.example.client.autoservis.network.retrofitAutoservis
import com.example.client.databinding.FragmentAllBooksInGroupBinding
import com.example.client.databinding.FragmentStartAuthorizationBinding
import com.example.client.network.retrofit
import com.example.client.ui.allBooksInGroupDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartAuthorizationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartAuthorizationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentStartAuthorizationBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
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
       // return inflater.inflate(R.layout.fragment_start_authorization, container, false)
        _binding = FragmentStartAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bLogin.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                var login = binding.tvLogin.text.toString()
                var password = binding.tvPassword.text.toString()
              //  var info = retrofit.RetrofitModule.iRetrofit.getinfo(testId)
                try {
                    var tempUser =
                        retrofitAutoservis.RetrofitModule.iRetrofitAutoservis.getUser(login)
                   if (tempUser[0].nameUser == login && tempUser[0].paswordUser == password){
                      // action_startAuthorizationFragment_to_mainListChoiseFragment
                     //  = allBooksInGroupDirections.actionAllBooksInGroupToBookDetalFragment2(data)
                       val action = StartAuthorizationFragmentDirections.actionStartAuthorizationFragmentToMainListChoiseFragment()
                       findNavController().navigate(action)
                   }


                }catch (e:Exception)
                {

                }

            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartAuthorizationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartAuthorizationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}