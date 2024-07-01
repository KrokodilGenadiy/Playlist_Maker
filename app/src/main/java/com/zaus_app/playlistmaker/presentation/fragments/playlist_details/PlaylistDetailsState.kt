package com.zaus_app.playlistmaker.presentation.fragments.playlist_details

import android.net.Uri
import com.zaus_app.playlistmaker.domain.entities.Track

sealed class PlaylistDetailsState {
    data class Content(
        val imageUrl: Uri?,
        val playlistName: String,
        val playlistDetails: String?,
        val playlistDuration: String?,
        val playlistCountTrack: String?,
        val listTracks: List<Track>?
    ) : PlaylistDetailsState()

    data object Delete : PlaylistDetailsState()
}