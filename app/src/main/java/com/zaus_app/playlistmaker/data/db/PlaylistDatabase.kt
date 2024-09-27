package com.zaus_app.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaus_app.playlistmaker.data.db.dao.PlaylistDao
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.util.RoomConverter

@Database(entities = [Playlist::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class PlaylistDatabase : RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao
}