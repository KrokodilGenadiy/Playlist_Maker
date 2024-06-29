package com.zaus_app.playlistmaker.data.implementations.interactors

import com.zaus_app.playlistmaker.domain.interactors.SettingsInteractor
import com.zaus_app.playlistmaker.domain.repositrories.SettingsRepository

class SettingInteractorImpl(val repository: SettingsRepository): SettingsInteractor {
    override fun sharePlaylist(message: String) {
        repository.sharePlaylist(message)
    }
}