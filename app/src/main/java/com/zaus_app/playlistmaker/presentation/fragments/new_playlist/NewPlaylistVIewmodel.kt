package com.zaus_app.playlistmaker.presentation.fragments.new_playlist

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.repositrories.PlaylistRepository
import kotlinx.coroutines.launch

open class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistRepository
) : ViewModel() {

   private val statePlaylistLiveData = MutableLiveData<NewPlaylistState>()
    fun observeState(): LiveData<NewPlaylistState> = statePlaylistLiveData

    fun addPlaylist(playlistName: String, playlistDescription: String, imageUri: Uri?) {
        viewModelScope.launch {
            playlistsRepository.addPlaylist(
                Playlist(
                    0,
                    playlistName,
                    playlistDescription,
                    if (imageUri != null)
                        playlistsRepository.getImageFromPrivateStorage(
                        playlistsRepository.saveImageToPrivateStorage(imageUri)
                    ) else null,
                    mutableListOf(),
                    0
                )
            )
            statePlaylistLiveData.postValue(NewPlaylistState.Success)
        }
    }
}