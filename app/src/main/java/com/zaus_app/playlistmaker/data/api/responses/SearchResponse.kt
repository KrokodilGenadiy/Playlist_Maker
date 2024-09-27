package com.zaus_app.playlistmaker.data.api.responses

import com.zaus_app.playlistmaker.domain.entities.Track

data class SearchResponse(
    val resultCount: Int,
    val results: List<Track>
)