package com.cuongngo.cinemax.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel
import com.cuongngo.cinemax.response.movie_response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.repository.MediaRepository
import com.cuongngo.cinemax.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val mediaRepository: MediaRepository) : BaseViewModel() {
    private val _movieDetail = MutableLiveData<BaseResult<MovieDetailResponse>>()
    val movieDetail: LiveData<BaseResult<MovieDetailResponse>> get() = _movieDetail

    private val _listMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listMovie: LiveData<BaseResult<MovieResponse>> get() = _listMovie

    private val _listPopularMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listPopularMovie: LiveData<BaseResult<MovieResponse>> get() = _listPopularMovie

    private val _listGenreMovie = MutableLiveData<BaseResult<GenresMovieResponse>>()
    val listGenres: LiveData<BaseResult<GenresMovieResponse>> get() = _listGenreMovie

    private val _listGenreTV = MutableLiveData<BaseResult<GenresMovieResponse>>()
    val listGenreTV: LiveData<BaseResult<GenresMovieResponse>> get() = _listGenreTV

    private val _trendingMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val trendingMovie: LiveData<BaseResult<MovieResponse>> get() = _trendingMovie

    var page: Int = 1
    var keyword: String? = null


    init {
        if (App.getGenres().genres.isNullOrEmpty()){
            getGenresMovie()
        }
        getTrending()
    }

    fun getMovieDetail(
        movieId: String?
    ) {
        _movieDetail.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _movieDetail.postValue(mediaRepository.getMovieDetail(movieId))
            }
        }
    }

    fun getUpcoming() {
        _listMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listMovie.postValue(mediaRepository.getUpcoming())
            }
        }
    }

    fun getTrending() {
        _trendingMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _trendingMovie.postValue(
                    mediaRepository.getTrendingMovie(
                        media_type = Constants.MediaType.MOVIE,
                        time_window = Constants.TimeWindow.WEEK,
                        page = 1
                    )
                )
            }
        }
    }

    fun getGenresMovie() {
        _listGenreMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listGenreMovie.postValue(mediaRepository.getGenresMovie())
            }
        }
    }

    fun getGenresTV() {
        _listGenreTV.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listGenreTV.postValue(mediaRepository.getGenresTV())
            }
        }
    }

    fun getPopularMovie() {
        _listPopularMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listPopularMovie.postValue(
                    mediaRepository.getPopularMovie(page)
                )
            }
        }
    }

    fun loadMoreMovie(maxPage: Int) {
        if (page < maxPage) {
            page += 1
            if (keyword.isNullOrEmpty()) {
                getPopularMovie()
            } else {
//                searchMovie()
            }
        }
    }

}