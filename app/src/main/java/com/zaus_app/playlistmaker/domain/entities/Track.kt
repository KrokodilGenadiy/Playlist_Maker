package com.zaus_app.playlistmaker.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize

@Entity(tableName = "favorites_table",indices = [Index(value = ["Track"], unique = false)])
data class Track(
    @PrimaryKey
    val trackId: Int,
    @ColumnInfo(name = "Track")
    val trackName: String,
    @ColumnInfo(name = "Artist")
    val artistName: String,
    @ColumnInfo(name = "Duration")
    val trackTimeMillis: Long,
    @ColumnInfo(name = "Cover")
    val artworkUrl100: String,
    @ColumnInfo(name = "Collection")
    val collectionName: String,
    @ColumnInfo(name = "Release date")
    val releaseDate: String,
    @ColumnInfo(name = "Genre")
    val primaryGenreName: String,
    @ColumnInfo(name = "Country")
    val country: String,
    @ColumnInfo(name = "Url")
    val previewUrl: String,
    @ColumnInfo(name = "Add time")
    var addTime: Long
): Parcelable

