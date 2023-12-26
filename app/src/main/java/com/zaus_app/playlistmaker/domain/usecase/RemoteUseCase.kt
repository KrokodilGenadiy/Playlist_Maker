package com.zaus_app.playlistmaker.domain.usecase

import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse
import kotlinx.coroutines.flow.Flow

interface RemoteUseCase {
    fun getTracksFromWeb(term: String): Flow<ResultResponse<SearchResponse>>
}