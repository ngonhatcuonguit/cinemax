package com.cuongngo.cinemax.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.movie_response.VideoResponse
import com.cuongngo.cinemax.response.tv_response.TvDetailResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.repository.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaViewModel (private val mediaRepository: MediaRepository): BaseViewModel() {
    private val _movieDetail = MutableLiveData<BaseResult<MovieDetailResponse>>()
    val movieDetail: LiveData<BaseResult<MovieDetailResponse>> get() = _movieDetail

    private val _tvDetail = MutableLiveData<BaseResult<TvDetailResponse>>()
    val tvDetail: LiveData<BaseResult<TvDetailResponse>> get() = _tvDetail

    private val _listMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listMovie: LiveData<BaseResult<MovieResponse>> get() = _listMovie

    private val _listPopularMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listPopularMovie: LiveData<BaseResult<MovieResponse>> get() = _listPopularMovie

    var page: Int = 1
    var keyword: String? = null

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

    fun getTvDetail(
        tvId: String?
    ) {
        _tvDetail.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tvDetail.postValue(mediaRepository.getTvDetail(tvId))
            }
        }
    }

    fun getUpcoming(){
        _listMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _listMovie.postValue(mediaRepository.getUpcoming())
            }
        }
    }

    fun getPopularMovie(){
        _listPopularMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _listPopularMovie.postValue(
                    mediaRepository.getPopularMovie(page)
                )
            }
        }
    }

    fun loadMoreMovie(maxPage: Int) {
        if (page < maxPage) {
            page = page.plus(1)
            if (keyword.isNullOrEmpty()) {
                getPopularMovie()
            } else {
//                searchMovie()
            }
        }
    }

}