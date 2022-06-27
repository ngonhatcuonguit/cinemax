package com.cuongngo.cinemax.ui.media.list_move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemMovieBinding
import com.cuongngo.cinemax.response.movie_response.GenresMovie
import com.cuongngo.cinemax.response.Movie

class MovieAdapter (
    listMovie: ArrayList<Movie>,
    listGenres: List<GenresMovie>?,
    private val selectedListener: SelectedListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovie = listMovie
    private val listGenres = listGenres

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

        val genre = movie.genre_ids?.firstOrNull()

        listGenres?.forEach {
            if (it.id == genre){
                binding.tvGenre.text = it.name ?: ""
            }
        }

        if (position == 0){
            val paddingStart = App.getResources().getDimensionPixelOffset(R.dimen._24dp)
            val paddingEnd = App.getResources().getDimensionPixelOffset(R.dimen._12dp)
            binding.clContainer.setPadding(paddingStart,0,paddingEnd,0)
        }

        binding.root.setOnClickListener {
            selectedListener.onSelectedListener(movie)
        }

    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    interface SelectedListener {
        fun onSelectedListener(movie: Movie)
    }

    fun submitListMovie(listMovie: List<Movie>){
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        val itemMovieBinding: ItemMovieBinding
    ): RecyclerView.ViewHolder(itemMovieBinding.root)
}