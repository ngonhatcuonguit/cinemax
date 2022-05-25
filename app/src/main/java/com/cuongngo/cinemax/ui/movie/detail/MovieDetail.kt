package com.cuongngo.cinemax.ui.movie.detail

import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.AppBaseActivityMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityDetailMovieBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.movie.MovieViewModel

class MovieDetail : AppBaseActivityMVVM<ActivityDetailMovieBinding, MovieViewModel>() {

    override val viewModel: MovieViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.activity_detail_movie

    override fun setUp() {
        viewModel.getMovieDetail("752623")
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