package com.cuongngo.cinemax.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.common.collection.EndlessRecyclerViewScrollListener
import com.cuongngo.cinemax.databinding.SearchActivityBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.media.detail.MediaDetailActivity
import com.cuongngo.cinemax.ui.media.list_move.MovieHorizontalAdapter
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
    private var isMore: Boolean = true

    private lateinit var horizontalMovieAdapter: MovieHorizontalAdapter

    private val genres by lazy { intent.getSerializableExtra(LIST_GENRE) as? GenresMovieResponse }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLightStatusBar()
    }

    override fun setUp() {
        binding.edtSearch.requestFocus()
        binding.tvCancel.setOnClickListener {
            binding.edtSearch.setText("")
            binding.edtSearch.requestFocus()
            showKeyBoard()
        }
        setupSearchMulti()
        setupRecycleViewListMovie()
    }

    override fun setUpObserver() {
        observeLiveDataChanged(searchViewModel.searchMulti) {
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = {
                    hideProgressDialog()
                    totalPages = it.data?.total_pages ?: return@onResultReceived
                    if (it.data.results.orEmpty().size <= 20) {
                        isMore = false
                    }
                    horizontalMovieAdapter.submitListMovie(it.data)
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }

    }

    private fun setupRecycleViewListMovie() {

        val gridLayoutManager = GridLayoutManager(this, 1)

        horizontalMovieAdapter = MovieHorizontalAdapter(
            arrayListOf(),
            genres?.genres,
            onItemClick = {
                startActivity(
                    MediaDetailActivity().newIntent(
                        this,
                        it.id.orEmpty(),
                        it.media_type.orEmpty()
                    )
                )
            }
        )

        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                searchViewModel.loadMoreSearch(totalPages)
            }
        }

        binding.rcvListMovieSearch.apply {
            adapter = horizontalMovieAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(scrollListener)
        }

        //setup load more
//        binding.rcvListMovieSearch.let {
//            it.addOnScrollListener( object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if (!isMore && dy > 20){
//                        val total = (it.layoutManager as? LinearLayoutManager)!!.itemCount
//                        val lastItem = (it.layoutManager as? LinearLayoutManager)!!.findLastVisibleItemPosition()
//                        if (lastItem == total - 1){
//
//                        }
//                    }
//                }
//            })
//        }
    }

    /**
     *  Setup search movie
     * */
    private fun setupSearchMulti() {
        compositeDisposable =
            binding.edtSearch.textChangeEvents().skip(1).debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    currentKeyword = it.text.toString()
                    if (currentKeyword != searchViewModel.keyword) {
                        searchViewModel.updateKeyword(currentKeyword)
                        searchViewModel.page = 1
                        if (searchViewModel.keyword.isNullOrEmpty()) {
                            Log.d("test_search", "getPopularMovie $currentKeyword")
//                            searchViewModel.getPopularMovie()
                        } else {
                            Log.d("test_search", "searchMovie $currentKeyword")
                            searchViewModel.searchMulti()
                            hideKeyboard()
                        }
                    }
                }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view: View? = currentFocus
        val ret = super.dispatchTouchEvent(event)
        if (view is EditText) {
            currentFocus?.let {
                val w: View = it
                val scrcoords = IntArray(2)
                w.getLocationOnScreen(scrcoords)
                val x: Float = event.rawX + w.left - scrcoords[0]
                val y: Float = event.rawY + w.top - scrcoords[1]
                if (event.action == MotionEvent.ACTION_UP
                    && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
                ) {
                    hideKeyboard()

                }
            }
        }
        return ret
    }

    companion object {
        val TAG = SearchActivity::class.java.simpleName
        const val LIST_GENRE = "LIST_GENRE"

        fun newIntent(
            context: Context,
            genresMovieResponse: GenresMovieResponse
        ): Intent {
            return Intent(context, SearchActivity::class.java).apply {
                putExtra(LIST_GENRE, genresMovieResponse)
            }
        }

    }

}