package com.cuongngo.cinemax.ui.movie.list_move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemHorizontalMovieBinding
import com.cuongngo.cinemax.response.Movie

class MovieHorizontalAdapter(
    listMovie: ArrayList<Movie>,
    private val onItemClick: ((Movie) -> Unit)? = null
): RecyclerView.Adapter<MovieHorizontalAdapter.MovieHorizontalViewHolder>() {

    private val listMovie = listMovie

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHorizontalViewHolder {
        return MovieHorizontalViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_horizontal_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieHorizontalViewHolder, position: Int) {
        val binding = holder.movieHorizontalMovieBinding
        val movie = listMovie[position]
        binding.movie = movie
        binding.root.setOnClickListener {
            onItemClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    fun submitListMovie(listMovie: List<Movie>){
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    class MovieHorizontalViewHolder(
        val movieHorizontalMovieBinding: ItemHorizontalMovieBinding
    ): RecyclerView.ViewHolder(movieHorizontalMovieBinding.root)

}