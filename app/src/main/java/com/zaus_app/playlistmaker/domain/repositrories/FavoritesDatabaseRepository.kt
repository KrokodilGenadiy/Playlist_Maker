package com.zaus_app.playlistmaker.domain.repositrories

import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.entities.TrackInPlaylist
import kotlinx.coroutines.flow.Flow

interface FavoritesDatabaseRepository {

    suspend fun addTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun getTracksIDs(): Flow<List<Int>>
    suspend fun getTrackById(trackId: Int): Track?
    fun getAllFavoritesTrack(): Flow<List<Track>>

    suspend fun getPlaylistTrackById(trackId: Int): Track?

    suspend fun addTrackToPlaylistTable(track: TrackInPlaylist)
}