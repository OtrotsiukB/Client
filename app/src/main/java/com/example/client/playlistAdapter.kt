package com.example.client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.data.BookInfo

class playlistAdapter(private val listener: playlistAdapter.OnItemClickListener) : RecyclerView.Adapter<playlistAdapter.EmptyViewHolder>() {


   private  var data: MutableList<Files> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(data: Files)
    }

    fun setData(data: MutableList<Files>) {
        this.data = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playlistAdapter.EmptyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.playlist_holder,
            parent,
            false
        )
        return playlistAdapter.EmptyViewHolder(itemView)
        // return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))

    }
    override fun onBindViewHolder(holder: playlistAdapter.EmptyViewHolder, position: Int) {


        holder.onBind(data[position],listener)
        //  holder.nameMovies.text=moviesList[position].NameMovies.toString()
        // holder.nameMovies.text="wwwwwwww"

    }
    override fun getItemCount(): Int {
        return data.count()
    }
    override fun getItemViewType(position: Int): Int {

        return 0
    }



    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameTrek = itemView.findViewById<TextView>(R.id.tv_file)



        fun onBind(data: Files, listener: playlistAdapter.OnItemClickListener) {

            if (data.title=="---"){
                nameTrek.text = data.title+"("+data.name+")"
            }else {
                nameTrek.text = data.title
            }

            // ic_movies.setBackgroundResource(R.drawable.gradient)*/
            itemView.setOnClickListener { listener.onItemClick(data) }

        }
    }

}