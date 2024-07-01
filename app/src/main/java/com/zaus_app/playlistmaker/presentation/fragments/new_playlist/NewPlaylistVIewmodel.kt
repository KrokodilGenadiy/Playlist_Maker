package com.zaus_app.playlistmaker.presentation.fragments.new_playlist

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import kotlinx.coroutines.launch

open class NewPlaylistViewModel(
    private val interactor: PlaylistInteractor
) : ViewModel() {

   private val statePlaylistLiveData = MutableLiveData<NewPlaylistState>()
    fun observeState(): LiveData<NewPlaylistState> = statePlaylistLiveData

    fun addPlaylist(playlistName: String, playlistDescription: String, imageUri: Uri?) {
        viewModelScope.launch {
            interactor.addPlaylist(
                Playlist(
                    0,
                    playlistName,
                    playlistDescription,
                    if (imageUri != null)
                        interactor.getImageFromPrivateStorage(
                        interactor.saveImageToPrivateStorage(imageUri)
                    ) else null,
                    mutableListOf(),
                    0
                )
            )
            statePlaylistLiveData.postValue(NewPlaylistState.Success)
        }
    }

    fun editPlaylist(playlist: Playlist,playlistName: String, playlistDescription: String, imageUri: Uri?) {
        viewModelScope.launch {
            interactor.updatePlaylist(
                Playlist(
                    playlist.id,
                    playlistName,
                    playlistDescription,
                    if (imageUri != null)
                        interactor.getImageFromPrivateStorage(
                            interactor.saveImageToPrivateStorage(imageUri)
                        ) else playlist.urlImage,
                    playlist.tracks,
                    playlist.tracksCount
                )
            )
            statePlaylistLiveData.postValue(NewPlaylistState.Success)
        }
    }
}