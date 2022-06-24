package com.cuongngo.cinemax.di

import androidx.lifecycle.ViewModelProvider
import com.cuongngo.cinemax.base.viewmodel.bindViewModel
import com.cuongngo.cinemax.services.MediaApi
import com.cuongngo.cinemax.services.network.invoker.NetworkConnectionInterceptor
import com.cuongngo.cinemax.services.remote.MediaRemoteDataSource
import com.cuongngo.cinemax.services.repository.MediaRepository
import com.cuongngo.cinemax.ui.home.HomeViewModel
import com.cuongngo.cinemax.ui.media.MediaViewModel
import com.cuongngo.cinemax.ui.search.SearchViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

const val APP_MODULE = "app_module"

val appMovieModule = Kodein.Module(APP_MODULE, false) {
    bind() from singleton { NetworkConnectionInterceptor(instance()) }
    bind() from singleton { MediaApi()}

    bind() from singleton { MediaRemoteDataSource(instance()) }
    bind() from singleton { MediaRepository(instance()) }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
    bindViewModel<MediaViewModel>() with provider {
        MediaViewModel(instance())
    }
    bindViewModel<HomeViewModel>() with provider {
        HomeViewModel(instance())
    }
    bindViewModel<SearchViewModel>() with provider {
        SearchViewModel(instance())
    }
}