package com.cuongngo.cinemax.di

import androidx.lifecycle.ViewModelProvider
import com.cuongngo.cinemax.base.viewmodel.bindViewModel
import com.cuongngo.cinemax.services.MovieApi
import com.cuongngo.cinemax.services.network.invoker.NetworkConnectionInterceptor
import com.cuongngo.cinemax.services.remote.MovieRemoteDataSource
import com.cuongngo.cinemax.services.repository.MovieRepository
import com.cuongngo.cinemax.ui.movie.MovieViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

const val APP_MODULE = "app_module"

val appMovieModule = Kodein.Module(APP_MODULE, false) {
    bind() from singleton { NetworkConnectionInterceptor(instance()) }
    bind() from singleton { MovieApi()}

    bind() from singleton { MovieRemoteDataSource(instance()) }
    bind() from singleton { MovieRepository(instance()) }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
    bindViewModel<MovieViewModel>() with provider {
        MovieViewModel(instance())
    }
}