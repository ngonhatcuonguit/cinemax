package com.cuongngo.cinemax.ui.media.list_move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemHorizontalMovieBinding
import com.cuongngo.cinemax.response.MultiMedia
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.utils.Constants

class MovieHorizontalAdapter(
    listMedia: ArrayList<MultiMedia>,
    private val onItemClick: ((MultiMedia) -> Unit)? = null
) : RecyclerView.Adapter<MovieHorizontalAdapter.MovieHorizontalViewHolder>() {

    private val listMedia = listMedia

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
        var media: MultiMedia? = if (listMedia[position].media_type == Constants.MediaType.TV) {
            listMedia[position].known_for?.firstOrNull()
        } else listMedia[position]

        binding.multiMedia = media

        binding.root.setOnClickListener {
            onItemClick?.invoke(media ?: return@setOnClickListener)
        }
        val genreID = media?.genre_ids?.firstOrNull()
        App.getGenres().genres?.forEach {
            if (it.id == genreID) {
                binding.tvGenre.text = it.name.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return listMedia.size
    }

    fun submitListMovie(mediaResponse: MultiMediaResponse?) {
        if (mediaResponse?.page == 1) {
            this.listMedia.clear()
        }
        this.listMedia.addAll(mediaResponse?.results.orEmpty())
        notifyDataSetChanged()
    }

    class MovieHorizontalViewHolder(
        val movieHorizontalMovieBinding: ItemHorizontalMovieBinding
    ) : RecyclerView.ViewHolder(movieHorizontalMovieBinding.root)

}