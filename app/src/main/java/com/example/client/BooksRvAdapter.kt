package com.example.client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.data.BookInfo

class BooksRvAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<BooksRvAdapter.EmptyViewHolder>() {

    private var data = listOf<BookInfo>()

    interface OnItemClickListener {
        fun onItemClick(data: BookInfo)
    }

    fun setData(data: List<BookInfo>) {
        this.data = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.book_holder,
            parent,
            false
        )
        return EmptyViewHolder(itemView)
        // return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))

    }

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {


        holder.onBind(data[position],listener)
        //  holder.nameMovies.text=moviesList[position].NameMovies.toString()
        // holder.nameMovies.text="wwwwwwww"

    }
    // override fun getItemCount(): Int = 4
    override fun getItemCount(): Int {
        return data.count()
    }
    override fun getItemViewType(position: Int): Int {

        return 0
    }

    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameBook = itemView.findViewById<TextView>(R.id.tv_title)
        var genres = itemView.findViewById<TextView>(R.id.tv_genre)
        var bookInfo = itemView.findViewById<TextView>(R.id.tv_infobook)
        var icBook = itemView.findViewById<ImageView>(R.id.ic_book)


        fun onBind(data: BookInfo, listener: OnItemClickListener) {

           nameBook.text = data.name.toString()
           var genresAll:String =""
            for (x in data.genre){
                genresAll = genresAll +" "+ x.toString()
            }
           genres.text = genresAll
            var input = data.infoOfBook
            if (input.length > 100) {
                input.take(100)
            }
            bookInfo.text = data.infoOfBook.take(100) +"..."

            Glide.with(itemView.context)
                //.load(R.drawable.movie6)
                .load(data.urlImage)

                .into(icBook)

            // ic_movies.setBackgroundResource(R.drawable.gradient)*/
            itemView.setOnClickListener { listener.onItemClick(data) }

        }
    }




}