package com.zaus_app.playlistmaker.presentation.fragments.player_fragment

import android.media.MediaPlayer
import android.os.Handler
import androidx.lifecycle.ViewModel
import com.zaus_app.playlistmaker.domain.entities.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


class PlayerViewModel: ViewModel() {
    var track: Flow<Track> = emptyFlow()
    var mediaPlayer = MediaPlayer()
    var playerState = STATE_DEFAULT

    var mainThreadHandler: Handler? = null
    var mainRunnable: Runnable? = null

    fun startPlayer(op: (Long,Long) -> Runnable) {
        mediaPlayer.start()
        val startTime = System.currentTimeMillis()


        mainThreadHandler?.post(
            op(startTime, TRACK_TIME)

        )
        playerState = STATE_PLAYING
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        mainRunnable?.let { mainThreadHandler?.removeCallbacks(it) }
        playerState = STATE_PAUSED
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