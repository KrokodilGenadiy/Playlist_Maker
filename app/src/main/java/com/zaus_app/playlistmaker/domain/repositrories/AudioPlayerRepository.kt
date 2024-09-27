package com.zaus_app.playlistmaker.domain.repositrories

import com.zaus_app.playlistmaker.domain.util.State

interface AudioPlayerRepository {
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(url: String, statusBeenChanged: (s: State) -> Unit)
    fun changingPlayer(statusBeenChanged: (s: State) -> Unit)
    fun stoppingPlayer()
    fun getCurrentState(): State
    fun getCurrentPosition(): Int
}