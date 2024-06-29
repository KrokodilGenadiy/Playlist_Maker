package com.zaus_app.playlistmaker.presentation.fragments.add_track_to_playlist

import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track

data class AddTrackSignUiModel(
    val isAdded: Boolean,
    val track: Track,
    val playlist: Playlist,
    val playlistPosition: Int
)