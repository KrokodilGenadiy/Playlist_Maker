package com.zaus_app.playlistmaker.domain.repositrories

import android.net.Uri
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun addPlaylist(playlist: Playlist)
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun saveImageToPrivateStorage(uri: Uri): String
    fun getImageFromPrivateStorage(imageName: String): Uri
    suspend fun getPlaylistById(playlistId: Int): Playlist
    suspend fun addTrackInPlaylist(trackId: Int, playlist: Playlist)
    suspend fun updatePlaylistAndDeleteTrack(trackId: Int, playlist: Playlist)
    suspend fun updatePlaylist(playlist: Playlist)
    suspend fun deletePlaylist(playlistId: Int)
    suspend fun getTrackById(trackId: Int): Track?
}