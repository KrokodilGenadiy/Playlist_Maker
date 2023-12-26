package com.zaus_app.playlistmaker.data.api.responses

import com.zaus_app.playlistmaker.data.Track

data class SearchResponse(
    val resultCount: Int,
    val results: List<Track>
)