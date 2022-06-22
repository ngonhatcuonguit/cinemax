package com.cuongngo.cinemax.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel (private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _searchMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val searchMovie: LiveData<BaseResult<MovieResponse>> get() = _searchMovie

    private val _listPopularMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listPopularMovie: LiveData<BaseResult<MovieResponse>> get() = _listPopularMovie

    var page: Int = 1
    var keyword: String? = null

    init {
//        getPopularMovie()
    }

    fun searchMovie() {
        _searchMovie.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searchMovie.postValue(movieRepository.searchMovie(keyword, page))
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

}