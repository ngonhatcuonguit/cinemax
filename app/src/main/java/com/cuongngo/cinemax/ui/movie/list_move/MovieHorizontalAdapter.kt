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
import com.cuongngo.cinemax.response.MultiMedia
import com.cuongngo.cinemax.roomdb.entity.GenreEntity

class MovieHorizontalAdapter(
    listMedia: ArrayList<MultiMedia>,
    listGenre: ArrayList<GenresMovie>?,
    private val onItemClick: ((MultiMedia) -> Unit)? = null
): RecyclerView.Adapter<MovieHorizontalAdapter.MovieHorizontalViewHolder>() {

    private val listMedia = listMedia
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
        val media = listMedia[position]
        binding.multiMedia = media
        binding.root.setOnClickListener {
            onItemClick?.invoke(media)
        }
        val genreID = media.genre_ids?.firstOrNull()
        listGenre?.forEach {
            if (it.id == genreID){
                binding.tvGenre.text = it.name.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return listMedia.size
    }

    fun submitListMovie(listMedia: List<MultiMedia>){
        this.listMedia.clear()
        this.listMedia.addAll(listMedia)
        notifyDataSetChanged()
    }

    class MovieHorizontalViewHolder(
        val movieHorizontalMovieBinding: ItemHorizontalMovieBinding
    ): RecyclerView.ViewHolder(movieHorizontalMovieBinding.root)

}