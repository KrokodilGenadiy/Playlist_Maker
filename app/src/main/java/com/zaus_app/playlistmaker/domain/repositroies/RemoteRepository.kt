package com.zaus_app.playlistmaker.domain.repositroies

import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse

interface RemoteRepository {
    suspend fun getTracks(term: String): ResultResponse<SearchResponse>
}