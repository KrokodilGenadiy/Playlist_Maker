package com.zaus_app.playlistmaker.presentation.fragments.search_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.data.implementations.PreferenceProviderImpl
import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(private val remoteUseCase: RemoteUseCase, private val preferenceProviderImpl: PreferenceProvider): ViewModel() {

    val historyList: Flow<List<Track>> = (preferenceProviderImpl as PreferenceProviderImpl).historyFlow
    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class)
    val response: StateFlow<ResultResponse<SearchResponse>> = query.flatMapLatest {
        if (it.isNotEmpty())
            remoteUseCase.getTracksFromWeb(query.value)
        else
            flowOf(ResultResponse.Initial)
    }.stateIn(viewModelScope, SharingStarted.Lazily, ResultResponse.Initial)

    fun search(term: String) = remoteUseCase.getTracksFromWeb(term)

    fun saveTrack(track: Track) {
        preferenceProviderImpl.saveTrack(track)
    }

    fun clearHistory() {
        preferenceProviderImpl.clearHistory()
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}