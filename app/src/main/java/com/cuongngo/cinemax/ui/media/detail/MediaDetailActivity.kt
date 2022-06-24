package com.cuongngo.cinemax.ui.media.detail

import android.content.Context
import android.content.Intent
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.AppBaseActivityMVVM
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityDetailMovieBinding
import com.cuongngo.cinemax.ext.WTF
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.response.MediaDetailResponse
import com.cuongngo.cinemax.services.network.onResultReceived
import com.cuongngo.cinemax.ui.media.MediaViewModel
import com.cuongngo.cinemax.utils.loadImagePath

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
        if (mediaType == "tv"){
            viewModel.getTvDetail(mediaID)
            WTF("tv_type $mediaType $mediaDetail")
        }else viewModel.getMovieDetail(mediaID)

        with(binding){
            layoutAppBar.ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun setUpObserver() {
        observeLiveDataChanged(viewModel.movieDetail){
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = { detail ->
                    hideProgressDialog()
                    mediaDetail.setMovieData(detail.data ?:return@onResultReceived)
                    binding.mediaDetail = mediaDetail
                    binding.layoutAppBar.tvTitle.text = detail.data.title.toString()

                    if (mediaDetail.media_type == "tv"){
                        loadImagePath(binding.ivPoster, mediaDetail.tvDetail?.poster_path)
                    }else loadImagePath(binding.ivPoster, mediaDetail.movieDetail?.poster_path)

                    if (mediaDetail.media_type == "tv"){
                        loadImagePath(binding.cvAvatar, mediaDetail.tvDetail?.poster_path)
                    }else loadImagePath(binding.cvAvatar, mediaDetail.movieDetail?.poster_path)
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
        observeLiveDataChanged(viewModel.tvDetail){
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = { detail ->
                    hideProgressDialog()
                    mediaDetail.setTvData(detail.data ?:return@onResultReceived)
                    binding.mediaDetail = mediaDetail
                    binding.layoutAppBar.tvTitle.text = detail.data.name.toString()

                    if (mediaDetail.media_type == "tv"){
                        loadImagePath(binding.ivPoster, mediaDetail.tvDetail?.poster_path)
                    }else loadImagePath(binding.ivPoster, mediaDetail.movieDetail?.poster_path)

                    if (mediaDetail.media_type == "tv"){
                        loadImagePath(binding.cvAvatar, mediaDetail.tvDetail?.poster_path)
                    }else loadImagePath(binding.cvAvatar, mediaDetail.movieDetail?.poster_path)
                },
                onError = {
                    hideProgressDialog()
                }
            )
        }
    }
}