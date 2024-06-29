package com.zaus_app.playlistmaker.presentation.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.TrackItemBinding
import com.zaus_app.playlistmaker.domain.entities.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistTrackAdapter(private val clickListener: OnItemClickListener,)  :
    ListAdapter<Track, PlaylistTrackAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding =
            TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding,clickListener)
    }
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }

    inner class TrackViewHolder(private val binding: TrackItemBinding, clickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickListener.click(getItem(adapterPosition))
            }
            binding.root.setOnLongClickListener {
                clickListener.onLongClick(getItem(adapterPosition),adapterPosition)
            }
        }
        fun bind(track: Track) {
            binding.apply {
                trackName.text = track.trackName
                artistName.text = track.artistName
                duration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
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

    interface OnItemClickListener {
        fun click(track: Track)
        fun onLongClick(track: Track,position: Int): Boolean
    }
}