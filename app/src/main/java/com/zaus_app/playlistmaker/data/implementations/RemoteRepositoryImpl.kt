package com.zaus_app.playlistmaker.data.implementations

import com.zaus_app.playlistmaker.data.api.TrackApi
import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse
import com.zaus_app.playlistmaker.domain.repositrories.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class RemoteRepositoryImpl(private val retrofitService: TrackApi): RemoteRepository {
    override suspend fun getTracks(term: String): Flow<ResultResponse<SearchResponse>> = flow  {
    try {
            emit(ResultResponse.Loading)
            val response: Response<SearchResponse> = retrofitService.search(term)
            if (response.isSuccessful) {
               emit(ResultResponse.Success(response.body()!!))
            } else {
                emit(ResultResponse.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}
