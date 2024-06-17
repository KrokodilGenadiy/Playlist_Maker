package com.zaus_app.playlistmaker.data.implementations

import com.zaus_app.playlistmaker.domain.repositrories.AudioPlayerRepository
import com.zaus_app.playlistmaker.domain.util.State

class AudioPlayerInteractorImpl(private val audioPlayerRepository: AudioPlayerRepository) :
    AudioPlayerRepository {
    override fun startPlayer() {
        audioPlayerRepository.startPlayer()
    }

    override fun pausePlayer() {
        audioPlayerRepository.pausePlayer()
    }

    override fun preparePlayer(url: String, statusBeenChanged: (s: State) -> Unit) {
        audioPlayerRepository.preparePlayer(url, statusBeenChanged)
    }

    override fun changingPlayer(statusBeenChanged: (s: State) -> Unit) {
        audioPlayerRepository.changingPlayer(statusBeenChanged)
    }

    override fun stoppingPlayer() {
        audioPlayerRepository.stoppingPlayer()
    }

    override fun getCurrentState(): State {
        return audioPlayerRepository.getCurrentState()
    }

    override fun getCurrentPosition(): Int = audioPlayerRepository.getCurrentPosition()
}