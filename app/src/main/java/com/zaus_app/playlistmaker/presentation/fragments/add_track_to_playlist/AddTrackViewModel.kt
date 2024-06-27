package com.zaus_app.playlistmaker.presentation.fragments.add_track_to_playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsState
import kotlinx.coroutines.launch

class AddTrackViewModel(private val interactor: PlaylistInteractor): ViewModel() {
    private val stateLiveData = MutableLiveData<PlaylistsState>()
    fun observeState(): LiveData<PlaylistsState> = stateLiveData

    init {
        viewModelScope.launch {
            interactor.getAllPlaylists().collect { result ->
                if (result.isEmpty()) {
                    stateLiveData.postValue(PlaylistsState.Empty(result))
                } else {
                    stateLiveData.postValue(PlaylistsState.Content(result))
                }
            }
        }
    }
    suspend fun updatePlaylist(track: Track,playlist: Playlist): Boolean = interactor.addTrackInPlaylist(track,playlist)
}