package com.cuongngo.cinemax.ui.youtube

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.base.activity.BaseActivity
import com.cuongngo.cinemax.base.viewmodel.kodeinViewModel
import com.cuongngo.cinemax.databinding.ActivityListVideoBinding
import com.cuongngo.cinemax.ext.observeLiveDataChanged
import com.cuongngo.cinemax.services.network.onResultReceived
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener

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
//        binding.layoutAppBar.ivBack.setOnClickListener {
//            onBackPressed()
//        }
//        setUpRecycleView()
    }

//    private fun setUpRecycleView(){
//        val gridLayoutManager = GridLayoutManager(this, 1)
//
//         videoAdapter = VideoAdapter(
//             arrayListOf()
//         )
//
//        binding.rcvListVideo.apply {
//            adapter = videoAdapter
//            layoutManager = gridLayoutManager
//        }
//    }

    private fun youtubeSetup(videoID: String, play: Boolean){
        lifecycle.addObserver(binding.youtubePlayerView)
        with(binding){
//            youtubePlayerView.enterFullScreen();
//            youtubePlayerView.exitFullScreen();
//            youtubePlayerView.isFullScreen();
//            youtubePlayerView.toggleFullScreen();
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady( youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoID, 0f)
                    if (!play){
                        youTubePlayer.pause()
                    }
                }
            })
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
//                    videoAdapter.submitListVideo(it.data?.results.orEmpty())
                    var keyYoutube: String? = it.data?.results?.firstOrNull()?.key.orEmpty()
                    it.data?.results?.forEach {
                        if(it.type == "Trailer"){
                            keyYoutube = it.key
                            return@forEach
                        }
                    }
                    youtubeSetup(keyYoutube.orEmpty(),true)
                },
                onError =  {
                    hideProgressDialog()
                }
            )
        }
    }

}