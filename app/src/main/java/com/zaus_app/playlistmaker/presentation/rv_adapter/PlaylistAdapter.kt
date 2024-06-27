package com.zaus_app.playlistmaker.presentation.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.PlaylistItemBinding
import com.zaus_app.playlistmaker.domain.entities.Playlist

class PlaylistAdapter(private val clickListener: OnItemClickListener)  :
    ListAdapter<Playlist, PlaylistAdapter.PlaylistViewHolder>(PlaylistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding =
            PlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding) {
            clickListener.click(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = getItem(position)
        holder.bind(playlist)
    }

    inner class PlaylistViewHolder(private val binding: PlaylistItemBinding, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
        fun bind(playlist: Playlist) {
            binding.apply {
                playlistName.text = playlist.playlistName
                countTracks.text = playlist.tracksCount.toString()
                Glide.with(root.context)
                    .load(playlist.urlImage)
                    .centerCrop()
                    .placeholder(R.drawable.playlist_placeholder)
                    .into(playlistCover)
            }
        }
    }

    class PlaylistDiffCallback : DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.playlistName == newItem.playlistName
        }

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem == newItem
        }
    }

    fun interface OnItemClickListener {
        fun click(playlist: Playlist)
    }
}