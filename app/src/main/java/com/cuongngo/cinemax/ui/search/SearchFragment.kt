package com.cuongngo.cinemax.ui.search

import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.fragment.BaseFragmentMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.FragmentSearchBinding
import com.cuongngo.cinemax.response.movie_response.GenresMovie
import com.cuongngo.cinemax.ui.categories.GenreAdapter

class SearchFragment : BaseFragmentMVVM<FragmentSearchBinding, SearchViewModel>(),
    GenreAdapter.SelectedListener {

    override val viewModel: SearchViewModel by kodeinViewModel()

    private lateinit var genreAdapter: GenreAdapter
    private var genreSelected: GenresMovie? = null
    private var listGenres = App.getGenres().genres

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

            val genreID = todayMovie?.genre_ids?.firstOrNull()

            listGenres?.forEach {
                if (it.id == genreID) {
                    layoutToday.tvGenre.text = it.name
                }
            }

            setupRcvCategories()
        }

    }

    private fun setupRcvCategories() {
        genreAdapter = GenreAdapter(
            arrayListOf(),
            this
        )
        if (genreSelected == null) {
            listGenres?.firstOrNull()?.is_selected = true
            genreSelected = listGenres?.firstOrNull()
        }
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

    override fun setUpObserver() {

    }

    companion object {
        val TAG = SearchFragment::class.java.simpleName
    }

}