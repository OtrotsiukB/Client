package com.example.client.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.client.BooksRvAdapter
import com.example.client.R
import com.example.client.RepositoryTest
import com.example.client.data.BookInfo
import com.example.client.databinding.FragmentAllBooksInGroupBinding
import com.example.client.iShowMiniPlayer

//import com.example.client.databinding.FragmentAllBooksInGroupBinding


class allBooksInGroup : Fragment(),BooksRvAdapter.OnItemClickListener {
    //private var _binding: FragmentFirstBinding? = null
    private var _binding: FragmentAllBooksInGroupBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var recycler: RecyclerView? = null

    private val movieAdapter = BooksRvAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_all_books_in_group, container, false)
        _binding = FragmentAllBooksInGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = binding.rvListBooks
        initRVAdapter()
        if (i_showMiniPlayer?.nowPlaing()==true){
            i_showMiniPlayer?.miniPlayerOnVisible()
        }

    }
    fun initRVAdapter(){
        movieAdapter.setData(RepositoryTest.books())
        recycler?.adapter=movieAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment allBooksInGroup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            allBooksInGroup().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClick(data: BookInfo) {
        // Ваш код, открывающий фрагмент
     //   val action = YourFragmentDirections.actionYourFragment(yourData)

        val action = allBooksInGroupDirections.actionAllBooksInGroupToBookDetalFragment2(data)
        findNavController().navigate(action)

      //  findNavController().navigate(R.id.action_FirstFragment_to_allBooksInGroup)


    }
}