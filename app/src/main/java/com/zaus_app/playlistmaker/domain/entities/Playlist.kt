package com.zaus_app.playlistmaker.domain.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class Playlist (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val playlistName: String,
    val description: String,
    val urlImage: Uri?,
    @ColumnInfo(name = "Tracks")
    val tracks: MutableList<Int>,
    var tracksCount: Int
)