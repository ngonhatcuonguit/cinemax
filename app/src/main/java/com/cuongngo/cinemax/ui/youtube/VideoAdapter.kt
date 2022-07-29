package com.cuongngo.cinemax.ui.youtube

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemVideoBinding
import com.cuongngo.cinemax.response.movie_response.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class VideoAdapter(
    listVideo: List<Video>
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var listVideo = listVideo as ArrayList<Video>

    class VideoViewHolder(
        val itemVideoBinding: ItemVideoBinding
    ) : RecyclerView.ViewHolder(itemVideoBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_video,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val binding = holder.itemVideoBinding
        val video = listVideo[position]
        binding.video = video
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady( youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(video.key.orEmpty(), 0F)
                youTubePlayer.pause()
            }
        })
    }

    override fun getItemCount() = listVideo.size

    fun submitListVideo(listVideo: List<Video>){
        this.listVideo.addAll(listVideo)
        notifyDataSetChanged()
    }
}