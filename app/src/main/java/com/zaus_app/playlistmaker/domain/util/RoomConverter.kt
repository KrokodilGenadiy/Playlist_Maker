package com.zaus_app.playlistmaker.domain.util

import androidx.room.TypeConverter
import android.net.Uri

class RoomConverter {
    @TypeConverter
    fun fromUriToString(uri: Uri?): String {
        return uri.toString()
    }

    @TypeConverter
    fun fromStringToUri(str: String): Uri? {
        return Uri.parse(str)
    }

    @TypeConverter
    fun fromListInt(list: MutableList<Int>): String {
        return if (list.isNotEmpty())
            list.joinToString(",")
        else
            ""
    }

    @TypeConverter
    fun toListInt(data: String): MutableList<Int> {
        return if (data != "")
            mutableListOf(*data.split(",").map { it.toInt() }.toTypedArray())
        else
            mutableListOf()
    }
}