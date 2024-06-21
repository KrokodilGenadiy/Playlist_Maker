package com.zaus_app.playlistmaker.presentation.fragments.player_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bumptech.glide.Glide
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.databinding.FragmentPlayerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private var  favorite_flag = false
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var timeInterval: String
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
        viewModel.track = flowOf(track)
        setTrackDetails()
        initFavoritesButton()
        with(binding) {
            goBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            setFavoritesButtonStatus()
            viewModel.observePlayState().observe(viewLifecycleOwner) {
                timeInterval = it.progress
                binding.buttonPlayTrack.isEnabled = it.checkingButtonStatus
                binding.buttonPlayTrack.setImageResource(it.buttonState)
                binding.trackTimer.text = it.progress
            }

            binding.buttonPlayTrack.setOnClickListener {
                viewModel.playbackControl()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("timer",binding.trackTimer.text.toString() )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            binding.trackTimer.text = savedInstanceState.getString("timer")
        }
    }

    private fun setTrackDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.track.collectLatest {
                with(binding) {
                    durationTime.text =
                        SimpleDateFormat("mm:ss", Locale.getDefault()).format(it.trackTimeMillis)
                    trackName.text = it.trackName
                    artistName.text = it.artistName
                    albumName.text = it.collectionName
                    yearRelease.text = it.releaseDate.substring(0, 4)
                    genreName.text = it.primaryGenreName
                    countryName.text = it.country
                    if (trackTimer.text.isEmpty())
                        trackTimer.text = START_TIME
                    Glide.with(root.context)
                        .load(it.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .into(trackCover)
                    viewModel.trackId = it.trackId
                    viewModel.preparePlayer(it.previewUrl)
                }
            }
        }
    }

    private fun initFavoritesButton() {
        with(binding) {
            buttonFavorites.setOnClickListener {
                setFavoritesButtonStatus()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.track.collectLatest {
                        it.addTime = System.currentTimeMillis()
                        viewModel.addTrack(it)
                    }
                }
                /*favorite_flag = if (!favorite_flag) {
                    binding.buttonPlayTrack.setBackgroundResource(R.drawable.add_favorites_filled)
                    true
                } else {
                    binding.buttonPlayTrack.setBackgroundResource(R.drawable.add_favorites)
                    false
                }*/
            }
        }
    }
    private fun setFavoritesButtonStatus() {
        viewModel.isFavoriteTrack.observe(viewLifecycleOwner) { isFavoriteTrack ->
            if (isFavoriteTrack) binding.buttonFavorites.setImageResource(R.drawable.add_favorites_filled)
            else binding.buttonFavorites.setImageResource(R.drawable.add_favorites)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val START_TIME = "00:00"
    }

}