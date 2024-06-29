package com.zaus_app.playlistmaker.data.implementations.interactors

import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.entities.TrackInPlaylist
import com.zaus_app.playlistmaker.domain.interactors.FavoritesInteractor
import com.zaus_app.playlistmaker.domain.repositrories.FavoritesDatabaseRepository
import kotlinx.coroutines.flow.Flow

class FavoritesInteractorImpl(private val repository: FavoritesDatabaseRepository): FavoritesInteractor {
    override suspend fun addTrack(track: Track) {
        repository.addTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        repository.deleteTrack(track)
    }

    override fun getTracksIDs(): Flow<List<Int>> = repository.getTracksIDs()

    override suspend fun getTrackById(trackId: Int): Track? = repository.getTrackById(trackId)

    override fun getAllFavoritesTrack(): Flow<List<Track>> = repository.getAllFavoritesTrack()
    override suspend fun getPlaylistTrackById(trackId: Int): Track? = repository.getPlaylistTrackById(trackId)
    override suspend fun addTrackToPlaylistTable(track: TrackInPlaylist) {
        repository.addTrackToPlaylistTable(track)
    }
}