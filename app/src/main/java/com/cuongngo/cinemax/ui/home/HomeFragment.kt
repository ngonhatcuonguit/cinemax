package com.cuongngo.cinemax.ui.home

import android.app.Activity
import android.os.Handler
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.fragment.BaseFragmentMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.HomeFragmentBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.GenresMovie
import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.Movie
import com.cuongngo.cinemax.roomdb.AppDatabase
import com.cuongngo.cinemax.roomdb.entity.GenreEntity
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.categories.GenreAdapter
import com.cuongngo.cinemax.ui.movie.detail.MovieDetailActivity
import com.cuongngo.cinemax.ui.movie.list_move.MovieAdapter
import com.cuongngo.cinemax.ui.search.SearchActivity
import com.cuongngo.cinemax.ui.view_pager.ViewPagerAdapter
import com.cuongngo.cinemax.ui.view_pager.ViewPagerHelper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragmentMVVM<HomeFragmentBinding, HomeViewModel>(), GenreAdapter.SelectedListener, MovieAdapter.SelectedListener {

    override val viewModel: HomeViewModel by kodeinViewModel()

    override fun inflateLayout() = R.layout.home_fragment

    private val sliderHandle = Handler()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var genreAdapter: GenreAdapter
    private var currentKeyword: String? = null
    private var totalPages: Int = 1
    private var listLocalGenres: List<GenreEntity> = emptyList()
    private var listGenres: ArrayList<GenresMovie> = arrayListOf()
    private var genreSelected: GenresMovie? = null
    private var genresMovieResponse:  GenresMovieResponse? = null

    override fun setUp() {
        with(binding) {
            clSearch.setOnClickListener {
                startActivity(SearchActivity.newIntent(requireActivity(), genresMovieResponse ?: return@setOnClickListener))
            }
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
                            viewPager2 = binding.vpTopViewpager,
                            onItemClick = {
                                startActivity(MovieDetailActivity().newIntent(requireActivity(),it.id.orEmpty()))
                            }
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
                    genresMovieResponse = it.data
                    setupRcvCategories(it.data.genres)
//                    setupGenreLocal(it.data.genres)
                    viewModel.getPopularMovie()
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }

    }

    private fun setupGenreLocal(listGenre: List<GenresMovie>) {
        App.getGenreDatabase().getGenres().let { list ->
            if (list.isEmpty()){
                lifecycleScope.launch {
                    listGenre.forEach{ genre ->
                        App.getGenreDatabase().addGenre(GenreEntity(genre.id.toString(), genre.name.orEmpty()))
                    }
                }
            }else{ }
            listLocalGenres = App.getGenreDatabase().getGenres()
        }
    }

    private fun setupRecycleViewListMovie(listMovie: List<Movie>) {
        movieAdapter = MovieAdapter(
            arrayListOf(),
            listGenres,
            this
        )
        movieAdapter.submitListMovie(listMovie)
        binding.rcvListMovie.apply {
            adapter = movieAdapter
        }
    }

    private fun setupRcvCategories(listGenres: List<GenresMovie>){
        genreAdapter = GenreAdapter(
            arrayListOf(),
            this
        )
        if (genreSelected == null){
            listGenres.firstOrNull()?.is_selected = true
            genreSelected = listGenres.firstOrNull()
        }
        genreAdapter.submitListGenres(listGenres)
        binding.rcvListGenres.adapter = genreAdapter
    }

    override fun onSelectedListener(genre: GenresMovie) {
        genreSelected = genre
        val oldData = listGenres.find { it.is_selected }
        val oldIndex = listGenres.indexOf(oldData)
        val index = listGenres.indexOf(genre)

        oldData?.is_selected = false
        genre.is_selected = true
        listGenres[oldIndex] = (oldData ?: return)
        listGenres[index] = genre
        genreAdapter.notifyItemChanged(index)
        genreAdapter.notifyItemChanged(oldIndex)
    }


    override fun onSelectedListener(movie: Movie) {
        startActivity(MovieDetailActivity().newIntent(requireActivity(),movie.id.orEmpty()))
    }

    companion object{
        val TAG = HomeFragment::class.java.simpleName
    }

}