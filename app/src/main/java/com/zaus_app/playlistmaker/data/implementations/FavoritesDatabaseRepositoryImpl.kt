package com.zaus_app.playlistmaker.data.implementations

import com.zaus_app.playlistmaker.data.db.FavoritesDatabase
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.entities.TrackInPlaylist
import com.zaus_app.playlistmaker.domain.repositrories.FavoritesDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesDatabaseRepositoryImpl(
    private val database: FavoritesDatabase,
) : FavoritesDatabaseRepository {

    override suspend fun addTrack(track: Track) {
        database.trackDao().addTrack(
            track
        )
    }

    override suspend fun deleteTrack(track: Track) {
        track.trackId.let { database.trackDao().deleteTrack(it) }
    }

    override fun getTracksIDs(): Flow<List<Int>> = database.trackDao().getTracksIds()

    override suspend fun getTrackById(trackId: Int): Track? = database.trackDao().getTrackById(trackId)

    override fun getAllFavoritesTrack(): Flow<List<Track>> = database.trackDao().getAllTrack()
    override suspend fun getPlaylistTrackById(trackId: Int): Track? = database.trackDao().getPlaylistTrackById(trackId)
    override suspend fun addTrackToPlaylistTable(track: TrackInPlaylist) {
        database.trackDao().addTrackToPlaylist(track)
    }


}