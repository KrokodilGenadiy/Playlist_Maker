package com.zaus_app.playlistmaker.domain.repositrories

import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getTracks(term: String): Flow<ResultResponse<SearchResponse>>
}