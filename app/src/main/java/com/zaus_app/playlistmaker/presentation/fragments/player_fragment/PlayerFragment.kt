package com.zaus_app.playlistmaker.presentation.fragments.player_fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.databinding.FragmentPlayerBinding
import com.zaus_app.playlistmaker.presentation.fragments.search_fragment.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mainThreadHandler = Handler(Looper.getMainLooper())
        val track = arguments?.get("track") as Track
        setTrackDetails(track)
        preparePlayer(track)
        with(binding) {
            goBack.setOnClickListener {
                viewModel.mediaPlayer.release()
                viewModel.mainRunnable?.let { viewModel.mainThreadHandler?.removeCallbacks(it) }
                parentFragmentManager.popBackStack()
            }
            buttonPlayTrack.setOnClickListener {
                playbackControl()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
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
            trackTimer.text = START_TIME

            Glide.with(root.context)
                .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(trackCover)
        }
    }

    private fun preparePlayer(track: Track) {
        with(viewModel.mediaPlayer) {
            setDataSource(track.previewUrl)
            prepareAsync()
            setOnPreparedListener {
                viewModel.playerState = STATE_PREPARED
            }
            setOnCompletionListener {
                viewModel.playerState = STATE_PREPARED
            }
        }
    }



    private fun playbackControl() {
        when(viewModel.playerState) {
            STATE_PLAYING -> {
                binding.buttonPlayTrack.setImageDrawable(resources.getDrawable(R.drawable.play_track))
                viewModel.pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                binding.buttonPlayTrack.setImageDrawable(resources.getDrawable(R.drawable.pause_button))
                viewModel.startPlayer(::createUpdateTimerTask)
            }
        }
    }

    private fun createUpdateTimerTask(startTime: Long, duration: Long): Runnable {
        return object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                val remainingTime = duration - elapsedTime
                if (remainingTime > 0) {
                    if (_binding != null)
                        binding.trackTimer.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(viewModel.mediaPlayer.currentPosition)
                    viewModel.mainThreadHandler?.postDelayed(this, DELAY)
                } else {
                    if (_binding != null) {
                        binding.buttonPlayTrack.setImageDrawable(resources.getDrawable(R.drawable.play_track))
                        binding.trackTimer.text = "00:00"
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.mediaPlayer.release()
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 300L
        private const val TRACK_TIME = 29500L
        private const val START_TIME = "00:00"
    }

}