package com.zaus_app.playlistmaker.view.fragments.settings_fragment

import androidx.lifecycle.ViewModel
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel@Inject constructor(private val preferenceProvider: PreferenceProvider): ViewModel() {
    fun getThemeStatus() = preferenceProvider.getDefaultTheme()
}