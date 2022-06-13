package com.cuongngo.cinemax.base.viewmodel

import androidx.lifecycle.LiveData
import com.cuongngo.cinemax.common.SingleLiveEvent

abstract class BaseCollectionViewModel: BaseViewModel() {
    private val _isLoadingMore = SingleLiveEvent<Boolean>().apply { value = false }
    var isLoadingMore: LiveData<Boolean> = _isLoadingMore
    var after: String? = null

    fun invokeLoadMore(){
        if (!after.isNullOrEmpty() && _isLoadingMore.value == false)
            loadMore()
    }

    open fun loadMore(){
        showLoadingMore()
    }

    open fun refreshState(){
        after = null
        hideLoadingMore()
    }

    open fun showLoadingMore(){
        _isLoadingMore.postValue(true)
    }

    open fun hideLoadingMore(){
        _isLoadingMore.postValue(false)
    }

    open fun hideModeLoading(loadingMode: LoadingMode){
        when (loadingMode) {
            LoadingMode.LOAD -> hideLoading()
            LoadingMode.LOAD_MORE -> hideLoadingMore()
        }
    }

    open fun showModeLoading(loadingMode: LoadingMode){
        when (loadingMode) {
            LoadingMode.LOAD -> showLoading()
            LoadingMode.LOAD_MORE -> showLoadingMore()
        }
    }
}