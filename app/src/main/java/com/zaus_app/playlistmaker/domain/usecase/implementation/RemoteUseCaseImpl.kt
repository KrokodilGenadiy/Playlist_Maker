package com.zaus_app.playlistmaker.domain.usecase.implementation

import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import com.zaus_app.playlistmaker.data.api.responses.SearchResponse
import com.zaus_app.playlistmaker.data.base.ResultResponse
import com.zaus_app.playlistmaker.domain.repositroies.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteUseCaseImpl(private val repository: RemoteRepository): RemoteUseCase {
    override fun getTracksFromWeb(term: String): Flow<ResultResponse<SearchResponse>> = flow {
        emit(ResultResponse.Loading)
        val result = repository.getTracks(term)
        emit(result)
    }.flowOn(Dispatchers.IO)
}