package com.cuongngo.cinemax.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuongngo.cinemax.base.viewmodel.BaseViewModel
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.repository.MediaRepository
import com.cuongngo.cinemax.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val mediaRepository: MediaRepository) : BaseViewModel() {

    private val _searchMulti = MutableLiveData<BaseResult<MultiMediaResponse>>()
    val searchMulti: LiveData<BaseResult<MultiMediaResponse>> get() = _searchMulti

    private val _listPopularMovie = MutableLiveData<BaseResult<MovieResponse>>()
    val listPopularMovie: LiveData<BaseResult<MovieResponse>> get() = _listPopularMovie

    private val _trendingMedia = MutableLiveData<BaseResult<MultiMediaResponse>>()
    val trendingMedia: LiveData<BaseResult<MultiMediaResponse>> get() = _trendingMedia

    var page: Int = 1
    var preKeyword: String? = null
    var keyword: String? = null

    init {
//        getPopularMovie()
    }

    fun updateKeyword(currentKeyword: String?) {
        preKeyword = keyword
        keyword = currentKeyword
    }

    fun searchMulti() {
        _searchMulti.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searchMulti.postValue(mediaRepository.searchMedia(keyword, page))
            }
        }
    }

    fun searchMultiPreKeyword() {
        _searchMulti.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searchMulti.postValue(mediaRepository.searchMedia(preKeyword, page))
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

    fun getTrending() {
        _trendingMedia.value = BaseResult.loading(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _trendingMedia.postValue(
                    mediaRepository.getTrending(
                        media_type = Constants.MediaType.MOVIE,
                        time_window = Constants.TimeWindow.WEEK,
                        page = 1
                    )
                )
            }
        }
    }

    fun loadMoreSearch(maxPage: Int) {
        if (page < maxPage) {
            page += 1
            if (!keyword.isNullOrEmpty()) {
                searchMulti()
            } else {
                searchMultiPreKeyword()
            }
        }
    }

}