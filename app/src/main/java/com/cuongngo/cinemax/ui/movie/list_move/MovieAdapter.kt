package com.cuongngo.cinemax.ui.movie.list_move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemMovieBinding
import com.cuongngo.cinemax.response.Movie

class MovieAdapter (
    listMovie: ArrayList<Movie>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovie = listMovie

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MovieViewHolder
        val binding = holder.itemMovieBinding
        val movie = listMovie[position]
        binding.movie = movie
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    fun submitListMovie(listMovie: List<Movie>){
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        val itemMovieBinding: ItemMovieBinding
    ): RecyclerView.ViewHolder(itemMovieBinding.root)
}