package com.zaus_app.playlistmaker.domain.repositroies.implementation

import com.zaus_app.playlistmaker.data.api.TrackApi
import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse
import com.zaus_app.playlistmaker.domain.repositroies.RemoteRepository
import retrofit2.Response

class RemoteRepositoryImpl(private val retrofitService: TrackApi): RemoteRepository {
    override suspend fun getTracks(term: String): ResultResponse<SearchResponse> {
        return try {
            val response: Response<SearchResponse> = retrofitService.search(term)
            if (response.isSuccessful) {
                ResultResponse.Success(response.body()!!)
            } else {
                ResultResponse.Error(response.message())
            }
        } catch (e: Exception) {
            ResultResponse.Error(e.message ?: "An error occurred")
        }
    }
}
