package com.zaus_app.playlistmaker.domain.interactors

import com.zaus_app.playlistmaker.domain.entities.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    suspend fun addTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    fun getTracksIDs(): Flow<List<Int>>
    suspend fun getTrackById(trackId: Int): Track?
    fun getAllFavoritesTrack(): Flow<List<Track>>
}