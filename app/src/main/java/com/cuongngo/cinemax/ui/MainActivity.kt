package com.cuongngo.cinemax.ui

import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.AppBaseActivityMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityMainBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.movie.MovieViewModel

class MainActivity : AppBaseActivityMVVM<ActivityMainBinding, MovieViewModel>() {

    override val viewModel: MovieViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.activity_main

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
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
    }
}