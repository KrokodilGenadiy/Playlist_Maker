package com.zaus_app.playlistmaker.view.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.data.Track
import com.zaus_app.playlistmaker.databinding.TrackItemBinding

class TrackAdapter :
    ListAdapter<Track, TrackAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding =
            TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }

    inner class TrackViewHolder(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            binding.apply {
                trackName.text = track.trackName
                artistName.text = track.artistName
                duration.text = track.trackTime
                Glide.with(root.context)
                    .load(track.artworkUrl100)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(image)
            }
        }
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.trackName == newItem.trackName
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}
