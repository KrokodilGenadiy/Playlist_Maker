package com.zaus_app.playlistmaker.presentation.fragments.player_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.interactors.AudioPlayerInteractor
import com.zaus_app.playlistmaker.domain.interactors.FavoritesInteractor
import com.zaus_app.playlistmaker.domain.repositrories.AudioPlayerRepository
import com.zaus_app.playlistmaker.domain.repositrories.FavoritesDatabaseRepository
import com.zaus_app.playlistmaker.domain.util.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.Locale


class PlayerViewModel(private val audioPlayerInteractor: AudioPlayerInteractor,
    private val favoritesInteractor: FavoritesInteractor) : ViewModel() {


    var track: Flow<Track> = emptyFlow()
    private var timerJob: Job? = null

    private val _isFavoriteTrack = MutableLiveData(false)
    val isFavoriteTrack: LiveData<Boolean> = _isFavoriteTrack
    var trackId = -1


    init {
        viewModelScope.launch {
            track.collectLatest { trackId = it.trackId }
        }
        viewModelScope.launch {
            favoritesInteractor.getTracksIDs().collect { trackIds ->
                _isFavoriteTrack.value = trackIds.contains(trackId)
            }
        }
    }

    private val playState = MutableLiveData<StateAudioPlayer>(StateAudioPlayer.Default())
    fun observePlayState(): LiveData<StateAudioPlayer> = playState

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        stoppingPlayer()
    }

    fun onPause() {
        pausePlayer()
    }

    private fun stoppingPlayer() {
        audioPlayerInteractor.stoppingPlayer()
        StateAudioPlayer.Default()
    }

    fun playbackControl() {
        if (audioPlayerInteractor.getCurrentState() == State.PLAYING) {
            pausePlayer()
        } else {
            startPlayer()
        }
    }

    fun preparePlayer(url: String) {
        audioPlayerInteractor.preparePlayer(url = url) {
            playState.postValue(StateAudioPlayer.Prepared())
            timerJob?.cancel()
        }
        playState.postValue(StateAudioPlayer.Prepared())
    }


    private fun pausePlayer() {
        audioPlayerInteractor.pausePlayer()
        playState.postValue(StateAudioPlayer.Paused(getCurrentPlayerPosition()))
        timerJob?.cancel()
    }

    private fun startPlayer() {
        audioPlayerInteractor.startPlayer()
        playState.postValue(StateAudioPlayer.Playing(getCurrentPlayerPosition()))
        startTimer()
    }

    private fun getCurrentPlayerPosition(): String {
        return if (audioPlayerInteractor.getCurrentPosition() > 29800)
            "00:00"
        else
            android.icu.text.SimpleDateFormat(
                "mm:ss",
                Locale.getDefault()
            ).format(audioPlayerInteractor.getCurrentPosition()) ?: "00:00"
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (audioPlayerInteractor.getCurrentState() == State.PLAYING) {
                delay(DELAY_MILLIS)
                playState.postValue(StateAudioPlayer.Playing(getCurrentPlayerPosition()))
            }
        }
    }

    fun addTrack(track: Track) {
        viewModelScope.launch {
            if (_isFavoriteTrack.value == true) {
                favoritesInteractor.deleteTrack(track)
                _isFavoriteTrack.postValue(false)
            } else {
                favoritesInteractor.addTrack(track)
                _isFavoriteTrack.postValue(true)
            }
        }
    }

    companion object {
        private const val DELAY_MILLIS = 300L
    }

}

sealed class StateAudioPlayer(
    val checkingButtonStatus: Boolean,
    val buttonState: Int,
    val progress: String
) {
    abstract fun isPrepared(): Boolean

    class Default : StateAudioPlayer(false, R.drawable.pause_button, "00:00") {
        override fun isPrepared(): Boolean = false
    }

    class Prepared : StateAudioPlayer(true, R.drawable.play_track, "00:00") {
        override fun isPrepared(): Boolean = true
    }

    class Playing(progress: String) : StateAudioPlayer(true, R.drawable.pause_button, progress) {
        override fun isPrepared(): Boolean = false
    }

    class Paused(progress: String) : StateAudioPlayer(true, R.drawable.play_track, progress) {
        override fun isPrepared(): Boolean = false
    }
}

