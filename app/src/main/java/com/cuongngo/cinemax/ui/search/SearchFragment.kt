package com.cuongngo.cinemax.ui.search

import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.App.Companion.genreSelected
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.fragment.BaseFragmentMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.common.collection.EndlessRecyclerViewScrollListener
import com.cuongngo.cinemax.databinding.FragmentSearchBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.Movie
import com.cuongngo.cinemax.response.movie_response.GenresMovie
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.categories.GenreAdapter
import com.cuongngo.cinemax.ui.media.detail.MediaDetailActivity
import com.cuongngo.cinemax.ui.media.list_move.MovieAdapter
import com.cuongngo.cinemax.ui.personal.PersonalAdapter
import com.cuongngo.cinemax.utils.Constants

class SearchFragment : BaseFragmentMVVM<FragmentSearchBinding, SearchViewModel>(),
    GenreAdapter.SelectedListener, MovieAdapter.SelectedListener {

    override val viewModel: SearchViewModel by kodeinViewModel()

    private lateinit var genreAdapter: GenreAdapter
    private var listGenres = App.getGenres().genres
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var personalAdapter: PersonalAdapter
    private var movieTotalPages: Int = 1
    private var personalTotalPages: Int = 1
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun inflateLayout(): Int = R.layout.fragment_search

    override fun setUp() {
        val todayMovie = App.movieTrending.results?.firstOrNull()
        with(binding) {
            clSearch.setOnClickListener {
                startActivity(
                    SearchActivity.newIntent(
                        requireActivity()
                    )
                )
            }
            layoutToday.movie = todayMovie
            layoutToday.root.setOnClickListener {
                startActivity(
                    MediaDetailActivity().newIntent(
                        requireActivity(),
                        todayMovie?.id.orEmpty(),
                        Constants.MediaType.MOVIE
                    )
                )
            }

            val genreID = todayMovie?.genre_ids?.firstOrNull()

            listGenres?.forEach {
                if (it.id == genreID) {
                    layoutToday.tvGenre.text = it.name
                }
            }
        }

        setupRcvCategories()
        setupRecycleViewListMovie()
        setupRecycleViewListPersonal()
        viewModel.getPopularMovie()
        viewModel.getPopularPersonal()
    }

    private fun setupRcvCategories() {
        genreAdapter = GenreAdapter(
            arrayListOf(),
            this
        )
        genreAdapter.submitListGenres(listGenres as ArrayList<GenresMovie>)
        binding.rcvListGenres.adapter = genreAdapter
    }

    override fun onSelectedListener(genre: GenresMovie) {
        genreSelected = genre
        val oldData = listGenres?.find { it.is_selected }
        val oldIndex = listGenres?.indexOf(oldData)
        val index = listGenres?.indexOf(genre)

        oldData?.is_selected = false
        genre.is_selected = true
        listGenres?.set(oldIndex!!, (oldData ?: return))
        listGenres?.set(index!!, genre)
        genreAdapter.notifyItemChanged(index!!)
        genreAdapter.notifyItemChanged(oldIndex!!)
    }

    private fun setupRecycleViewListMovie() {

        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)

        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadMoreMoviePopular(movieTotalPages)
            }
        }

        movieAdapter = MovieAdapter(
            arrayListOf(),
            this
        )

        binding.rcvListMovieRecommend.apply {
            adapter = movieAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(scrollListener)
        }

    }

    private fun setupRecycleViewListPersonal(){
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)

        scrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadMorePersonalPopular(personalTotalPages)
            }
        }

        personalAdapter = PersonalAdapter(
            arrayListOf()
        )
        binding.rcvListPopularPersonal.apply {
            adapter =personalAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(scrollListener)
        }

    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.listPopularMovie) {
            it.onResultReceived(
                onLoading = {
                    binding.flProgressBarMoviePopular.isVisible = true
                },
                onSuccess = {
                    binding.flProgressBarMoviePopular.isVisible = false
                    movieAdapter.submitListMovie(it.data?.results ?: return@onResultReceived)
                    movieTotalPages = it.data.total_pages ?: return@onResultReceived
                },
                onError = {
                    binding.flProgressBarMoviePopular.isVisible = false
                }
            )
        }
        observeLiveDataChanged(viewModel.popularPersonal){
            it.onResultReceived(
                onLoading = {

                },
                onSuccess = {
                    personalAdapter.submitList(it.data?.results ?: return@onResultReceived)
                    personalTotalPages = it.data?.total_pages ?:return@onResultReceived
                },
                onError = {

                }
            )
        }
    }

    override fun onSelectedListener(movie: Movie) {
        startActivity(MediaDetailActivity().newIntent(requireActivity(), movie.id.orEmpty(), Constants.MediaType.MOVIE))
    }

    companion object {
        val TAG = SearchFragment::class.java.simpleName
    }

}