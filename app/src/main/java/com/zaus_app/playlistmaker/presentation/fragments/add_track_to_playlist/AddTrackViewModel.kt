package com.zaus_app.playlistmaker.presentation.fragments.add_track_to_playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.entities.TrackInPlaylist
import com.zaus_app.playlistmaker.domain.interactors.FavoritesInteractor
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTrackViewModel(
    private val interactor: PlaylistInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<PlaylistsState>()
    fun observeState(): LiveData<PlaylistsState> = stateLiveData

    private val addTrackMessageLiveData = MutableLiveData<AddTrackSignUiModel>()
    fun observeAddTrackMessage(): LiveData<AddTrackSignUiModel> = addTrackMessageLiveData

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

    fun updatePlaylist(track: Track, playlist: Playlist, playlistPosition: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addTrackMessageLiveData.postValue(
                    AddTrackSignUiModel(
                        isAdded = interactor.addTrackInPlaylist(track, playlist),
                        track = track,
                        playlist = playlist,
                        playlistPosition = playlistPosition
                    )
                )
            }
        }
    }

    fun addTrackToPlaylistTable(trackInPlaylist: TrackInPlaylist) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favoritesInteractor.addTrackToPlaylistTable(trackInPlaylist)
            }
        }
    }

    suspend fun updatePlaylist(track: Track, playlist: Playlist): Boolean =
        interactor.addTrackInPlaylist(track, playlist)
}