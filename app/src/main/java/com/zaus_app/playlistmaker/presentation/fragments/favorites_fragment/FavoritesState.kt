package com.zaus_app.playlistmaker.presentation.fragments.favorites_fragment

import com.zaus_app.playlistmaker.domain.entities.Track

sealed class FavoritesState {
    data object Loading : FavoritesState()
    data class Ready(val favoritesList: List<Track>) : FavoritesState()
    data object Error : FavoritesState()
}