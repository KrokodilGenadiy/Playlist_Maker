package com.zaus_app.playlistmaker.data.implementations

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.data.db.FavoritesDatabase
import com.zaus_app.playlistmaker.data.db.PlaylistDatabase
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.repositrories.ImageStorageRepository
import com.zaus_app.playlistmaker.domain.repositrories.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl(
    private val playlistDb: PlaylistDatabase,
    private val trackDb: FavoritesDatabase,
    private val imageStorage: ImageStorageRepository
) : PlaylistRepository {
    override suspend fun addPlaylist(playlist: Playlist) {
        playlistDb.playlistDao().insertPlaylist(playlist)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDb.playlistDao().getAllPlaylist()
    }


    override suspend fun getPlaylistById(playlistId: Int): Playlist {
        return playlistDb.playlistDao().getPlaylistById(playlistId)
    }

    override suspend fun addTrackInPlaylist(track: Track, playlist: Playlist): Boolean {
        val result =playlistDb.playlistDao().getPlaylistById(playlist.id)
        return if (result.tracks.contains(track.trackId))
            false
        else {
            playlist.tracks.add(track.trackId)
            playlist.tracksCount += 1
            playlistDb.playlistDao().updatePlaylist(playlist)
            true
        }
    }

    override fun saveImageToPrivateStorage(uri: Uri): String {
        return imageStorage.saveImageToPrivateStorage(uri)
    }

    override fun getImageFromPrivateStorage(imageName: String): Uri {
        return imageStorage.getImageFromPrivateStorage(imageName)
    }

    override suspend fun updatePlaylistAndDeleteTrack(trackId: Int, playlist: Playlist) {
        playlist.tracks.remove(trackId)
        playlist.tracksCount = playlist.tracks.size
        playlistDb.playlistDao().updatePlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistDb.playlistDao().updatePlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        playlistDb.playlistDao().deletePlaylist(playlistId)
    }

    override suspend fun getTrackById(trackId: Int): Track? {
        return trackDb.trackDao().getTrackById(trackId)

    }

}