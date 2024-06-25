package com.zaus_app.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaus_app.playlistmaker.domain.entities.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: Track)

    @Query("DELETE FROM favorites_table WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: Int)

    @Query("SELECT * FROM favorites_table ORDER BY `Add time` DESC")
    fun getAllTrack(): Flow<List<Track>>

    @Query("SELECT trackId FROM favorites_table ")
    fun getTracksIds(): Flow<List<Int>>

    @Query("SELECT * FROM favorites_table WHERE trackId = :trackId")
    suspend fun getTrackById(trackId: Int): Track?
}