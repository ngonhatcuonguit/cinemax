package com.cuongngo.cinemax.ui.search

import android.util.Log
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.common.collection.EndlessRecyclerViewScrollListener
import com.cuongngo.cinemax.databinding.SearchActivityBinding
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity<SearchActivityBinding>() {

    private val searchViewModel: SearchViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.search_activity

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var compositeDisposable: Disposable? = null

    private var currentKeyword: String? = null
    private var totalPages: Int = 1

    override fun setUp() {

        setupSearchMovie()
    }

    override fun setUpObserver() {

    }

    /**
     *  Setup search movie
     * */
    private fun setupSearchMovie() {
        compositeDisposable =
            binding.edtSearch.textChangeEvents().skip(1).debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    currentKeyword = it.text.toString()
                    if (currentKeyword != searchViewModel.keyword) {
                        searchViewModel.keyword = currentKeyword
//                        movieSection.clear()
                        searchViewModel.page = 1
                        if (searchViewModel.keyword.isNullOrEmpty()) {
                            Log.d("test_search", "getPopularMovie $currentKeyword")
                            searchViewModel.getPopularMovie()
                        } else {
                            Log.d("test_search", "searchMovie $currentKeyword")
                            searchViewModel.searchMovie()
                        }
                    }
                    hideKeyboard()
                }
    }
}