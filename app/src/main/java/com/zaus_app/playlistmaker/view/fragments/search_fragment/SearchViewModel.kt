package com.zaus_app.playlistmaker.view.fragments.search_fragment

import androidx.lifecycle.ViewModel
import com.zaus_app.playlistmaker.data.Track
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val remoteUseCase: RemoteUseCase, private val preferenceProvider: PreferenceProvider): ViewModel() {
    fun search(term: String) = remoteUseCase.getTracksFromWeb(term)

    fun saveTrack(track: Track) {
        preferenceProvider.saveTrack(track)
    }

    fun getHistory(): List<Track> = preferenceProvider.getHistory().toList()
}