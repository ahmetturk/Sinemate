package com.ahmetroid.popularmovies.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetroid.popularmovies.data.model.Video
import com.ahmetroid.popularmovies.databinding.ItemVideoBinding

class VideosAdapter(private val listener: (key: String) -> Unit) :
    RecyclerView.Adapter<VideosAdapterViewHolder>() {

    private var list = emptyList<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideosAdapterViewHolder(
            ItemVideoBinding.inflate(layoutInflater, parent, false),
            listener
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VideosAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(videos: List<Video>) {
        list = videos
        notifyDataSetChanged()
    }
}

class VideosAdapterViewHolder(
    private val binding: ItemVideoBinding,
    private val listener: (key: String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(video: Video) {
        binding.video = video
        binding.videoCardView.setOnClickListener {
            listener(video.key)
        }
    }
}