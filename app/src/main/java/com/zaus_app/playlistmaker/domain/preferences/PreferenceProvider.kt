package com.zaus_app.playlistmaker.domain.preferences

import com.zaus_app.playlistmaker.data.entities.Track

interface PreferenceProvider {
    fun getHistory(): List<Track>
    fun saveDefaultTheme(mode: Boolean)
    fun getDefaultTheme(): Boolean
    fun saveHistory(tracks: ArrayList<Track>)
    fun saveTrack(track: Track)
    fun clearHistory()
}