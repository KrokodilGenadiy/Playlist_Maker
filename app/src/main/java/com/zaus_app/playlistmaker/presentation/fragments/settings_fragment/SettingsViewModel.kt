package com.zaus_app.playlistmaker.presentation.fragments.settings_fragment

import androidx.lifecycle.ViewModel
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider

class SettingsViewModel(private val preferenceProviderImpl: PreferenceProvider): ViewModel() {
    fun getThemeStatus() = preferenceProviderImpl.getDefaultTheme()
}