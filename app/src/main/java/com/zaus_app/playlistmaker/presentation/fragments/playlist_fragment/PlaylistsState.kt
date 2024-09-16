package com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment

import com.zaus_app.playlistmaker.domain.entities.Playlist

sealed interface PlaylistsState {
    data class Content(
        val playList: List<Playlist>
    ) : PlaylistsState

    data class Empty(val playList: List<Playlist>) : PlaylistsState
}