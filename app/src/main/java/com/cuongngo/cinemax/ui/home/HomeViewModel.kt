package com.cuongngo.cinemax.ui.home

import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel
import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel (private val movieRepository: MovieRepository): BaseViewModel() {
    private val _movieDetail = MutableLiveData<BaseResult<MovieDetailResponse>>()
    val movieDetail: LiveData<BaseResult<MovieDetailResponse>> get() = _movieDetail

    private val _listMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listMovie: LiveData<BaseResult<MovieResponse>> get() = _listMovie

    private val _listPopularMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listPopularMovie: LiveData<BaseResult<MovieResponse>> get() = _listPopularMovie

    private val _listGenres = MutableLiveData<BaseResult<GenresMovieResponse>>()
    val listGenres: LiveData<BaseResult<GenresMovieResponse>> get() = _listGenres

    var page: Int = 1
    var keyword: String? = null


    init {
        getGenresMovie()
        getUpcoming()
    }

    fun getMovieDetail(
        movieId: String?
    ) {
        _movieDetail.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _movieDetail.postValue(movieRepository.getMovieDetail(movieId))
            }
        }
    }

    fun getUpcoming(){
        _listMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _listMovie.postValue(movieRepository.getUpcoming())
            }
        }
    }

    fun getGenresMovie(){
        _listGenres.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _listGenres.postValue(movieRepository.getGenresMovie())
            }
        }
    }

    fun getPopularMovie(){
        _listPopularMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _listPopularMovie.postValue(
                    movieRepository.getPopularMovie(page)
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