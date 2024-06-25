package com.zaus_app.playlistmaker.presentation.fragments.new_playlist

sealed interface NewPlaylistState {

    data object Success : NewPlaylistState
    data object Error : NewPlaylistState
}