package com.zaus_app.playlistmaker.presentation.fragments.search_fragment

import androidx.lifecycle.ViewModel
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.data.implementations.PreferenceProviderImpl
import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val remoteUseCase: RemoteUseCase, private val preferenceProviderImpl: PreferenceProvider): ViewModel() {

    val historyList: Flow<List<Track>> = (preferenceProviderImpl as PreferenceProviderImpl).historyFlow

    fun search(term: String) = remoteUseCase.getTracksFromWeb(term)

    fun saveTrack(track: Track) {
        preferenceProviderImpl.saveTrack(track)
    }


    fun clearHistory() {
        preferenceProviderImpl.clearHistory()
    }
}