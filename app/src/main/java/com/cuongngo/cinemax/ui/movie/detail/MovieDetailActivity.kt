package com.cuongngo.cinemax.ui.movie.detail

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
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
        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
    }

    fun newIntent(
        context: Context,
        movieID: String
    ): Intent {
        return Intent(context, MovieDetailActivity::class.java).apply {
            putExtra(MOVIE_ID_KEY, movieID)
        }
    }

    private val movieID by lazy { intent.getStringExtra(MOVIE_ID_KEY) ?: "" }

    override fun setUp() {
        viewModel.getMovieDetail(movieID)
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