package com.cuongngo.cinemax.ui.youtube

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityListVideoBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.services.network.onResultReceived

class YoutubePlayerActivity : BaseActivity<ActivityListVideoBinding>() {

    private val videoViewModel: VideoViewModel by kodeinViewModel()

    override fun inflateLayout() = R.layout.activity_list_video

    private val movieID by lazy { intent.getStringExtra(MOVIE_ID)}

    private lateinit var videoAdapter: VideoAdapter

    companion object{
        const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(context: Context, movieID:String?): Intent{
            return Intent(context, YoutubePlayerActivity::class.java).apply {
                putExtra(MOVIE_ID, movieID)
            }
        }
    }

    override fun setUp() {
        videoViewModel.getListVideo(movieID.toString().orEmpty())
        setUpRecycleView()
    }

    private fun setUpRecycleView(){
        val gridLayoutManager = GridLayoutManager(this, 1)

         videoAdapter = VideoAdapter(
             arrayListOf()
         )

        binding.rcvListVideo.apply {
            adapter = videoAdapter
            layoutManager = gridLayoutManager
        }
    }

    override fun setUpObserver() {
        observeLiveDataChanged(videoViewModel.listVideo){
            it.onResultReceived(
                onLoading = {
                    showProgressDialog()
                },
                onSuccess = {
                    hideProgressDialog()
                    videoAdapter.submitListVideo(it.data?.results.orEmpty())
                },
                onError =  {
                    hideProgressDialog()
                }
            )
        }
    }

}