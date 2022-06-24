package com.cuongngo.cinemax.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.repository.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel (private val mediaRepository: MediaRepository) : BaseViewModel() {

    private val _searchMulti = MutableLiveData<BaseResult<MultiMediaResponse>>()
    val searchMulti: LiveData<BaseResult<MultiMediaResponse>> get() = _searchMulti

    private val _listPopularMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listPopularMovie: LiveData<BaseResult<MovieResponse>> get() = _listPopularMovie

    var page: Int = 1
    var keyword: String? = null

    init {
//        getPopularMovie()
    }

    fun searchMulti() {
        _searchMulti.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searchMulti.postValue(mediaRepository.searchMedia(keyword, page))
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

}