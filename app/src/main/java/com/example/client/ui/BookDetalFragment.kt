package com.example.client.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.data.BookInfo
import com.example.client.databinding.FragmentBookDetalBinding
import com.example.client.databinding.FragmentFirstBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookDetalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookDetalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentBookDetalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var book:BookInfo


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

        val integerPart = number.toInt()
        val fractionalPart = number.rem(1.0)
        if (fractionalPart>0){
            binding.tvCycle.text = book.cycle + " ("+book.numberCycle+")"
        }else{
            binding.tvCycle.text = book.cycle + " ("+book.numberCycle.toInt()+")"

        }


        var allgenry =""
        for (item in book.genre){
            allgenry = allgenry +""+item+" "
        }


        binding.tvGenre.text = allgenry
        binding.tvInfobook.text = book.infoOfBook


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: BookDetalFragmentArgs by navArgs()
        book = args.book
        initComponent()
        // В коде вашего фрагмента


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
}