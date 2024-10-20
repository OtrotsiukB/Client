package com.example.client.autoservis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.client.R
import com.example.client.autoservis.data.dataapi.APICarInfo
import com.example.client.autoservis.network.retrofitAutoservis
import com.example.client.databinding.FragmentCarMainPageBinding
import com.example.client.databinding.FragmentMainListChoiseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarMainPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarMainPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentCarMainPageBinding? = null
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
        //return inflater.inflate(R.layout.fragment_car_main_page, container, false)
        _binding = FragmentCarMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bAdd.setOnClickListener {
            var nomerCar = binding.tvNomerCar.text.toString()
            if (nomerCar!=""){
                var modelCar = binding.tvAutoModel.text.toString()
                if (modelCar!=""){
                    var phone = binding.tvPhone.text.toString()
                    if (phone!=""){
                        var obem = binding.tvObem.text.toString()
                        if (obem!=""){
                            var kw = binding.tvKw.text.toString()
                            if (kw!=""){
                                var yearCar = binding.tvYearCar.text.toString()
                                if (yearCar!=""){
                                    var vin = binding.tvVin.text.toString()
                                    if (vin!= ""){
                                        val car:APICarInfo = APICarInfo(nomerCar,modelCar,phone,obem,kw, vin, yearCar)
                                        addCar(car)
                                    }else{
                                        // Показать Toast
                                        Toast.makeText(
                                            requireContext(),
                                            "Введите VIN авто",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }else{
                                    // Показать Toast
                                    Toast.makeText(
                                        requireContext(),
                                        "Введите год выпуска авто",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }else{
                                // Показать Toast
                                Toast.makeText(
                                    requireContext(),
                                    "Введите KW авто",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }else{
                            // Показать Toast
                            Toast.makeText(
                                requireContext(),
                                "Введите обьем авто",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }else{
                        // Показать Toast
                        Toast.makeText(
                            requireContext(),
                            "Введите телефон",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }else{
                    // Показать Toast
                    Toast.makeText(
                        requireContext(),
                        "Введите модель авто",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }else{

                    // Показать Toast
                    Toast.makeText(
                        requireContext(),
                        "Введите номер авто",
                        Toast.LENGTH_LONG
                    ).show()


            }
        }
    }
    fun addCar(car:APICarInfo){
        CoroutineScope(Dispatchers.IO).launch {
            try {

              var x =  retrofitAutoservis.RetrofitModule.iRetrofitAutoservis.addCar(car)
                var y = x
                var z = y
                CoroutineScope(Dispatchers.Main).launch {
                    // Показать Toast
                    Toast.makeText(
                        requireContext(),
                        "Добавлено авто",
                        Toast.LENGTH_LONG
                    ).show()
                }
                //добавиить переход
            }catch (e:Exception){
                val t = e
                val h = t
                CoroutineScope(Dispatchers.Main).launch {
                    // Показать Toast
                    Toast.makeText(
                        requireContext(),
                        "Ошибка добавления авто",
                        Toast.LENGTH_LONG
                    ).show()
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
         * @return A new instance of fragment CarMainPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarMainPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}