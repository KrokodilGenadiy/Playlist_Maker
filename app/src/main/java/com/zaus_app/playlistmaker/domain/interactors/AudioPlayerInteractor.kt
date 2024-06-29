package com.zaus_app.playlistmaker.domain.interactors

import com.zaus_app.playlistmaker.domain.util.State

interface AudioPlayerInteractor {
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(url: String, statusBeenChanged: (s: State) -> Unit)
    fun changingPlayer(statusBeenChanged: (s: State) -> Unit)
    fun stoppingPlayer()
    fun getCurrentState(): State
    fun getCurrentPosition(): Int
}