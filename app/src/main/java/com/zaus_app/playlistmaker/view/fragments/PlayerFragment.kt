package com.zaus_app.playlistmaker.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.data.Track
import com.zaus_app.playlistmaker.databinding.FragmentPlayerBinding
import com.zaus_app.playlistmaker.databinding.FragmentSettingsBinding
import com.zaus_app.playlistmaker.view.fragments.settings_fragment.SettingsViewModel
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val track = arguments?.get("track") as Track
        setTrackDetails(track)
        with(binding) {
            goBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setTrackDetails(track: Track) {
        with(binding) {
            durationTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            trackName.text = track.trackName
            artistName.text= track.artistName
            albumName.text = track.collectionName
            yearRelease.text = track.releaseDate.substring(0,4)
            genreName.text = track.primaryGenreName
            countryName.text = track.country
            trackTimer.text = "00:30"
            Glide.with(root.context)
                .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(trackCover)
        }
    }
}