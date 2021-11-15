package com.example.rappitv.moviesmodule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitv.R
import com.example.rappitv.databinding.ItemVideoBinding
import com.example.rappitv.domain.Result
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideosAdapter : ListAdapter<Result, VideosAdapter.MyViewHolder>(DiffUtilCallback) {
    private lateinit var mContext: Context


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = ItemVideoBinding.bind(itemView)
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val video = getItem(position)
        val videoMovie = holder.mBinding.youtubeVideoPlayerItemVideo
        holder.mBinding.tvNameItemVideo.text = video.name
        videoMovie.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = video.key
                youTubePlayer.loadVideo(videoId, 0F)
                youTubePlayer.pause()
            }

        })
    }
}