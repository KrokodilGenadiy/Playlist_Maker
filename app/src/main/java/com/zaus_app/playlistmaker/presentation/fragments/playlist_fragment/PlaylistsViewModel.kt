package com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import com.zaus_app.playlistmaker.domain.repositrories.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val playlistsRepository: PlaylistInteractor) : ViewModel() {

    private val stateLiveData = MutableLiveData<PlaylistsState>()
    fun observeState(): LiveData<PlaylistsState> = stateLiveData

    init {
        viewModelScope.launch {
            playlistsRepository.getAllPlaylists().collect { result ->
                if (result.isEmpty()) {
                    stateLiveData.postValue(PlaylistsState.Empty(result))
                } else {
                    stateLiveData.postValue(PlaylistsState.Content(result))
                }
            }
        }
    }
}