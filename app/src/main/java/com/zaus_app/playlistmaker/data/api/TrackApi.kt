package com.zaus_app.playlistmaker.data.api

import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): Response<SearchResponse>
}