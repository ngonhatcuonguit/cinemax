package com.cuongngo.cinemax.ui.home

import android.os.Handler
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.fragment.BaseFragmentMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.HomeFragmentBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.GenresMovie
import com.cuongngo.cinemax.response.Movie
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.view_pager.ViewPagerAdapter
import com.cuongngo.cinemax.ui.movie.list_move.MovieAdapter
import com.cuongngo.cinemax.ui.view_pager.ViewPagerHelper
import kotlin.math.abs

class HomeFragment : BaseFragmentMVVM<HomeFragmentBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by kodeinViewModel()

    override fun inflateLayout() = R.layout.home_fragment

    private val sliderHandle = Handler()
    private lateinit var movieAdapter: MovieAdapter
    private var currentKeyword: String? = null
    private var totalPages: Int = 1
    private var listGenres: List<GenresMovie> = emptyList()

    override fun setUp() {
        with(binding){

        }
    }

    private val sliderRunnable= Runnable{
        binding.vpTopViewpager.currentItem = binding.vpTopViewpager.currentItem + 1
    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.listMovie) {
            it.onResultReceived(
                onLoading = {},
                onSuccess = {
                    ViewPagerHelper(
                        viewPager2 = binding.vpTopViewpager,
                        defaultPos = 0,
                        viewPagerAdapter = ViewPagerAdapter(
                            data = it.data?.results ?: return@onResultReceived,
                            viewPager2 = binding.vpTopViewpager
                        ),
                        onPageChanged = {}
                    ).autoScroll(lifecycleScope, 3000)
                        .execute()
                },
                onError = {}
            )
        }

        observeLiveDataChanged(viewModel.listPopularMovie) {
            it.onResultReceived(
                onLoading = {},
                onSuccess = {
                    hideProgressDialog()
                    setupRecycleViewListMovie(it.data?.results ?: return@onResultReceived)
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }

        observeLiveDataChanged(viewModel.listGenres){
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = {
                    listGenres = it.data?.genres ?: return@onResultReceived
                    viewModel.getPopularMovie()
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }

    }

    private fun setupRecycleViewListMovie(listMovie: List<Movie>) {
        movieAdapter = MovieAdapter(
            arrayListOf(),
            listGenres
        )
        movieAdapter.submitListMovie(listMovie)
        binding.rcvListMovie.apply {
            adapter = movieAdapter
        }
    }

    companion object{
        val TAG = HomeFragment::class.java.simpleName
    }
}