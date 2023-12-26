package com.zaus_app.playlistmaker.domain.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.zaus_app.playlistmaker.data.Track

class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putBoolean(KEY_DEFAULT_THEME, DEFAULT_THEME) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    fun saveDefaultTheme(theme: Boolean) {
        preference.edit { putBoolean(KEY_DEFAULT_THEME, theme) }
    }

    fun getDefaultTheme(): Boolean {
        return preference.getBoolean(KEY_DEFAULT_THEME, DEFAULT_THEME) ?: DEFAULT_THEME
    }

    fun getHistory(): Array<Track> {
        val json = preference.getString(TRACKS_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    private fun saveHistory(tracks: ArrayList<Track>) {
        val json = Gson().toJson(tracks)
        preference.edit()
            .putString(TRACKS_LIST_KEY, json)
            .apply()
    }

    fun saveTrack(track: Track) {
        val history = arrayListOf<Track>()
        history.addAll(getHistory())
        if (history.contains(track)) {
            history.remove(track)
        }
        history.add(0, track)
        if (history.size > 10) {
            history.removeLast()
        }
        saveHistory(history)
    }

    private fun clearHistory() {
        preference
            .edit()
            .clear()
            .apply()
    }

    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_THEME = "default_theme"
        private const val TRACKS_LIST_KEY = "track_list"
        private const val DEFAULT_THEME = false
    }
}