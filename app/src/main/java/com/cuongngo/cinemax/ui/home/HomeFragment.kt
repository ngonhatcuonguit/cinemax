package com.cuongngo.cinemax.ui.home

import android.os.Handler
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.App.Companion.setListTrending
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.fragment.BaseFragmentMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.common.collection.EndlessRecyclerViewScrollListener
import com.cuongngo.cinemax.databinding.HomeFragmentBinding
import com.cuongngo.cinemax.ext.WTF
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.Movie
import com.cuongngo.cinemax.response.movie_response.GenresMovie
import com.cuongngo.cinemax.response.movie_response.GenresMovieResponse
import com.cuongngo.cinemax.roomdb.entity.GenreEntity
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.categories.GenreAdapter
import com.cuongngo.cinemax.ui.media.detail.MediaDetailActivity
import com.cuongngo.cinemax.ui.media.list_move.MovieAdapter
import com.cuongngo.cinemax.ui.search.SearchActivity
import com.cuongngo.cinemax.ui.view_pager.ViewPagerAdapter
import com.cuongngo.cinemax.ui.view_pager.ViewPagerHelper
import com.cuongngo.cinemax.utils.Constants
import kotlinx.coroutines.launch

class HomeFragment : BaseFragmentMVVM<HomeFragmentBinding, HomeViewModel>(),
    GenreAdapter.SelectedListener, MovieAdapter.SelectedListener {

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
    private var genresMovieResponse: GenresMovieResponse? = null
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun setUp() {
        with(binding) {
            tvHintSearch.setOnClickListener {
                startActivity(
                    SearchActivity.newIntent(
                        requireActivity()
                    )
                )
            }
            ivSearch.setOnClickListener {
                startActivity(
                    SearchActivity.newIntent(
                        requireActivity()
                    )
                )
            }
        }
        setupRecycleViewListMovie()
    }

    private val sliderRunnable = Runnable {
        binding.vpTopViewpager.currentItem = binding.vpTopViewpager.currentItem + 1
    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.trendingMovie) {
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
                                startActivity(
                                    MediaDetailActivity().newIntent(
                                        requireActivity(),
                                        it.id.orEmpty(),
                                        Constants.MediaType.MOVIE
                                    )
                                )
                            }
                        ),
                        onPageChanged = {}
                    ).autoScroll(lifecycleScope, 3000)
                        .execute()

                    setListTrending(it.data ?:return@onResultReceived)
                },
                onError = { }
            )
        }

        observeLiveDataChanged(viewModel.listGenres) {
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = {
                    listGenres = it.data?.genres ?: return@onResultReceived
                    genresMovieResponse = it.data
                    setupRcvCategories(it.data.genres.orEmpty())
//                    setupGenreLocal(it.data.genres)
                    viewModel.getGenresTV()
                    viewModel.getPopularMovie()
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }

        observeLiveDataChanged(viewModel.listPopularMovie) {
            it.onResultReceived(
                onLoading = {
                    binding.flProgressBar.isVisible = true
                },
                onSuccess = {
                    binding.flProgressBar.isVisible = false
                    hideProgressDialog()
                    movieAdapter.submitListMovie(it.data?.results ?: return@onResultReceived)
                    totalPages = it.data.total_pages ?: return@onResultReceived
                },
                onError = {
                    hideProgressDialog()
                    binding.flProgressBar.isVisible = false
                }
            )
        }

        observeLiveDataChanged(viewModel.listGenreTV) {
            it.onResultReceived(
                onLoading = {},
                onSuccess = {
                    genresMovieResponse?.addGenres(it.data?.genres.orEmpty() as ArrayList<GenresMovie>)
                    App.setGenres(genresMovieResponse ?: return@onResultReceived)
                    WTF("genres ${App.getGenres()}")
                },
                onError = {}
            )
        }

    }

    private fun setupGenreLocal(listGenre: List<GenresMovie>) {
        App.getGenreDatabase().getGenres().let { list ->
            if (list.isEmpty()) {
                lifecycleScope.launch {
                    listGenre.forEach { genre ->
                        App.getGenreDatabase()
                            .addGenre(GenreEntity(genre.id.toString(), genre.name.orEmpty()))
                    }
                }
            } else {
            }
            listLocalGenres = App.getGenreDatabase().getGenres()
        }
    }

    private fun setupRecycleViewListMovie() {

        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)

        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadNextDataFromApi()
            }
        }

        movieAdapter = MovieAdapter(
            arrayListOf(),
            listGenres,
            this
        )

        binding.rcvListMovie.apply {
            adapter = movieAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(scrollListener)
        }

    }

    private fun setupRcvCategories(listGenres: List<GenresMovie>) {
        genreAdapter = GenreAdapter(
            arrayListOf(),
            this
        )
        if (genreSelected == null) {
            listGenres.firstOrNull()?.is_selected = true
            genreSelected = listGenres.firstOrNull()
        }
        genreAdapter.submitListGenres(listGenres)
        binding.rcvListGenres.adapter = genreAdapter
    }

    /**
     *  Load more movie result
     * */
    private fun loadNextDataFromApi() {
        viewModel.loadMoreMovie(totalPages)
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
        startActivity(MediaDetailActivity().newIntent(requireActivity(), movie.id.orEmpty(), Constants.MediaType.MOVIE))
    }

    companion object {
        val TAG = HomeFragment::class.java.simpleName
    }

}