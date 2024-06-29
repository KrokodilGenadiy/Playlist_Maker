package com.zaus_app.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaus_app.playlistmaker.data.db.dao.TrackDao
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.entities.TrackInPlaylist

@Database(entities = [Track::class,TrackInPlaylist::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
}