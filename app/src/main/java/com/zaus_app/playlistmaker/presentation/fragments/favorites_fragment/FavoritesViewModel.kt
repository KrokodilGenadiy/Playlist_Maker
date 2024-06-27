package com.zaus_app.playlistmaker.presentation.fragments.favorites_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.interactors.FavoritesInteractor
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val interactor: FavoritesInteractor,
) :
    ViewModel() {

    private val _stateLiveData = MutableLiveData<FavoritesState>()
    val stateLiveData: LiveData<FavoritesState> = _stateLiveData

    init {
        viewModelScope.launch {
            interactor
                .getAllFavoritesTrack()
                .collect { result ->
                    if (result.isEmpty()) {
                        _stateLiveData.value = FavoritesState.Error
                    } else {
                        _stateLiveData.value = FavoritesState.Ready(result)
                    }
                }
        }
    }

    private fun fetchFavoriteTracks() {
        viewModelScope.launch {
            interactor.getAllFavoritesTrack().collect { result ->
                if (result.isEmpty()) {
                    _stateLiveData.postValue(FavoritesState.Error)
                } else {
                    _stateLiveData.postValue(FavoritesState.Ready(result))
                }
            }
        }
    }

    fun addTrackToFavorites(track: Track) {
        viewModelScope.launch {
            interactor.addTrack(track)
            fetchFavoriteTracks()
        }
    }
}