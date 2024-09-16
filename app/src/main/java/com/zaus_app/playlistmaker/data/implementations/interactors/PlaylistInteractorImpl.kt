package com.zaus_app.playlistmaker.data.implementations.interactors

import android.net.Uri
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import com.zaus_app.playlistmaker.domain.repositrories.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(private val repository: PlaylistRepository): PlaylistInteractor {
    override suspend fun addPlaylist(playlist: Playlist) {
       repository.addPlaylist(playlist)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> = repository.getAllPlaylists()

    override fun saveImageToPrivateStorage(uri: Uri): String = repository.saveImageToPrivateStorage(uri)

    override fun getImageFromPrivateStorage(imageName: String): Uri = repository.getImageFromPrivateStorage(imageName)

    override suspend fun getPlaylistById(playlistId: Int): Playlist = repository.getPlaylistById(playlistId)

    override suspend fun addTrackInPlaylist(track: Track, playlist: Playlist) = repository.addTrackInPlaylist(track,playlist)

    override suspend fun updatePlaylistAndDeleteTrack(track: Track, playlist: Playlist) = repository.updatePlaylistAndDeleteTrack(track.trackId, playlist)

    override suspend fun updatePlaylist(playlist: Playlist) {
        repository.updatePlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        repository.deletePlaylist(playlistId)
    }

    override suspend fun getTrackById(trackId: Int): Track? = repository.getTrackById(trackId)
}