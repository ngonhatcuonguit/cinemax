package com.cuongngo.cinemax.ui.movie.list_move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemHorizontalMovieBinding
import com.cuongngo.cinemax.ext.WTF
import com.cuongngo.cinemax.response.GenresMovie
import com.cuongngo.cinemax.response.Movie
import com.cuongngo.cinemax.roomdb.entity.GenreEntity

class MovieHorizontalAdapter(
    listMovie: ArrayList<Movie>,
    listGenre: ArrayList<GenresMovie>?,
    private val onItemClick: ((Movie) -> Unit)? = null
): RecyclerView.Adapter<MovieHorizontalAdapter.MovieHorizontalViewHolder>() {

    private val listMovie = listMovie
    private val listGenre = listGenre

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
        val genreID = movie.genre_ids?.firstOrNull()
        listGenre?.forEach {
            if (it.id == genreID){
                binding.tvGenre.text = it.name.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    fun submitListMovie(listMovie: List<Movie>){
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    class MovieHorizontalViewHolder(
        val movieHorizontalMovieBinding: ItemHorizontalMovieBinding
    ): RecyclerView.ViewHolder(movieHorizontalMovieBinding.root)

}