package com.zaus_app.playlistmaker.data.implementations

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.zaus_app.playlistmaker.data.entities.Track
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class PreferenceProviderImpl(context: Context): PreferenceProvider {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _historyFlow = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == TRACKS_LIST_KEY) {
                    trySend(getHistory()).isSuccess
                }
            }
        preference.registerOnSharedPreferenceChangeListener(listener)
        trySend(getHistory()).isSuccess
        awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
    }.distinctUntilChanged()

    val historyFlow: Flow<List<Track>> = _historyFlow

    override fun getHistory(): List<Track> {
        val json = preference.getString(TRACKS_LIST_KEY, null) ?: return emptyList()
        return Gson().fromJson(json, Array<Track>::class.java).toList()
    }

    init {
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putBoolean(IS_DARK_MODE, DEFAULT_DARK_MODE) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    override fun saveDefaultTheme(mode: Boolean) {
        preference.edit { putBoolean(IS_DARK_MODE, mode) }
    }

    override fun getDefaultTheme(): Boolean {
        return preference.getBoolean(IS_DARK_MODE, DEFAULT_DARK_MODE)
    }

    override fun saveHistory(tracks: ArrayList<Track>) {
        val json = Gson().toJson(tracks)
        preference.edit {
            putString(TRACKS_LIST_KEY, json)
        }
    }

    override fun saveTrack(track: Track) {
        getHistory()
            .filter { it != track }
            .toMutableList()
            .apply { add(0, track) }
            .take(10)
            .let { saveHistory(ArrayList(it)) }
    }

    override fun clearHistory() {
        saveHistory(ArrayList())
    }

    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val IS_DARK_MODE = "default_mode"
        private const val TRACKS_LIST_KEY = "track_list"
        private const val DEFAULT_DARK_MODE = false
    }
}