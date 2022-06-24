package com.cuongngo.cinemax.ui.movie.detail

import android.content.Context
import android.content.Intent
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.AppBaseActivityMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityDetailMovieBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.movie.MovieViewModel

class MovieDetailActivity : AppBaseActivityMVVM<ActivityDetailMovieBinding, MovieViewModel>() {

    override val viewModel: MovieViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.activity_detail_movie

    companion object {
        val TAG = MovieDetailActivity::class.java.simpleName
        const val MEDIA_ID_KEY = "MEDIA_ID_KEY"
        const val MEDIA_TYPE = "MEDIA_TYPE"
    }

    fun newIntent(
        context: Context,
        mediaID: String,
        mediaType: String
    ): Intent {
        return Intent(context, MovieDetailActivity::class.java).apply {
            putExtra(MEDIA_ID_KEY, mediaID)
            putExtra(MEDIA_TYPE, mediaType)
        }
    }

    private val mediaID by lazy { intent.getStringExtra(MEDIA_ID_KEY) ?: "" }
    private val mediaType by lazy { intent.getStringExtra(MEDIA_TYPE) ?: "" }

    override fun setUp() {
        if (mediaType == "tv"){

        }else viewModel.getMovieDetail(mediaID)

        with(binding){
            layoutAppBar.ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.movieDetail){
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = { detail ->
                    hideProgressDialog()
                    binding.movieDetail = detail.data
                    binding.layoutAppBar.tvTitle.text = detail.data?.title.toString()
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
    }
}