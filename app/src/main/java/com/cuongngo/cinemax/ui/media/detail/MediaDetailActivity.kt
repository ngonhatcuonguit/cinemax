package com.cuongngo.cinemax.ui.media.detail

import android.content.Context
import android.content.Intent
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.AppBaseActivityMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityDetailMovieBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.MediaDetailResponse
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.media.MediaViewModel
import com.cuongngo.cinemax.utils.loadImagePath
import com.cuongngo.cinemax.utils.roundRating

class MediaDetailActivity : AppBaseActivityMVVM<ActivityDetailMovieBinding, MediaViewModel>() {

    override val viewModel: MediaViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.activity_detail_movie

    companion object {
        val TAG = MediaDetailActivity::class.java.simpleName
        const val MEDIA_ID_KEY = "MEDIA_ID_KEY"
        const val MEDIA_TYPE = "MEDIA_TYPE"
    }

    private var mediaDetail = MediaDetailResponse(
        null,
        null,
        null
    )

    fun newIntent(
        context: Context,
        mediaID: String,
        mediaType: String
    ): Intent {
        return Intent(context, MediaDetailActivity::class.java).apply {
            putExtra(MEDIA_ID_KEY, mediaID)
            putExtra(MEDIA_TYPE, mediaType)
        }
    }

    private val mediaID by lazy { intent.getStringExtra(MEDIA_ID_KEY) ?: "" }
    private val mediaType by lazy { intent.getStringExtra(MEDIA_TYPE) ?: "" }

    override fun setUp() {
        mediaDetail.setMediaType(mediaType)
        if (mediaType == "tv") {
            viewModel.getTvDetail(mediaID)
        } else viewModel.getMovieDetail(mediaID)

        with(binding) {
            layoutAppBar.ivBack.setOnClickListener {
                onBackPressed()
            }

            tvOverview.apply{
                setShowingLine(4)
                setShowLessTextColor(App.getResources().getColor(R.color.blue_accent))
                setShowMoreTextColor(App.getResources().getColor(R.color.blue_accent))
                addShowLessText("Less")
                addShowMoreText("More")
            }
        }
    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.movieDetail) {
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = { detail ->
                    hideProgressDialog()
                    mediaDetail.setMovieData(detail.data ?: return@onResultReceived)
                    binding.mediaDetail = mediaDetail
                    binding.layoutAppBar.tvTitle.text = detail.data.title.toString()
                    bindSateDetail()
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
        observeLiveDataChanged(viewModel.tvDetail) {
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = { detail ->
                    hideProgressDialog()
                    mediaDetail.setTvData(detail.data ?: return@onResultReceived)
                    binding.mediaDetail = mediaDetail
                    bindSateDetail()
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
    }

    private fun bindSateDetail() {
        if (mediaDetail.media_type == "tv") {
            val tvDetail = mediaDetail.tvDetail
            with(binding) {
                loadImagePath(ivPosterBackground, tvDetail?.poster_path)
                loadImagePath(cvAvatar, tvDetail?.poster_path)
                layoutAppBar.tvTitle.text = tvDetail?.name.toString()
                tvReleaseYear.text = tvDetail?.first_air_date.toString()
                tvDuration.text = tvDetail?.last_episode_to_air?.runtime.toString() + " Minutes"
                tvCategory.text = tvDetail?.genres?.firstOrNull()?.name
                roundRating(tvRating, tvDetail?.vote_average ?: 0F)
            }

        } else {
            val movieDetail = mediaDetail.movieDetail
            with(binding) {
                loadImagePath(ivPosterBackground, movieDetail?.poster_path)
                loadImagePath(cvAvatar, movieDetail?.poster_path)
                layoutAppBar.tvTitle.text = movieDetail?.title.toString()
                tvReleaseYear.text = movieDetail?.release_date.toString()
                tvDuration.text = movieDetail?.runtime.toString() + " Minutes"
                tvCategory.text = movieDetail?.genres?.firstOrNull()?.name
                roundRating(tvRating, movieDetail?.vote_average ?: 0F)
            }
        }
    }

}